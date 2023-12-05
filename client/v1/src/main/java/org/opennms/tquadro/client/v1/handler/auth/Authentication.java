package org.opennms.tquadro.client.v1.handler.auth;

import java.util.List;
import java.util.Map;

import org.opennms.tquadro.client.v1.handler.Pair;

public interface Authentication {
    /**
     * Apply authentication settings to header and query params.
     *
     * @param queryParams List of query parameters
     * @param headerParams Map of header parameters
     */
    void applyToParams(List<Pair> queryParams, Map<String, String> headerParams);
}
