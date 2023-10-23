package org.opennms.tquadro.configuration;

import java.util.List;

import org.opennms.integration.api.v1.config.poller.Monitor;
import org.opennms.integration.api.v1.config.poller.Package;
import org.opennms.integration.api.v1.config.poller.PollerConfigurationExtension;
import org.opennms.integration.api.xml.ClasspathPollerConfigurationLoader;

public class TQuadroPollerConfigurationExtension implements PollerConfigurationExtension {
        final PollerConfigurationExtension pollerConfiguration = new ClasspathPollerConfigurationLoader(TQuadroPollerConfigurationExtension.class, "",
                "poller-configuration.xml").getPollerConfiguration();

        @Override
        public List<Package> getPackages() {
            return pollerConfiguration.getPackages();
        }

        @Override
        public List<Monitor> getMonitors() {
            return pollerConfiguration.getMonitors();
        }
}
