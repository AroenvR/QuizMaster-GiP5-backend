package be.ucll.quizmaster.quizmaster.controller.dto;

public class QuizDTO {

    private String quizTitle;

    private String quizCode;

    public QuizDTO() {
    }

    public QuizDTO(String quizTitle, String quizCode) {
        this.quizTitle = quizTitle;
        this.quizCode = quizCode;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getQuizCode() {
        return quizCode;
    }

    public void setQuizCode(String quizCode) {
        this.quizCode = quizCode;
    }

    @Override
    public String toString() {
        return "QuizDTO{" +
                "quizTitle='" + quizTitle + '\'' +
                ", quizCode='" + quizCode + '\'' +
                '}';
    }

}
