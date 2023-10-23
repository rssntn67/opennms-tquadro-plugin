package org.opennms.tquadro.pollers;

import org.opennms.tquadro.clients.ClientManager;
import org.opennms.tquadro.connections.ConnectionManager;

public class TQuadroPollerFactory extends TQuadroAbstractPoller.Factory<TQuadroPoller> {

    public TQuadroPollerFactory(final ClientManager clientManager,
                                final ConnectionManager connectionManager) {
        super(clientManager, connectionManager, TQuadroPoller.class);
    }

    @Override
    protected TQuadroPoller createPoller(final ClientManager clientManager) {
        return new TQuadroPoller(clientManager);
    }
}
