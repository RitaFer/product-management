package com.rita.product_management.architecture;

import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.domain.JavaClasses;
import org.junit.jupiter.api.Test;

public class LayeredArchitectureTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("com.rita.product_management");

    @Test
    public void layeredArchitectureShouldBeRespected() {
        Architectures.layeredArchitecture()
                .consideringAllDependencies()
                .layer("Presentation").definedBy("com.rita.product_management.presentation..")
                .layer("Application").definedBy("com.rita.product_management.application..")
                .layer("Domain").definedBy("com.rita.product_management.domain..")
                .layer("Infrastructure").definedBy("com.rita.product_management.infrastructure..")

                // Rules
                .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Presentation")
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure")
                .whereLayer("Infrastructure").mayNotAccessAnyLayer()
                .check(classes);
    }
}
