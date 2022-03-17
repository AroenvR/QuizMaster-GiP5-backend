package be.ucll.quizmaster.quizmaster;

import be.ucll.quizmaster.quizmaster.controller.dto.MemberDTO;
import be.ucll.quizmaster.quizmaster.repo.MemberRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ActiveProfiles("")
public abstract class AbstractIntegrationTesting {

    private final Logger logger = LoggerFactory.getLogger(AbstractIntegrationTesting.class);

    protected MockMvc mockMvc;
    private MvcResult mvcPost;

    @Autowired
    private WebApplicationContext webAppCont;
    protected MemberDTO wout;
    protected MemberDTO oderick;

    @Autowired
    MemberRepo memberRepo;

    private static PostgreSQLContainer POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer("postgres:latest")
                .withDatabaseName("manager")
                .withUsername("postgres")
                .withPassword("manager");
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.username", () -> "postgres");
        dynamicPropertyRegistry.add("spring.datasource.password", () -> "manager");
        dynamicPropertyRegistry.add("spring.datasource.url",
                () -> String.format(
                        "jdbc:postgresql://localhost:%d/postgres",
                        POSTGRES.getMappedPort(5432)
                )
        );
    }

    @BeforeEach
    private void setup() throws Exception {
        MemberDTO woutDTO = new MemberDTO("wout@bosteels.eu", "woutb", "TESTTEST");

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webAppCont).apply(springSecurity()).build();

        mvcPost = this.mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .content(toJson(woutDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        wout = fromMvcResult(mvcPost, MemberDTO.class);


        MemberDTO oderickDTO = new MemberDTO("oderick@vandongen.be", "test", "test");

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webAppCont).apply(springSecurity()).build();

        mvcPost = this.mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .content(toJson(oderickDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        oderick = fromMvcResult(mvcPost, MemberDTO.class);
    }


    @AfterEach
    private void cleanup() throws Exception {

        memberRepo.deleteByEmailAddress("wout@bosteels.eu");
        memberRepo.deleteByEmailAddress("oderick@vandongen.be");

    }


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

    public <T> T fromMvcResult(MvcResult result, Class<T> clazz) {
        try {

            String response = result.getResponse().getContentAsString();
            logger.debug(response);
            T t = new ObjectMapper().readValue(response, clazz);

            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
