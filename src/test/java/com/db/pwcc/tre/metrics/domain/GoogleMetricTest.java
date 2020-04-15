package com.db.pwcc.tre.metrics.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.db.pwcc.tre.metrics.web.rest.TestUtil;

public class GoogleMetricTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoogleMetric.class);
        GoogleMetric googleMetric1 = new GoogleMetric();
        googleMetric1.setId("id1");
        GoogleMetric googleMetric2 = new GoogleMetric();
        googleMetric2.setId(googleMetric1.getId());
        assertThat(googleMetric1).isEqualTo(googleMetric2);
        googleMetric2.setId("id2");
        assertThat(googleMetric1).isNotEqualTo(googleMetric2);
        googleMetric1.setId(null);
        assertThat(googleMetric1).isNotEqualTo(googleMetric2);
    }
}
