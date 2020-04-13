package com.db.pwcc.tre.metrics;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.db.pwcc.tre.metrics");

        noClasses()
            .that()
                .resideInAnyPackage("com.db.pwcc.tre.metrics.service..")
            .or()
                .resideInAnyPackage("com.db.pwcc.tre.metrics.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.db.pwcc.tre.metrics.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
