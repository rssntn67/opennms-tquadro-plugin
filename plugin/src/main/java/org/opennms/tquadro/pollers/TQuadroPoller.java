package org.opennms.tquadro.pollers;

import java.util.concurrent.CompletableFuture;

import org.opennms.integration.api.v1.pollers.PollerResult;
import org.opennms.integration.api.v1.pollers.Status;
import org.opennms.integration.api.v1.pollers.immutables.ImmutablePollerResult;
import org.opennms.tquadro.client.api.TQuadroApiException;
import org.opennms.tquadro.clients.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TQuadroPoller extends TQuadroAbstractPoller {

    private static final Logger LOG = LoggerFactory.getLogger(TQuadroPoller.class);

    public TQuadroPoller(ClientManager client) {
        super(client);
    }

    @Override
    protected CompletableFuture<PollerResult> poll(Context context) throws TQuadroApiException {
        context.request.getAddress();
        if (context.client().getAssetByIpAddress(context.request.getAddress()) != null) {
            return CompletableFuture.completedFuture(ImmutablePollerResult.newBuilder()
                    .setStatus(Status.Up)
                    .build());
        };

        return CompletableFuture.completedFuture(ImmutablePollerResult.newBuilder()
                .setStatus(Status.Down)
                .setReason("No Asset Entity for ip address: " + context.request.getAddress().getHostAddress() + " found on: " + context.getClientCredentials().tquadroUrl)
                .build());
    }


}
