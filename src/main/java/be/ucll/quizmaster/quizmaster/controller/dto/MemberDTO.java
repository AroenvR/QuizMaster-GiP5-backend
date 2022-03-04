package be.ucll.quizmaster.quizmaster.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MemberDTO {

    @JsonProperty("email")
   private String email;

    @JsonProperty("username")
   private String username;

    @JsonProperty("password")
    private String password;

    public MemberDTO() {
    }

    public MemberDTO(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
