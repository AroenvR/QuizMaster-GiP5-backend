package be.ucll.quizmaster.quizmaster.controller.dto;

public class QuizDTO {

    private String title;

    private String hostName;

    public QuizDTO(String title, String hostName) {
        this.title = title;
        this.hostName = hostName;
    }

    public QuizDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
