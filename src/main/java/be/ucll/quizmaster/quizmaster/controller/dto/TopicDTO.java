package be.ucll.quizmaster.quizmaster.controller.dto;

public class TopicDTO {

    private String name;

    public TopicDTO(String name) {
        this.name = name;
    }

    public TopicDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TopicDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
