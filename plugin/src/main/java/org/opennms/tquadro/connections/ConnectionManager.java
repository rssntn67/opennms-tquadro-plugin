package org.opennms.tquadro.connections;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.opennms.integration.api.v1.runtime.Container;
import org.opennms.integration.api.v1.runtime.RuntimeInfo;
import org.opennms.integration.api.v1.scv.Credentials;
import org.opennms.integration.api.v1.scv.SecureCredentialsVault;
import org.opennms.integration.api.v1.scv.immutables.ImmutableCredentials;
import org.opennms.tquadro.client.api.ApiClientCredentials;
import org.opennms.tquadro.client.api.ApiClientService;
import org.opennms.tquadro.client.api.TQuadroApiException;
import org.opennms.tquadro.clients.ClientManager;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

public class ConnectionManager {

    private static final String PREFIX = "tquadro_connection_";
    public static final String TQUADRO_URL_KEY = "tquadroUrl";
    public static final String IGNORE_SSL_CERTIFICATE_VALIDATION_KEY = "ignoreSslCertificateValidation";

    public static final String ALIAS_KEY = "alias";

    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";




    private final RuntimeInfo runtimeInfo;

    private final SecureCredentialsVault vault;

    private final ClientManager clientManager;

    public ConnectionManager(final RuntimeInfo runtimeInfo,
                             final SecureCredentialsVault vault,
                             final ClientManager clientManager) {
        this.runtimeInfo = Objects.requireNonNull(runtimeInfo);
        this.vault = Objects.requireNonNull(vault);
        this.clientManager = Objects.requireNonNull(clientManager);
    }

    /**
     * Returns a set of all available connection aliases.
     *
     * @return the set of aliases
     */
    public Set<String> getAliases() {
        this.ensureCore();

        return this.vault.getAliases().stream()
                .filter(alias -> alias.startsWith(PREFIX))
                .map(alias -> alias.substring(PREFIX.length()))
                .collect(Collectors.toSet());
    }

    /**
     * Returns a connection config for the given alias.
     *
     * @param alias the alias of the connection config to retrieve
     * @return The connection config or {@code Optional#empty()} of no such alias exists
     */
    public Optional<Connection> getConnection(final String alias) {
        this.ensureCore();

        final var credentials = this.vault.getCredentials(PREFIX + alias.toLowerCase());
        if (credentials == null) {
            return Optional.empty();
        }

        return Optional.of(new ConnectionImpl(alias, ConnectionManager.fromStore(credentials)));
    }

    /**
     * Creates a basic authentication connection under the given alias.
     *
     * @param alias           the alias of the connection to add
     * @param tquadroUrl        the URL of the prism server
     * @param username          the username to authenticate the connection
     * @param password          the password to authenticate the connection
     * @param ignoreSslCerticateValidation          ignore Ssl Certificate Validation
     */
    public Connection newConnection(final String alias, final String tquadroUrl, final String username, final String password, final boolean ignoreSslCerticateValidation) {
        this.ensureCore();

        return new ConnectionImpl(alias, ApiClientCredentials.builder()
                .withTQuadroUrl(tquadroUrl)
                .withUsername(username)
                .withPassword(password)
                .withIgnoreSslCertificateValidation(ignoreSslCerticateValidation)
                .build());
    }

    /**
     * Deletes a connection under the given alias.
     *
     * @param alias the alias of the connection to delete
     * @return <b>true</b> if an existing connection with given alias was found and deleted and <b>false</b> if no
     * connection with given alias was not found
     */
    public boolean deleteConnection(final String alias) {
        this.ensureCore();

        final var connection = this.getConnection(alias);
        if (connection.isEmpty()) {
            return false;
        }
        connection.get().delete();
        return true;
    }

    public Optional<ApiClientService> getClient(final String alias) throws TQuadroApiException {
        this.ensureCore();

        final var connection = this.getConnection(alias);
        if (connection.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(this.clientManager.getClient(asNutanixCredentials(connection.get())));
    }


    private static ApiClientCredentials asNutanixCredentials(Connection connection) {
        return ApiClientCredentials.builder()
                .withUsername(connection.getUsername())
                .withPassword(connection.getPassword())
                .withTQuadroUrl(connection.getTQuadroUrl())
                .withIgnoreSslCertificateValidation(connection.isIgnoreSslCertificateValidation())
                .build();
    }

    private static ApiClientCredentials fromStore(final Credentials credentials) {


        if (Strings.isNullOrEmpty(credentials.getPassword())) {
            throw new IllegalStateException("API password is missing");
        }
        if (Strings.isNullOrEmpty(credentials.getUsername())) {
            throw new IllegalStateException("API username is missing");
        }

        if (Strings.isNullOrEmpty(credentials.getAttribute(TQUADRO_URL_KEY))) {
            throw new IllegalStateException("TQuadro URL is missing");
        }

        if (Strings.isNullOrEmpty(credentials.getAttribute(IGNORE_SSL_CERTIFICATE_VALIDATION_KEY))) {
            throw new IllegalStateException("Ignore  SSL CERTIFICATION Validation is missing");
        }


        final var tquadroUrl = credentials.getAttribute(TQUADRO_URL_KEY);

        final var username = credentials.getUsername();
        final var password = credentials.getPassword();
        final var ignoreSslCertificateValidation = Boolean.parseBoolean(credentials.getAttribute(IGNORE_SSL_CERTIFICATE_VALIDATION_KEY));

        return ApiClientCredentials.builder()
                .withTQuadroUrl(tquadroUrl)
                .withUsername(username)
                .withPassword(password)
                .withIgnoreSslCertificateValidation(ignoreSslCertificateValidation)
                .build();
        }


    private class ConnectionImpl implements Connection {
        private final String alias;

        private ApiClientCredentials credentials;

        private ConnectionImpl(final String alias, final ApiClientCredentials credentials) {
            this.alias = Objects.requireNonNull(alias).toLowerCase();
            this.credentials = Objects.requireNonNull(credentials);
        }

        @Override
        public boolean isIgnoreSslCertificateValidation() {
            return this.credentials.ignoreSslCertificateValidation;
        }

        @Override
        public void setIgnoreSslCertificateValidation(boolean ignoreSslCertificateValidation) {
            this.credentials = ApiClientCredentials.builder(this.credentials)
                    .withIgnoreSslCertificateValidation(ignoreSslCertificateValidation)
                    .build();
        }

        @Override
        public String getAlias() {
            return this.alias;
        }

        @Override
        public String getTQuadroUrl() {
            return this.credentials.tquadroUrl;
        }

        @Override
        public void setTQuadroUrl(final String url) {
            this.credentials = ApiClientCredentials.builder()
                                                            .withTQuadroUrl(url)
                                                            .build();
        }
        @Override
        public String getUsername() {
            return this.credentials.username;
        }

        @Override
        public void setUsername(final String username) {
            this.credentials = ApiClientCredentials.builder(this.credentials)
                    .withUsername(username)
                    .build();
        }

        @Override
        public String getPassword() {
            return this.credentials.password;
        }

        @Override
        public void setPassword(final String password) {
            this.credentials = ApiClientCredentials.builder(this.credentials)
                    .withPassword(password)
                    .build();
        }


        @Override
        public void save() {
            // Purge cached client with old credentials
            ConnectionManager.this.vault.setCredentials(PREFIX + this.alias, this.asCredentials());
        }

        @Override
        public Optional<ConnectionValidationError> validate() {
            return ConnectionManager.this.clientManager.validate(ConnectionManager.asNutanixCredentials(this));
        }

        @Override
        public void delete() {
            ConnectionManager.this.vault.deleteCredentials(PREFIX + this.alias);
        }

        private Credentials asCredentials() {
            Map<String,String> credentialMap = new HashMap<>();
            credentialMap.put(TQUADRO_URL_KEY, this.credentials.tquadroUrl);
            credentialMap.put(IGNORE_SSL_CERTIFICATE_VALIDATION_KEY, String.valueOf(this.credentials.ignoreSslCertificateValidation));
            return new ImmutableCredentials(this.credentials.username, this.credentials.password, credentialMap);


        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                              .add("alias", this.alias)
                              .add(TQUADRO_URL_KEY, this.credentials.tquadroUrl)
                              .add("username", this.credentials.username)
                              .add("password", "******")
                              .add(IGNORE_SSL_CERTIFICATE_VALIDATION_KEY, this.credentials.ignoreSslCertificateValidation)
                    .toString();
        }
    }

    private void ensureCore() {
        if (this.runtimeInfo.getContainer() != Container.OPENNMS) {
            throw new IllegalStateException("Operation only allowed on OpenNMS instance");
        }
    }
}
