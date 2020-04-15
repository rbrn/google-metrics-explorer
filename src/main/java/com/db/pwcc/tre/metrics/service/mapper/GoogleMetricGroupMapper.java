package com.db.pwcc.tre.metrics.service.mapper;


import com.db.pwcc.tre.metrics.domain.*;
import com.db.pwcc.tre.metrics.service.dto.GoogleMetricGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoogleMetricGroup} and its DTO {@link GoogleMetricGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoogleMetricGroupMapper extends EntityMapper<GoogleMetricGroupDTO, GoogleMetricGroup> {


    @Mapping(target = "googleMetrics", ignore = true)
    @Mapping(target = "removeGoogleMetric", ignore = true)
    GoogleMetricGroup toEntity(GoogleMetricGroupDTO googleMetricGroupDTO);

    default GoogleMetricGroup fromId(String id) {
        if (id == null) {
            return null;
        }
        GoogleMetricGroup googleMetricGroup = new GoogleMetricGroup();
        googleMetricGroup.setId(id);
        return googleMetricGroup;
    }
}
