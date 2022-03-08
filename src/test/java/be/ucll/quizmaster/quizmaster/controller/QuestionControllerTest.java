package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.AbstractIntegrationTesting;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateAnswerDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuestionDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionControllerTest extends AbstractIntegrationTesting {

    private final Logger logger = LoggerFactory.getLogger(QuestionControllerTest.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createQuestion() throws Exception {
        CreateAnswerDTO answer = new CreateAnswerDTO.Builder()
                .answerString("true")
                .correct(true)
                .build();

        Set<CreateAnswerDTO> answers = new HashSet();
        answers.add(answer);

        CreateQuestionDTO question = new CreateQuestionDTO.Builder()
                .questionString("Is dit vraag 1?")
                .description("True or False.")
                .topic("Quizzes")
                .type(1)
                .answersDTOs(answers)
                .build();



        MvcResult mvcPost = this.mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(question)))
//                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        CreateQuestionDTO savedQuestion = fromMvcResult(mvcPost, CreateQuestionDTO.class);

        logger.debug(savedQuestion.getQuestionString());
        logger.debug(savedQuestion.getDescription());
        logger.debug(savedQuestion.getTopic());
    }
}