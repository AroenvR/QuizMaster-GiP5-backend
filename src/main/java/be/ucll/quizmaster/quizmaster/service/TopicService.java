package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.Topic;
import be.ucll.quizmaster.quizmaster.repo.TopicRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    private final Logger logger = LoggerFactory.getLogger(TopicService.class);

    private final TopicRepo topicRepo;

    @Autowired
    public TopicService(TopicRepo topicRepo) {
        this.topicRepo = topicRepo;
    }

    public boolean topicExists(String topic) {
        return topicRepo.existsByName(topic);
    }

    public Topic getTopic(String topic) {

        return topicRepo.getByName(topic);

    }
}

