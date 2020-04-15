package com.db.pwcc.tre.metrics.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.db.pwcc.tre.metrics.web.rest.TestUtil;

public class GoogleMetricGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoogleMetricGroup.class);
        GoogleMetricGroup googleMetricGroup1 = new GoogleMetricGroup();
        googleMetricGroup1.setId("id1");
        GoogleMetricGroup googleMetricGroup2 = new GoogleMetricGroup();
        googleMetricGroup2.setId(googleMetricGroup1.getId());
        assertThat(googleMetricGroup1).isEqualTo(googleMetricGroup2);
        googleMetricGroup2.setId("id2");
        assertThat(googleMetricGroup1).isNotEqualTo(googleMetricGroup2);
        googleMetricGroup1.setId(null);
        assertThat(googleMetricGroup1).isNotEqualTo(googleMetricGroup2);
    }
}
