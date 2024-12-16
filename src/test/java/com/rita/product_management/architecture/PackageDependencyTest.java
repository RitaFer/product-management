package com.rita.product_management.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class PackageDependencyTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("com.rita.product_management");

    @Test
    public void noCyclicDependencies() {
        SlicesRuleDefinition.slices()
                .matching("com.rita.product_management.(*)..")
                .should().beFreeOfCycles()
                .check(classes);
    }

    @Test
    public void domainShouldNotDependOnApplicationOrInfrastructure() {
        classes()
                .that().resideInAPackage("..domain..")
                .should().onlyHaveDependentClassesThat()
                .resideInAnyPackage("..domain..", "..application..")
                .check(classes);
    }
}
