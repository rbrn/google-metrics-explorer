package com.db.pwcc.tre.metrics.repository.search;

import com.db.pwcc.tre.metrics.domain.GoogleMetricGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link GoogleMetricGroup} entity.
 */
public interface GoogleMetricGroupSearchRepository extends ElasticsearchRepository<GoogleMetricGroup, String> {
}
