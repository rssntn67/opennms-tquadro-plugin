package org.opennms.tquadro.shell;

import java.net.InetAddress;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Completion;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.karaf.shell.api.console.Session;
import org.apache.karaf.shell.support.table.Col;
import org.apache.karaf.shell.support.table.ShellTable;
import org.opennms.tquadro.connections.ConnectionManager;

@Command(scope = "opennms-tquadro", name = "check-ip", description = "Check TQuadro Ip Asset ", detailedDescription = "Check TQuadro if Ip is holded by Asset")
@Service
public class TQuadroCheckCommand implements Action {

    @Reference
    private ConnectionManager connectionManager;

    @Reference
    private Session session;

    @Argument(name = "alias", description = "Connection alias", required = true)
    @Completion(AliasCompleter.class)
    private String alias = null;

    @Argument(name = "ip", description = "Ip Address", required = true)
    private String ip = null;

    @Override
    public Object execute() throws Exception {
        final var client = this.connectionManager.getClient(this.alias);
        if (client.isEmpty()) {
            System.err.println("No connection with alias " + this.alias);
            return null;
        }

        final var table = new ShellTable()
                .size(session.getTerminal().getWidth() - 1)
                .column(new Col("asset").maxSize(36).bold(true));

        final var asset = client.get().getAssetByIpAddress(InetAddress.getByName(ip));
        final var row = table.addRow();
        row.addContent(asset);

        table.print(System.out, true);

        return null;
    }
}
