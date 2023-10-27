package org.opennms.tquadro.client.api;

import java.net.InetAddress;
import java.util.List;

import org.opennms.tquadro.client.api.model.Asset;

public interface ApiClientService {
    /**
     * Get the Asset by ipAddr.
     *
     * @param  address the ip address of the Asset
     * @return a {@link Asset}s
     * @throws TQuadroApiException "see message for detail"
     */
    Asset getAssetByIpAddress(InetAddress address) throws TQuadroApiException;

    Asset createAsset(Asset asset) throws TQuadroApiException;

    void updateDiscovedAssetById(Integer assetId) throws TQuadroApiException;

}
