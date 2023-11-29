package org.opennms.tquadro.plugin;

import lombok.Data;

@Data
public class ConnectionStateDTO {
    private String alias;
    private String prismUrl;
    private boolean valid;
}