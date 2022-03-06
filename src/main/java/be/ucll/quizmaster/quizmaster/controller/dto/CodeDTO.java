package be.ucll.quizmaster.quizmaster.controller.dto;

public class CodeDTO {

    private String quizCode;

    public CodeDTO() {
    }


    public CodeDTO(String quizCode) {
        this.quizCode = quizCode;
    }

    public String getQuizCode() {
        return quizCode;
    }

    public void setQuizCode(String quizCode) {
        this.quizCode = quizCode;
    }

}
