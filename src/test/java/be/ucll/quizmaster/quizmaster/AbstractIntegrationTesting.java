package be.ucll.quizmaster.quizmaster;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@Transactional
@ActiveProfiles("unit-test")
public abstract class AbstractIntegrationTesting {

//    private static PostgreSQLContainer POSTGRES;
//
//    static {
//        POSTGRES = new PostgreSQLContainer("postgres:latest")
//                .withDatabaseName("manager")
//                .withUsername("postgres")
//                .withPassword("manager");
//        POSTGRES.start();
//    }
//
//    @DynamicPropertySource
//    static void dynamicProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
//        dynamicPropertyRegistry.add("spring.datasource.username", () -> "postgres");
//        dynamicPropertyRegistry.add("spring.datasource.password", () -> "manager");
//        dynamicPropertyRegistry.add("spring.datasource.url",
//                () -> String.format(
//                        "jdbc:postgresql://localhost:%d/postgres",
//                        POSTGRES.getMappedPort(5432)
//                )
//        );
//    }

    public static String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String input, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(input, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromMvcResult(MvcResult result, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(result.getResponse().getContentAsString(), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
