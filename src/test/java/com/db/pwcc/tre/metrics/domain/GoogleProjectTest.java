package com.db.pwcc.tre.metrics.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.db.pwcc.tre.metrics.web.rest.TestUtil;

public class GoogleProjectTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoogleProject.class);
        GoogleProject googleProject1 = new GoogleProject();
        googleProject1.setId("id1");
        GoogleProject googleProject2 = new GoogleProject();
        googleProject2.setId(googleProject1.getId());
        assertThat(googleProject1).isEqualTo(googleProject2);
        googleProject2.setId("id2");
        assertThat(googleProject1).isNotEqualTo(googleProject2);
        googleProject1.setId(null);
        assertThat(googleProject1).isNotEqualTo(googleProject2);
    }
}
