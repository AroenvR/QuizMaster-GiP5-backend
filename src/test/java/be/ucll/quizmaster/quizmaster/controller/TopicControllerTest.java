package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.AbstractIntegrationTesting;
import be.ucll.quizmaster.quizmaster.controller.dto.MemberDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.TopicDTO;
import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.model.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TopicControllerTest extends AbstractIntegrationTesting{
    //temp logger
    private final Logger logger = LoggerFactory.getLogger(TopicControllerTest.class);

    @Autowired
    private TopicController topicController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private MvcResult mvcResult;

    String memberEmail = "test@test.test";
    String memberName = "test";
    String memberPass = "test";

    MemberDTO member = new MemberDTO.Builder()
            .email(memberEmail)
            .username(memberName)
            .password(memberPass)
            .build();

    @Autowired
    MemberController memberController;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();

        ResponseEntity<?> member = memberController.createMember(this.member);

//        MvcResult mvcPost = this.mockMvc.perform(MockMvcRequestBuilders.post("/members")
//                        .content(toJson(member))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//
//        logger.error(mvcPost.toString());
//        logger.error(mvcPost.getResponse().toString());
//        logger.error(mvcPost.getResponse().getStatus() + "");
//
//        Member mvcTestMember = fromMvcResult(mvcPost, Member.class);
//
//       logger.error(mvcTestMember.getUsername() + " " + mvcTestMember.getEmailAddress() + " " + mvcTestMember.getPassword());

        logger.error(member.getStatusCode() + " " + member.getBody().toString());
    }

    @Test
    void createTopic() throws Exception {
        //See CreateQuestion()
    }

    @Test
    void getAllTopics() throws Exception {
        //given
//        TopicDTO newTopic1 = new TopicDTO("Tests1");
//        TopicDTO newTopic2 = new TopicDTO("Tests2");
//        TopicDTO newTopic3 = new TopicDTO("Tests");
//        TopicDTO newTopic4 = new TopicDTO("Tests");
//
//        List<TopicDTO> allTopics = new ArrayList();
//        allTopics.add(newTopic1);
//        allTopics.add(newTopic2);
//        allTopics.add(newTopic3);
//        allTopics.add(newTopic4);
    TopicDTO topic = new TopicDTO("COD");

        //when
        this.mockMvc.perform(MockMvcRequestBuilders.get("/topics")
                        .with(httpBasic(memberEmail, memberPass))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<Set<TopicDTO>> rEntity = topicController.getAll();

        //then
        assertEquals(rEntity.getStatusCodeValue(), HttpStatus.OK.value());
        //assertEquals(rEntity.getBody().toString(), "[TopicDTO{name='Geography'}, TopicDTO{name='COD'}, TopicDTO{name='CSGO'}, TopicDTO{name='WOW'}]");
    }
}