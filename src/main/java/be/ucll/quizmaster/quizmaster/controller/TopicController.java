package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.controller.dto.TopicDTO;
import be.ucll.quizmaster.quizmaster.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("topics")
public class TopicController {

    private final Logger logger = LoggerFactory.getLogger(TopicController.class);

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping()
    public ResponseEntity<Set<TopicDTO>> getAll(){

        Set<TopicDTO> topics = new HashSet<>();
        topics.add(new TopicDTO("COD"));
        topics.add(new TopicDTO("WOW"));
        topics.add(new TopicDTO("CSGO"));
        topics.add(new TopicDTO("Geography"));
        return ResponseEntity.status(HttpStatus.OK).body(topics);

    }

}

