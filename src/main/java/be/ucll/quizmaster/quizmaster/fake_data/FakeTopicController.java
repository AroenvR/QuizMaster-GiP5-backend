package be.ucll.quizmaster.quizmaster.fake_data;

import be.ucll.quizmaster.quizmaster.controller.dto.TopicDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("fake-topics")
public class FakeTopicController {
    private final Logger logger = LoggerFactory.getLogger(FakeTopicController.class);

    // A really bad representation of DB data.
    @GetMapping()
    public ResponseEntity<Set<TopicDTO>> getAll(){
        Set<TopicDTO> topics = new HashSet<>();
        topics.add(new TopicDTO("Gaming"));
        topics.add(new TopicDTO("Nature"));
        topics.add(new TopicDTO("Space"));
        return ResponseEntity.status(HttpStatus.OK).body(topics);

    }
}
