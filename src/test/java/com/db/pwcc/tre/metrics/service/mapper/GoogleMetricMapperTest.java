package com.db.pwcc.tre.metrics.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GoogleMetricMapperTest {

    private GoogleMetricMapper googleMetricMapper;

    @BeforeEach
    public void setUp() {
        googleMetricMapper = new GoogleMetricMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(googleMetricMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(googleMetricMapper.fromId(null)).isNull();
    }
}
