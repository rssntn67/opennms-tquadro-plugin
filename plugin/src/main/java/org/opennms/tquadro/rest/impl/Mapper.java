package org.opennms.tquadro.rest.impl;

import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface Mapper {
    Mapper TQUADRO_INSTANCE = Mappers.getMapper(Mapper.class);

}
