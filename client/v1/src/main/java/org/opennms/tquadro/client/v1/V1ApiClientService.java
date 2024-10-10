package org.opennms.tquadro.client.v1;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.opennms.tquadro.client.api.ApiClientService;
import org.opennms.tquadro.client.api.TQuadroApiException;
import org.opennms.tquadro.client.api.model.Asset;
import org.opennms.tquadro.client.v1.api.OpenNmsApi;
import org.opennms.tquadro.client.v1.handler.ApiException;
import org.opennms.tquadro.client.v1.model.AssetOpenNMS;

public class V1ApiClientService implements ApiClientService {

    private final OpenNmsApi openNmsApi;
    public V1ApiClientService(ApiClientExtension client) {
        this.openNmsApi = new OpenNmsApi(client);
    }

    @Override
    public Asset getAssetByIpAddress(InetAddress address) throws TQuadroApiException {
        try {
            AssetOpenNMS asset = openNmsApi.apiOpenNMSAssetIpAddressGet(address.getHostAddress());
            return Asset.builder()
                    .withAssetId(asset.getAssetId())
                    .withHostname(asset.getHostname())
                    .withIpAddress(InetAddress.getByName(asset.getIpAddress()))
                    .build();
        } catch (ApiException e) {
            throw new TQuadroApiException(e.getMessage(), e, e.getCode(),e.getResponseHeaders(), e.getResponseBody());
        } catch (UnknownHostException e) {
            throw new TQuadroApiException(e.getMessage(),e);
        }
    }

    @Override
    public Asset createAsset(Asset asset) throws TQuadroApiException {
        try {
            AssetOpenNMS body = new AssetOpenNMS();
            body.setAssetId(asset.assetId);
            body.setHostname(asset.hostname);
            body.setIpAddress(asset.ipAddress.getHostAddress());
            body.setContactSNMP(asset.systemContact);
            body.setLocationSNMP(asset.systemLocation);
            body.setNameSNMP(asset.systemName);
            body.setSysObjectIdSNMP(asset.systemOid);
            body.descriptionSNMP(asset.systemDescription);
//            body.setArea(asset.area);
            AssetOpenNMS saved = openNmsApi.apiOpenNMSAssetPost(body);
            return Asset.builder()
                    .withAssetId(saved.getAssetId())
                    .withHostname(saved.getHostname())
                    .withIpAddress(InetAddress.getByName(saved.getIpAddress()))
                    .withSystemContact(saved.getContactSNMP())
                    .withSystemDescription(saved.getDescriptionSNMP())
                    .withSystemName(saved.getNameSNMP())
                    .withSystemOid(saved.getSysObjectIdSNMP())
                    .withSystemLocation(saved.getLocationSNMP())
  //                  .withArea(saved.getArea())
                    .build();
        } catch (ApiException e) {
            throw new TQuadroApiException(e.getMessage(), e, e.getCode(),e.getResponseHeaders(), e.getResponseBody());
        } catch (UnknownHostException e) {
            throw new TQuadroApiException(e.getMessage(),e);
        }
    }

    @Override
    public void updateDiscovedAssetById(Integer assetId) throws TQuadroApiException {
        try {
            openNmsApi.apiOpenNMSDiscoveredDateAssetIdPut(assetId, "");
        } catch (ApiException e) {
            throw new TQuadroApiException(e.getMessage(), e, e.getCode(),e.getResponseHeaders(), e.getResponseBody());
        }
    }

}
