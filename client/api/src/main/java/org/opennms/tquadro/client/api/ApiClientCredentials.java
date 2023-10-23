package org.opennms.tquadro.client.api;

import java.util.Objects;

import com.google.common.base.MoreObjects;

/**
 * Credentials for a Nutanix API connection.
 */
public class ApiClientCredentials {
    /**
     * The URL of the TQuadro Rest Interface.
     */
    public final String tquadroUrl;

    /**
     * The username used to authenticate the connection to the PRISM ELEMENT.
     */
    public final String username;

    /**
     * The password used to authenticate the connection to the PRISM ELEMENT.
     */
    public final String password;

    /**
     * Wheter to check SSL Certificate
     */
    public final Boolean ignoreSslCertificateValidation;

    private ApiClientCredentials(final Builder builder) {
        this.tquadroUrl = Objects.requireNonNull(builder.tquadroUrl);
        this.username = builder.username;
        this.password = builder.password;
        this.ignoreSslCertificateValidation = builder.ignoreSslCertificateValidation;
    }

    public static class Builder {
        private String tquadroUrl;
        private String username;
        private String password;

        private boolean ignoreSslCertificateValidation = false;

        private Builder() {
        }

        public Builder withTQuadroUrl(final String orchestratorUrl) {
            this.tquadroUrl = orchestratorUrl;
            return this;
        }

        public Builder withUsername(final String username) {
            this.username = username;
            return this;
        }


        public Builder withPassword(final String password) {
            this.password = password;
            return this;
        }

        public Builder withIgnoreSslCertificateValidation(final Boolean ignoreSslCertificateValidation) {
            this.ignoreSslCertificateValidation = ignoreSslCertificateValidation;
            return this;
        }

        public ApiClientCredentials build() {
            return new ApiClientCredentials(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(ApiClientCredentials credentials) {
        return builder()
                .withTQuadroUrl(credentials.tquadroUrl)
                .withUsername(credentials.username)
                .withPassword(credentials.password)
                .withIgnoreSslCertificateValidation(credentials.ignoreSslCertificateValidation);

    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tquadroUrl", this.tquadroUrl)
                .add("username", this.username)
                .add("password", "*****")
                .add("ignoreSslCertificateValidation", this.ignoreSslCertificateValidation)
                .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiClientCredentials)) {
            return false;
        }
        final ApiClientCredentials that = (ApiClientCredentials) o;
        return Objects.equals(this.tquadroUrl, that.tquadroUrl) &&
                Objects.equals(this.username, that.username) &&
                Objects.equals(this.password, that.password) &&
                Objects.equals(this.ignoreSslCertificateValidation, that.ignoreSslCertificateValidation);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tquadroUrl,
                 this.username, this.password, this.ignoreSslCertificateValidation);
    }

}
