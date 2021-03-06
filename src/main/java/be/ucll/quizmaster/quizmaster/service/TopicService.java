package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.controller.dto.TopicDTO;
import be.ucll.quizmaster.quizmaster.model.Topic;
import be.ucll.quizmaster.quizmaster.repo.TopicRepo;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {
    private final Logger logger = LoggerFactory.getLogger(TopicService.class);

    private final TopicRepo topicRepo;

    @Autowired
    public TopicService(TopicRepo topicRepo) {
        this.topicRepo = topicRepo;
    }

    public List<TopicDTO> getAll() throws NotAuthenticatedException {
        List<TopicDTO> listToReturn = new ArrayList<>();

        for (Topic t: topicRepo.findAll()) {
            TopicDTO dtoToAdd = new TopicDTO(t.getName());
            listToReturn.add(dtoToAdd);
        }

        return listToReturn;
    }

    public boolean topicExists(String topic) {
        return topicRepo.existsByName(topic);
    }

    public Topic getTopic(String topic) {

        return topicRepo.getByName(topic);

    }

    public void saveTopic(Topic topic) {

        topicRepo.save(topic);
    }
}

