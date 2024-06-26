
package org.opennms.tquadro.pollers;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.opennms.integration.api.v1.pollers.PollerRequest;
import org.opennms.integration.api.v1.pollers.PollerResult;
import org.opennms.integration.api.v1.pollers.ServicePoller;
import org.opennms.integration.api.v1.pollers.ServicePollerFactory;
import org.opennms.tquadro.client.api.ApiClientCredentials;
import org.opennms.tquadro.client.api.ApiClientService;
import org.opennms.tquadro.clients.ClientManager;
import org.opennms.tquadro.connections.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

public abstract class TQuadroAbstractPoller implements ServicePoller {
    private static final Logger LOG = LoggerFactory.getLogger(TQuadroAbstractPoller.class);
    private final ClientManager clientManager;

    public static final String ALIAS_KEY = "alias";
    public static final String CREATE_KEY = "create";
    public static final String LABEL_KEY = "label";
    public static final String AREA_KEY = "area";

    public static final String SYS_OBJECT_ID_KEY = "sys-object-id";
    public static final String SYS_NAME_KEY = "sys-name";
    public static final String SYS_LOCATION_KEY = "sys-location";
    public static final String SYS_CONTACT_KEY = "sys-contact";
    public static final String SYS_DESCRIPTION_KEY = "sys-description";


    protected TQuadroAbstractPoller(final ClientManager client) {
        this.clientManager = Objects.requireNonNull(client);
    }

    protected abstract CompletableFuture<PollerResult> poll(final Context context);

    @Override
    public final CompletableFuture<PollerResult> poll(final PollerRequest pollerRequest) {
            LOG.debug("poll: {} {}", pollerRequest.getAddress().getHostAddress(), pollerRequest.getServiceName());
            return this.poll(new Context(pollerRequest));
    }

    public static abstract class Factory<T extends TQuadroAbstractPoller> implements ServicePollerFactory<T> {

        private final ClientManager clientManager;

        private final ConnectionManager connectionManager;

        private final Class<T> clazz;

        protected Factory(final ClientManager client,
                          final ConnectionManager connectionManager,
                          final Class<T> clazz) {
            this.clientManager = Objects.requireNonNull(client);
            this.connectionManager = Objects.requireNonNull(connectionManager);

            this.clazz = Objects.requireNonNull(clazz);
        }

        protected abstract T createPoller(ClientManager clientManager);

        @Override
        public final T createPoller() {
            LOG.debug("Factory::createPoller -> class {}", getPollerClassName());
            return this.createPoller(this.clientManager);
        }

        @Override
        public final String getPollerClassName() {
            return this.clazz.getCanonicalName();
        }



        @Override
        public final Map<String, String> getRuntimeAttributes(final PollerRequest pollerRequest) {
            final var alias = Objects.requireNonNull(pollerRequest.getPollerAttributes().get(ALIAS_KEY), "Missing property: " + ALIAS_KEY);
            LOG.debug("Factory::getRuntimeAttributes -> alias: {}, class {}", alias, getPollerClassName());
            final var create = Objects.requireNonNull(pollerRequest.getPollerAttributes().get(CREATE_KEY), "Missing property: " + CREATE_KEY);
            LOG.debug("Factory::getRuntimeAttributes -> create: {}, class {}", create, getPollerClassName());
            final var label = Objects.requireNonNull(pollerRequest.getPollerAttributes().get(LABEL_KEY), "Missing property: " + LABEL_KEY);
            LOG.debug("Factory::getRuntimeAttributes -> label: {}, class {}", label, getPollerClassName());
            final var area = Objects.requireNonNull(pollerRequest.getPollerAttributes().get(AREA_KEY), "Missing property: " + AREA_KEY);
            LOG.debug("Factory::getRuntimeAttributes -> area: {}, class {}", area, getPollerClassName());
            final var connection = this.connectionManager.getConnection(alias)
                                                         .orElseThrow(() -> new NullPointerException("Connection not found for alias: " + alias));
            LOG.debug("Factory::getRuntimeAttributes -> connection: {}, class {}", connection, getPollerClassName());


            final var attrs = ImmutableMap.<String,String>builder();
            attrs.put(CREATE_KEY,create);
            attrs.put(LABEL_KEY,label);
            attrs.put(AREA_KEY,area);
            attrs.put(ConnectionManager.TQUADRO_URL_KEY, connection.getTQuadroUrl());
            attrs.put(ConnectionManager.USERNAME_KEY, connection.getUsername());
            attrs.put(ConnectionManager.PASSWORD_KEY, connection.getPassword());
            attrs.put(ConnectionManager.IGNORE_SSL_CERTIFICATE_VALIDATION_KEY, String.valueOf(connection.isIgnoreSslCertificateValidation()));
            final var sysoid = pollerRequest.getPollerAttributes().get(SYS_OBJECT_ID_KEY);
            LOG.debug("Factory::getRuntimeAttributes -> sysoid: {}, class {}", sysoid, getPollerClassName());
            if (sysoid != null) {
                attrs.put(SYS_OBJECT_ID_KEY,sysoid);
            }
            final var sysname = pollerRequest.getPollerAttributes().get(SYS_NAME_KEY);
            LOG.debug("Factory::getRuntimeAttributes -> sysname: {}, class {}", sysname, getPollerClassName());
            if (sysname != null) {
                attrs.put(SYS_NAME_KEY,sysname);
            }
            final var syscontact = pollerRequest.getPollerAttributes().get(SYS_CONTACT_KEY);
            LOG.debug("Factory::getRuntimeAttributes -> syscontact: {}, class {}", syscontact, getPollerClassName());
            if (syscontact != null) {
                attrs.put(SYS_CONTACT_KEY,syscontact);
            }
            final var syslocation = pollerRequest.getPollerAttributes().get(SYS_LOCATION_KEY);
            LOG.debug("Factory::getRuntimeAttributes -> syslocation: {}, class {}", syslocation, getPollerClassName());
            if (syslocation != null) {
                attrs.put(SYS_LOCATION_KEY,syslocation);
            }
            final var sysdescription = pollerRequest.getPollerAttributes().get(SYS_DESCRIPTION_KEY);
            LOG.debug("Factory::getRuntimeAttributes -> sysdescription: {}, class {}", sysdescription, getPollerClassName());
            if (sysdescription != null) {
                attrs.put(SYS_DESCRIPTION_KEY,sysdescription);
            }
            return attrs.build();
        }
    }

    public class Context {
        public final PollerRequest request;
        public Context(final PollerRequest request) {
            this.request = Objects.requireNonNull(request);

        }
        public String getLabel() {
            final var label= Objects.requireNonNull(this.request.getPollerAttributes().get(LABEL_KEY),
                    "Missing attribute: " + LABEL_KEY);
            LOG.debug("Context::getLabel: {}", label);
            return label;
        }

        public String getSysOid() {
            final var sysoid = Objects.requireNonNullElse(this.request.getPollerAttributes().get(SYS_OBJECT_ID_KEY), "");
            LOG.debug("Context::getSysOid: {}", sysoid);
            return sysoid;
        }

        public String getSysname() {
            final var sysname = Objects.requireNonNullElse(this.request.getPollerAttributes().get(SYS_NAME_KEY), "");
            LOG.debug("Context::getSysname: {}", sysname);
            return sysname;
        }

        public String getSysContact() {
            final var sysContact = Objects.requireNonNullElse( this.request.getPollerAttributes().get(SYS_CONTACT_KEY), "");
            LOG.debug("Context::getSysContact: {}", sysContact);
            return sysContact;
        }

        public String getSysLocation() {
            final var sysLocation = Objects.requireNonNullElse(this.request.getPollerAttributes().get(SYS_LOCATION_KEY), "");
            LOG.debug("Context::getSysLocation: {}", sysLocation);
            return sysLocation;
        }

        public String getSysDescription() {
            var sysDescription = Objects.requireNonNullElse(this.request.getPollerAttributes().get(SYS_DESCRIPTION_KEY), "");
            LOG.debug("Context::getSysDescription: {}", sysDescription);
            return sysDescription;
        }

        public String getArea() {
            var area = Objects.requireNonNull(this.request.getPollerAttributes().get(AREA_KEY), "");
            LOG.debug("Context::getArea: {}", area);
            return area;
        }

        public boolean getCreate() {
            final var create= Objects.requireNonNull(this.request.getPollerAttributes().get(CREATE_KEY),
                    "Missing attribute: " + CREATE_KEY);
            LOG.debug("Context::getCreate: {}", create);
            return Boolean.parseBoolean(create);
        }

        public ApiClientCredentials getClientCredentials() {
            final var prismUrl = Objects.requireNonNull(this.request.getPollerAttributes().get(ConnectionManager.TQUADRO_URL_KEY),
                                                               "Missing attribute: " + ConnectionManager.TQUADRO_URL_KEY);
            final var username = Objects.requireNonNull(this.request.getPollerAttributes().get(ConnectionManager.USERNAME_KEY),
                                                      "Missing attribute: " + ConnectionManager.USERNAME_KEY);

            final var password = Objects.requireNonNull(this.request.getPollerAttributes().get(ConnectionManager.PASSWORD_KEY),
                    "Missing attribute: " + ConnectionManager.PASSWORD_KEY);

            final var ignoreSslCertificateValidation = Objects.requireNonNull(this.request.getPollerAttributes().get(ConnectionManager.IGNORE_SSL_CERTIFICATE_VALIDATION_KEY),
                    "Missing attribute: " + ConnectionManager.IGNORE_SSL_CERTIFICATE_VALIDATION_KEY);


            ApiClientCredentials credentials = ApiClientCredentials.builder()
                    .withTQuadroUrl(prismUrl)
                    .withUsername(username)
                    .withPassword(password)
                    .withIgnoreSslCertificateValidation(Boolean.parseBoolean(ignoreSslCertificateValidation))
                    .build();

            LOG.debug("Context::getClientCredentials -> {}", credentials);

            return credentials;
        }

        public ApiClientService client() {
            return TQuadroAbstractPoller.this.clientManager.getClient(this.getClientCredentials());
        }
    }
}
