package be.ucll.quizmaster.quizmaster.fake_data;

import be.ucll.quizmaster.quizmaster.controller.dto.QuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("fake-questions")
public class FakeQuestionController {
    private final Logger logger = LoggerFactory.getLogger(FakeQuestionController.class);

    // A really bad representation of DB data.
    @GetMapping("/{topic}")
    public ResponseEntity<Set<QuestionDTO>> getAllForTopic(@PathVariable String topic) {

        // Set of questions for Games,
        Set<QuestionDTO> gamingSet = new HashSet<>();

        List<String> trueOrFalse = new ArrayList<>();
        trueOrFalse.add("true");
        trueOrFalse.add("false");

        QuestionDTO wow = new QuestionDTO.Builder()
                .topic("Gaming")
                .questionString("Is World of Warcraft an MMO?")
                .type(2)
                .description("Select the correct answer.")
                .quizTitle("Quiz_Title")
                .isBreak(false)
                .answers(trueOrFalse)
                .build();

        List<String> csGoAnswers = new ArrayList<>();
        csGoAnswers.add("19");
        csGoAnswers.add("34");
        csGoAnswers.add("69");
        csGoAnswers.add("42");
        csGoAnswers.add("28");

        QuestionDTO csgo = new QuestionDTO.Builder()
                .topic("Gaming")
                .questionString("How many guns are purchasable in CS:GO?")
                .type(1)
                .description("Select the correct answer.")
                .quizTitle("Quiz_Title")
                .isBreak(false)
                .answers(csGoAnswers)
                .build();

        List<String> lolAnswers = new ArrayList<>();
        lolAnswers.add("baker");
        lolAnswers.add("taker");
        lolAnswers.add("shaker");
        lolAnswers.add("maker");

        QuestionDTO lol = new QuestionDTO.Builder()
                .topic("Gaming")
                .questionString("Faker, Faker play ...!")
                .type(3)
                .description("Fill in your answer.")
                .quizTitle("Quiz_Title")
                .isBreak(false)
                .answers(lolAnswers)
                .build();

        gamingSet.add(wow);
        gamingSet.add(csgo);
        gamingSet.add(lol);

        // Set of questions for Nature.
        Set<QuestionDTO> natureSet = new HashSet<>();

        QuestionDTO cows = new QuestionDTO.Builder()
                .topic("Nature")
                .questionString("Cows kill more people than sharks.")
                .type(2)
                .description("Select the correct answer.")
                .quizTitle("Quiz_Title")
                .isBreak(false)
                .answers(trueOrFalse)
                .build();

        natureSet.add(cows);


        // Set of questions for Space.
        Set<QuestionDTO> spaceSet = new HashSet<>();

        QuestionDTO silence = new QuestionDTO.Builder()
                .topic("Space")
                .questionString("Is space completely silent?")
                .type(2)
                .description("Select the correct answer.")
                .quizTitle("Quiz_Title")
                .isBreak(false)
                .answers(trueOrFalse)
                .build();

        spaceSet.add(silence);

        // Sending the requested set.
        switch(topic) {
            case "Gaming":
                return ResponseEntity.status(HttpStatus.OK).body(gamingSet);

            case "Nature":
                return ResponseEntity.status(HttpStatus.OK).body(natureSet);

            case "Space":
                return ResponseEntity.status(HttpStatus.OK).body(spaceSet);

            default:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
    }
}
