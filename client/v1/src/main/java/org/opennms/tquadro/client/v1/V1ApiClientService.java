package org.opennms.tquadro.client.v1;

import java.net.InetAddress;

import org.opennms.tquadro.client.api.ApiClientService;
import org.opennms.tquadro.client.api.TQuadroApiException;
import org.opennms.tquadro.client.api.model.Asset;

public class V1ApiClientService implements ApiClientService {



    @Override
    public Asset getAssetByIpAddress(InetAddress address) throws TQuadroApiException {
        throw new TQuadroApiException("not supported", new UnsupportedOperationException());
    }

    @Override
    public void createAssetByIpAddress(InetAddress address) throws TQuadroApiException {
        throw new TQuadroApiException("not supported", new UnsupportedOperationException());
    }

    @Override
    public void updateDiscovedAssetById(Integer assetId) throws TQuadroApiException {
        throw new TQuadroApiException("not supported", new UnsupportedOperationException());
    }

}
