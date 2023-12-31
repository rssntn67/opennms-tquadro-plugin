package org.opennms.tquadro.plugin;

import lombok.Data;

@Data
public class ConnectionListElementDTO {
    private String alias;
    private String prismUrl;
    private String username;
    private Integer length;
    private Boolean ignoreSslCertificateValidation;
}
