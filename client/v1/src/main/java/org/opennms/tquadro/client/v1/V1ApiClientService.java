package org.opennms.tquadro.client.v1;

import java.net.InetAddress;

import org.opennms.tquadro.client.api.ApiClientService;
import org.opennms.tquadro.client.api.TQuadroApiException;
import org.opennms.tquadro.client.api.model.Asset;
import org.opennms.tquadro.client.v1.api.OpenNmsApi;

public class V1ApiClientService implements ApiClientService {

    private final OpenNmsApi openNmsApi;

    public V1ApiClientService(ApiClientExtension client) {
        this.openNmsApi = new OpenNmsApi(client);
    }

    @Override
    public Asset getAssetByIpAddress(InetAddress address) throws TQuadroApiException {

        throw new TQuadroApiException("not supported", new UnsupportedOperationException());
    }

    @Override
    public Asset createAsset(Asset asset) throws TQuadroApiException {
        throw new TQuadroApiException("not supported", new UnsupportedOperationException());
    }

    @Override
    public void updateDiscovedAssetById(Integer assetId) throws TQuadroApiException {
        throw new TQuadroApiException("not supported", new UnsupportedOperationException());
    }

}
