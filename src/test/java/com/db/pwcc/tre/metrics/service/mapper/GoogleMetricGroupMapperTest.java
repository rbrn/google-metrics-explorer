package com.db.pwcc.tre.metrics.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GoogleMetricGroupMapperTest {

    private GoogleMetricGroupMapper googleMetricGroupMapper;

    @BeforeEach
    public void setUp() {
        googleMetricGroupMapper = new GoogleMetricGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(googleMetricGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(googleMetricGroupMapper.fromId(null)).isNull();
    }
}
