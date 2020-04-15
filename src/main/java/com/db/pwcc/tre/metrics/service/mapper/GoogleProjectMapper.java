package com.db.pwcc.tre.metrics.service.mapper;


import com.db.pwcc.tre.metrics.domain.*;
import com.db.pwcc.tre.metrics.service.dto.GoogleProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoogleProject} and its DTO {@link GoogleProjectDTO}.
 */
@Mapper(componentModel = "spring", uses = {GoogleMetricMapper.class})
public interface GoogleProjectMapper extends EntityMapper<GoogleProjectDTO, GoogleProject> {

    @Mapping(source = "googleMetric.id", target = "googleMetricId")
    GoogleProjectDTO toDto(GoogleProject googleProject);

    @Mapping(source = "googleMetricId", target = "googleMetric")
    GoogleProject toEntity(GoogleProjectDTO googleProjectDTO);

    default GoogleProject fromId(String id) {
        if (id == null) {
            return null;
        }
        GoogleProject googleProject = new GoogleProject();
        googleProject.setId(id);
        return googleProject;
    }
}
