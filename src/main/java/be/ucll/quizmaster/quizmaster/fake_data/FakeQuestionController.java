package be.ucll.quizmaster.quizmaster.fake_data;

import be.ucll.quizmaster.quizmaster.controller.dto.ChooseQuestionDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.QuestionDTO;
import liquibase.pro.packaged.Q;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("fake-questions")
public class FakeQuestionController {
    private final Logger logger = LoggerFactory.getLogger(FakeQuestionController.class);

    // A really bad representation of DB data.
    int counter = 0;
    @GetMapping()
    private ResponseEntity<?> getNextQuestion(@RequestParam(value = "get-next") String answerToPrevious) {
        logger.error("Counter is at: " + counter);
        logger.error("Answer to previous: " + answerToPrevious);

        Set<String> answers = new HashSet<>();
        answers.add("4");
        answers.add("8");
        answers.add("16");
        answers.add("128");

        QuestionDTO multipleChoice = new QuestionDTO.Builder()
                .isBreak(false)
                .quizTitle("Fake Quiz")
                .type(1)
                .questionString("How much is 2 + 2?")
                .description("Select the correct answer!")
                .topic("Mathematics")
                .answers(answers)
                .build();

        answers = new HashSet<>();
        answers.add("4");

        QuestionDTO fillInTheBlank = new QuestionDTO.Builder()
                .isBreak(false)
                .quizTitle("Fake Quiz")
                .type(3)
                .questionString("2 + 2 = ?")
                .description("Fill in the correct answer!")
                .topic("Mathematics")
                .answers(answers)
                .build();

        answers = new HashSet<>();
        answers.add("true");
        answers.add("false");
        QuestionDTO trueOrFalse = new QuestionDTO.Builder()
                .isBreak(false)
                .quizTitle("Fake Quiz")
                .type(2)
                .questionString("2 + 2 = 4")
                .description("Select the correct answer!")
                .topic("Mathematics")
                .answers(answers)
                .build();

        QuestionDTO breakQuestion = new QuestionDTO.Builder()
                .isBreak(true)
                .quizTitle("Fake Quiz")
                .type(0)
                .questionString("")
                .description("")
                .topic("")
                .answers(new HashSet<>())
                .build();

        if (counter == 0) {
            counter++;
            return ResponseEntity.status(HttpStatus.OK).body(multipleChoice);
        }
        if (counter == 1) {
            counter++;
            return ResponseEntity.status(HttpStatus.OK).body(trueOrFalse);
        }
        if (counter == 2) {
            counter++;
            return ResponseEntity.status(HttpStatus.OK).body(breakQuestion);
        }
        if (counter == 3) {
            counter++;
            return ResponseEntity.status(HttpStatus.OK).body(fillInTheBlank);
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("");
    }

    // A really bad representation of DB data.
    @GetMapping("/{topic}")
    public ResponseEntity<Set<ChooseQuestionDTO>> getAllForTopic(@PathVariable String topic) {

        // Set of questions for Games,
        Set<ChooseQuestionDTO> gamingSet = new HashSet<>();

        Set<String> trueOrFalse = new HashSet<>();
        trueOrFalse.add("true");
        trueOrFalse.add("false");

        ChooseQuestionDTO wow = new ChooseQuestionDTO.Builder()
                .questionId(1L)
                .topic("Gaming")
                .questionString("Is World of Warcraft an MMO?")
                .type(2)
                .description("Select the correct answer.")
                .answers(trueOrFalse)
                .build();

        Set<String> csGoAnswers = new HashSet<>();
        csGoAnswers.add("19");
        csGoAnswers.add("34");
        csGoAnswers.add("69");
        csGoAnswers.add("42");
        csGoAnswers.add("28");

        ChooseQuestionDTO csgo = new ChooseQuestionDTO.Builder()
                .questionId(2L)
                .topic("Gaming")
                .questionString("How many guns are purchasable in CS:GO?")
                .type(1)
                .description("Select the correct answer.")
                .answers(csGoAnswers)
                .build();

        Set<String> lolAnswers = new HashSet<>();
        lolAnswers.add("baker");
        lolAnswers.add("taker");
        lolAnswers.add("shaker");
        lolAnswers.add("maker");

        ChooseQuestionDTO lol = new ChooseQuestionDTO.Builder()
                .questionId(3L)
                .topic("Gaming")
                .questionString("Faker, Faker play ...!")
                .type(3)
                .description("Fill in your answer.")
                .answers(lolAnswers)
                .build();

        gamingSet.add(wow);
        gamingSet.add(csgo);
        gamingSet.add(lol);

        // Set of questions for Nature.
        Set<ChooseQuestionDTO> natureSet = new HashSet<>();

        ChooseQuestionDTO cows = new ChooseQuestionDTO.Builder()
                .questionId(4L)
                .topic("Nature")
                .questionString("Cows kill more people than sharks.")
                .type(2)
                .description("Select the correct answer.")
                .answers(trueOrFalse)
                .build();

        natureSet.add(cows);


        // Set of questions for Space.
        Set<ChooseQuestionDTO> spaceSet = new HashSet<>();

        ChooseQuestionDTO silence = new ChooseQuestionDTO.Builder()
                .questionId(5L)
                .topic("Space")
                .questionString("Is space completely silent?")
                .type(2)
                .description("Select the correct answer.")
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
