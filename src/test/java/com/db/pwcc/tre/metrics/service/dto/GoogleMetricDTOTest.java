package com.db.pwcc.tre.metrics.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.db.pwcc.tre.metrics.web.rest.TestUtil;

public class GoogleMetricDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoogleMetricDTO.class);
        GoogleMetricDTO googleMetricDTO1 = new GoogleMetricDTO();
        googleMetricDTO1.setId("id1");
        GoogleMetricDTO googleMetricDTO2 = new GoogleMetricDTO();
        assertThat(googleMetricDTO1).isNotEqualTo(googleMetricDTO2);
        googleMetricDTO2.setId(googleMetricDTO1.getId());
        assertThat(googleMetricDTO1).isEqualTo(googleMetricDTO2);
        googleMetricDTO2.setId("id2");
        assertThat(googleMetricDTO1).isNotEqualTo(googleMetricDTO2);
        googleMetricDTO1.setId(null);
        assertThat(googleMetricDTO1).isNotEqualTo(googleMetricDTO2);
    }
}
