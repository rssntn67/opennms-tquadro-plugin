package org.opennms.tquadro.rest.impl;

import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface Mapper {
    Mapper NUTANIX_INSTANCE = Mappers.getMapper(Mapper.class);

    ClusterDTO sourceToTarget(Cluster source);
    HostDTO sourceToTarget(Host source);

    VMDTO sourceToTarget(VM source);
}
