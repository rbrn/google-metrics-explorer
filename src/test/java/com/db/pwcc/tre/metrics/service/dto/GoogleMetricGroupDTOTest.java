package com.db.pwcc.tre.metrics.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.db.pwcc.tre.metrics.web.rest.TestUtil;

public class GoogleMetricGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoogleMetricGroupDTO.class);
        GoogleMetricGroupDTO googleMetricGroupDTO1 = new GoogleMetricGroupDTO();
        googleMetricGroupDTO1.setId("id1");
        GoogleMetricGroupDTO googleMetricGroupDTO2 = new GoogleMetricGroupDTO();
        assertThat(googleMetricGroupDTO1).isNotEqualTo(googleMetricGroupDTO2);
        googleMetricGroupDTO2.setId(googleMetricGroupDTO1.getId());
        assertThat(googleMetricGroupDTO1).isEqualTo(googleMetricGroupDTO2);
        googleMetricGroupDTO2.setId("id2");
        assertThat(googleMetricGroupDTO1).isNotEqualTo(googleMetricGroupDTO2);
        googleMetricGroupDTO1.setId(null);
        assertThat(googleMetricGroupDTO1).isNotEqualTo(googleMetricGroupDTO2);
    }
}
