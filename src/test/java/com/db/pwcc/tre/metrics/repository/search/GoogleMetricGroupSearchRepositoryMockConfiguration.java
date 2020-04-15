package com.db.pwcc.tre.metrics.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link GoogleMetricGroupSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GoogleMetricGroupSearchRepositoryMockConfiguration {

    @MockBean
    private GoogleMetricGroupSearchRepository mockGoogleMetricGroupSearchRepository;

}
