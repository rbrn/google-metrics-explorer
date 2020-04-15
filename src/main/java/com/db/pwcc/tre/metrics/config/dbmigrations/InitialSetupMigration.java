package com.db.pwcc.tre.metrics.config.dbmigrations;

import com.db.pwcc.tre.metrics.domain.Authority;
import com.db.pwcc.tre.metrics.domain.GoogleMetric;
import com.db.pwcc.tre.metrics.domain.GoogleMetricGroup;
import com.db.pwcc.tre.metrics.domain.User;
import com.db.pwcc.tre.metrics.security.AuthoritiesConstants;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.google.api.MetricDescriptor;
import com.google.cloud.monitoring.v3.MetricServiceClient;
import com.google.cloud.monitoring.v3.MetricServiceClient.ListMetricDescriptorsPagedResponse;
import com.google.monitoring.v3.ListMetricDescriptorsRequest;
import com.google.monitoring.v3.ProjectName;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Creates the initial database setup.
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);
        mongoTemplate.save(adminAuthority);
        mongoTemplate.save(userAuthority);
    }

    @ChangeSet(order = "02", author = "initiator", id = "02-addUsers")
    public void addUsers(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);

        User systemUser = new User();
        systemUser.setId("user-0");
        systemUser.setLogin("system");
        systemUser.setPassword("$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG");
        systemUser.setFirstName("");
        systemUser.setLastName("System");
        systemUser.setEmail("system@localhost");
        systemUser.setActivated(true);
        systemUser.setLangKey("en");
        systemUser.setCreatedBy(systemUser.getLogin());
        systemUser.setCreatedDate(Instant.now());
        systemUser.getAuthorities().add(adminAuthority);
        systemUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(systemUser);

        User anonymousUser = new User();
        anonymousUser.setId("user-1");
        anonymousUser.setLogin("anonymoususer");
        anonymousUser.setPassword("$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO");
        anonymousUser.setFirstName("Anonymous");
        anonymousUser.setLastName("User");
        anonymousUser.setEmail("anonymous@localhost");
        anonymousUser.setActivated(true);
        anonymousUser.setLangKey("en");
        anonymousUser.setCreatedBy(systemUser.getLogin());
        anonymousUser.setCreatedDate(Instant.now());
        mongoTemplate.save(anonymousUser);

        User adminUser = new User();
        adminUser.setId("user-2");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setFirstName("admin");
        adminUser.setLastName("Administrator");
        adminUser.setEmail("admin@localhost");
        adminUser.setActivated(true);
        adminUser.setLangKey("en");
        adminUser.setCreatedBy(systemUser.getLogin());
        adminUser.setCreatedDate(Instant.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(adminUser);

        User userUser = new User();
        userUser.setId("user-3");
        userUser.setLogin("user");
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setFirstName("");
        userUser.setLastName("User");
        userUser.setEmail("user@localhost");
        userUser.setActivated(true);
        userUser.setLangKey("en");
        userUser.setCreatedBy(systemUser.getLogin());
        userUser.setCreatedDate(Instant.now());
        userUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(userUser);
    }

    @ChangeSet(order = "04", author = "initiator", id = "03-addDescriptors")
    public void addDescriptors(MongoTemplate mongoTemplate) throws IOException {
        String projectId = "dev1-onb-dbhc-monitoring-e561";

        List<String> groups = asList("instance");
        final MetricServiceClient client = MetricServiceClient.create();
        ProjectName name = ProjectName.of(projectId);

        ListMetricDescriptorsRequest request =
            ListMetricDescriptorsRequest.newBuilder().setName(name.toString()).build();
        ListMetricDescriptorsPagedResponse response = client.listMetricDescriptors(request);

        System.out.println("Listing descriptors: ");


        for (MetricDescriptor d : response.iterateAll()) {
            GoogleMetricGroup googleMetricGroup = getMetricGroup(d, mongoTemplate);
            if (groups.contains(googleMetricGroup.getName())) {
                System.out.println(d.getName() + " " + d.getDisplayName());
                GoogleMetric metric = new GoogleMetric();
                metric.setName(d.getType());
                metric.setDescription(d.getDescription());
                metric.setMetricGroup(googleMetricGroup);
                mongoTemplate.save(metric);
            } else {
                System.out.println(d.getName() + " excluded");
            }
        }
    }

    private GoogleMetricGroup getMetricGroup(MetricDescriptor metricDescriptor, MongoTemplate mongoTemplate) {
        String name = metricDescriptor.getType().split("/")[1];

        Query query = Query.query(Criteria.where("name").is(name));
        GoogleMetricGroup googleMetricGroup = mongoTemplate.findOne(query, GoogleMetricGroup.class);
        if (googleMetricGroup == null) {
            GoogleMetricGroup metricGroup = new GoogleMetricGroup();
            metricGroup.setName(name);
            return mongoTemplate.save(metricGroup);
        } else
            return googleMetricGroup;
    }
}
