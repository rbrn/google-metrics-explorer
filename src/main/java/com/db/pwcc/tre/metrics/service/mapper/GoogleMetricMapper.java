package com.db.pwcc.tre.metrics.service.mapper;


import com.db.pwcc.tre.metrics.domain.*;
import com.db.pwcc.tre.metrics.service.dto.GoogleMetricDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoogleMetric} and its DTO {@link GoogleMetricDTO}.
 */
@Mapper(componentModel = "spring", uses = {GoogleMetricGroupMapper.class})
public interface GoogleMetricMapper extends EntityMapper<GoogleMetricDTO, GoogleMetric> {

    @Mapping(source = "metricGroup.id", target = "metricGroupId")
    @Mapping(source = "metricGroup.name", target = "metricGroupName")
    GoogleMetricDTO toDto(GoogleMetric googleMetric);

    @Mapping(source = "metricGroupId", target = "metricGroup")
    GoogleMetric toEntity(GoogleMetricDTO googleMetricDTO);

    default GoogleMetric fromId(String id) {
        if (id == null) {
            return null;
        }
        GoogleMetric googleMetric = new GoogleMetric();
        googleMetric.setId(id);
        return googleMetric;
    }
}
