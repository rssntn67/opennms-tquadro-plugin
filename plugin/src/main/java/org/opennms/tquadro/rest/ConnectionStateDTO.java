package org.opennms.tquadro.rest;

import lombok.Data;

@Data
public class ConnectionStateDTO {
    private String alias;
    private String prismUrl;
    private boolean valid;
}