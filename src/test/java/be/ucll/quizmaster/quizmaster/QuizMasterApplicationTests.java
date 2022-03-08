package be.ucll.quizmaster.quizmaster;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class QuizMasterApplicationTests extends AbstractIntegrationTesting{
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Test
    void contextLoads() {
    }

}
