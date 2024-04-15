
package org.opennms.tquadro.client.api.model;

import java.net.InetAddress;
import java.util.Objects;

public class Asset {
    public final Integer assetId;
    public final String hostname;
    public final InetAddress ipAddress;

    public final String systemName;
    public final String systemOid;
    public final String systemDescription;
    public final String systemContact;

    public final String systemLocation;

    public final String area;

    @Override
    public String toString() {
        return "Asset{" +
                "assetId=" + assetId +
                ", hostname='" + hostname + '\'' +
                ", ipAddress=" + ipAddress +
                ", systemName='" + systemName + '\'' +
                ", systemOid='" + systemOid + '\'' +
                ", systemDescription='" + systemDescription + '\'' +
                ", systemContact='" + systemContact + '\'' +
                ", systemLocation='" + systemLocation + '\'' +
                ", area='" + area + '\'' +
                '}';
    }

    public Asset(Builder builder) {
        this.assetId = Objects.requireNonNull(builder.assetId);
        this.hostname = builder.hostname;
        this.ipAddress = Objects.requireNonNull(builder.ipAddress);
        this.systemOid = builder.systemOid;
        this.systemContact= builder.systemContact;
        this.systemLocation = builder.systemLocation;
        this.systemName = builder.systemName;
        this.systemDescription = builder.systemDescription;
        this.area = builder.area;
    }


    public static class Builder {

        private Integer assetId;

        private String hostname;
        private InetAddress ipAddress;

        private String systemName;
        private String systemOid;
        private String systemDescription;
        private String systemContact;

        private String systemLocation;
        private String area;

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

        public Asset.Builder withSystemName(String systemName) {
            this.systemName = systemName;
            return this;
        }

        public Asset.Builder withSystemOid(String systemOid) {
            this.systemOid = systemOid;
            return this;
        }

        public Asset.Builder withSystemContact(String systemContact) {
            this.systemContact = systemContact;
            return this;
        }

        public Asset.Builder withSystemLocation(String systemLocation) {
            this.systemLocation = systemLocation;
            return this;
        }

        public Asset.Builder withSystemDescription(String systemDescription) {
            this.systemDescription = systemDescription;
            return this;
        }

        public Asset.Builder withIpAddress(InetAddress ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Asset.Builder withArea(String area) {
            this.area = area;
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
