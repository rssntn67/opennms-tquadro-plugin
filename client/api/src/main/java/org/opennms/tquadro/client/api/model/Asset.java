
package org.opennms.tquadro.client.api.model;

import java.net.InetAddress;
import java.util.Objects;

public class Asset {
    public final Integer assetId;

    public final String hostname;
    public final InetAddress ipAddress;

    public Asset(Builder builder) {
        this.assetId = Objects.requireNonNull(builder.assetId);
        this.hostname = builder.hostname;
        this.ipAddress = Objects.requireNonNull(builder.ipAddress);
    }


    public static class Builder {

        private Integer assetId;

        private String hostname;
        private InetAddress ipAddress;

        private Builder() {
        }

        public Asset.Builder withAssetId(Integer assetId) {
            this.assetId = assetId;
            return this;
        }

        public Asset.Builder withHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Asset.Builder withIpAddress(InetAddress ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Asset build() {
            return new Asset(this);
        }


    }

    public static Builder builder() {
        return new Builder();
    }
}
