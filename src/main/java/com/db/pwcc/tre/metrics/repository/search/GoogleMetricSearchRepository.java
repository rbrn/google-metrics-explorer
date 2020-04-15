package com.db.pwcc.tre.metrics.repository.search;

import com.db.pwcc.tre.metrics.domain.GoogleMetric;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link GoogleMetric} entity.
 */
public interface GoogleMetricSearchRepository extends ElasticsearchRepository<GoogleMetric, String> {
}
