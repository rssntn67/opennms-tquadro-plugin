package org.opennms.tquadro.pollers;

import java.util.concurrent.CompletableFuture;

import org.opennms.integration.api.v1.pollers.PollerResult;
import org.opennms.integration.api.v1.pollers.Status;
import org.opennms.integration.api.v1.pollers.immutables.ImmutablePollerResult;
import org.opennms.tquadro.client.api.ApiClientService;
import org.opennms.tquadro.client.api.TQuadroApiException;
import org.opennms.tquadro.client.api.model.Asset;
import org.opennms.tquadro.clients.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TQuadroPoller extends TQuadroAbstractPoller {

    private static final Logger LOG = LoggerFactory.getLogger(TQuadroPoller.class);

    public TQuadroPoller(ClientManager client) {
        super(client);
    }

    @Override
    protected CompletableFuture<PollerResult> poll(Context context)  {
        ApiClientService api = context.client();
        try {
            Asset asset =api.getAssetByIpAddress(context.request.getAddress());
            api.updateDiscovedAssetById(asset.assetId);
            LOG.info("poll: {} found assetId {}", context.request.getAddress().getHostAddress(), asset.assetId);
            return CompletableFuture.completedFuture(ImmutablePollerResult.newBuilder()
                        .setStatus(Status.Up)
                        .build());
        } catch (TQuadroApiException e) {
            if (e.getCode() == 404) {
                LOG.info("poll: {}: Down. Asset not found on {}",
                        context.request.getAddress().getHostAddress(),
                        context.getClientCredentials().tquadroUrl
                        );
                if (context.getCreate()) {
                    create(
                            Asset.builder()
                            .withAssetId(0)
                            .withIpAddress(context.request.getAddress())
                            .withHostname(context.getLabel())
                            .withSystemOid(context.getSysOid())
                            .withSystemName(context.getSysname())
                            .withSystemContact(context.getSysContact())
                            .withSystemLocation(context.getSysLocation())
                            .withSystemDescription(context.getSysDescription())
                            .build(),
                            api
                    );
                    return CompletableFuture.completedFuture(ImmutablePollerResult.newBuilder()
                            .setStatus(Status.Down)
                            .setReason("No Asset Entity for ip address: "
                                    + context.request.getAddress().getHostAddress()
                                    + " on: "
                                    + context.getClientCredentials().tquadroUrl)
                            .build());
                }
            }
            LOG.error("poll:{}:Unknown. Got HTTP {} on {} with message: {}",
                    context.request.getAddress().getHostAddress(),
                    e.getCode(),
                    context.getClientCredentials().tquadroUrl,
                    e.getMessage()
            );
            return CompletableFuture.completedFuture(ImmutablePollerResult.newBuilder()
                        .setStatus(Status.Unknown)
                        .setReason(e.getMessage()
                                + " for: " + context.request.getAddress().getHostAddress()
                                + " on: " + context.getClientCredentials().tquadroUrl)
                        .build());
        }
    }

    private void create(Asset asset, ApiClientService api) {
        try {
            Asset saved = api.createAsset(asset);
            LOG.info("Asset Created {}", saved);
        } catch (TQuadroApiException e) {
            LOG.error("cannot create {}", asset);
        }
    }


}
