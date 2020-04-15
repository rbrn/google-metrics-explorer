package com.db.pwcc.tre.metrics.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GoogleProjectMapperTest {

    private GoogleProjectMapper googleProjectMapper;

    @BeforeEach
    public void setUp() {
        googleProjectMapper = new GoogleProjectMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(googleProjectMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(googleProjectMapper.fromId(null)).isNull();
    }
}
