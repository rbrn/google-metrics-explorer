package com.db.pwcc.tre.metrics.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.db.pwcc.tre.metrics.web.rest.TestUtil;

public class GoogleProjectDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoogleProjectDTO.class);
        GoogleProjectDTO googleProjectDTO1 = new GoogleProjectDTO();
        googleProjectDTO1.setId("id1");
        GoogleProjectDTO googleProjectDTO2 = new GoogleProjectDTO();
        assertThat(googleProjectDTO1).isNotEqualTo(googleProjectDTO2);
        googleProjectDTO2.setId(googleProjectDTO1.getId());
        assertThat(googleProjectDTO1).isEqualTo(googleProjectDTO2);
        googleProjectDTO2.setId("id2");
        assertThat(googleProjectDTO1).isNotEqualTo(googleProjectDTO2);
        googleProjectDTO1.setId(null);
        assertThat(googleProjectDTO1).isNotEqualTo(googleProjectDTO2);
    }
}
