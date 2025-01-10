package com.rita.product_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProductManagementApplication {
    /*
     * TODO: Project Improvements
     *
     * 1. Implement @Annotations for auditing:
     *    - Use annotations like @CreatedBy, @CreatedDate, @LastModifiedBy, and @LastModifiedDate
     *      to automate the tracking of entity metadata.
     *
     * 2. Restrict product creation to admin:
     *    - Ensure that only administrators can add new products, as stockists should not have
     *      permission to modify certain fields.
     *
     * 3. Access rule enforcement for stockists:
     *    - Guarantee restricted access for stockists to limited data fields in the report endpoint.
     *
     * 4. User name limitation per LGPD:
     *    - Limit the exposure of user names to comply with LGPD (Brazilian General Data Protection Law).
     *
     * 5. Handle Jackson Time serialization/deserialization issues:
     *    - Resolve issues caused by JacksonTime in the serialization and deserialization process.
     *    - After resolution, remove the need for mapping the Product object in the customizable
     *      method within the GetProductListUseCase class.
     *
     * 6. Fix dependency vulnerabilities:
     *    - Address four reported vulnerabilities in project dependencies to improve security.
     *
     * 7. Expand unit test coverage:
     *    - Add more unit tests beyond the primary scope of the project to ensure better coverage and reliability.
     *
     * 8. Implement containerized tests:
     *    - Use container-based environments for testing (e.g., using Docker) to better replicate production setups.
     *
     * 9. Add controller tests:
     *    - Write and implement tests for all controller endpoints to ensure the correctness of API behavior.
     */

    public static void main(String[] args) {
        SpringApplication.run(ProductManagementApplication.class, args);
    }

}
