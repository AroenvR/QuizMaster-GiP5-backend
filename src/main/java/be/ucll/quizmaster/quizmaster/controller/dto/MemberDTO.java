package be.ucll.quizmaster.quizmaster.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MemberDTO {

    @JsonProperty("emailAddress")
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

    private MemberDTO(Builder builder) {
        setEmail(builder.email);
        setUsername(builder.username);
        setPassword(builder.password);
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

    public static final class Builder {
        private String email;
        private String username;
        private String password;

        public Builder() {
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public MemberDTO build() {
            return new MemberDTO(this);
        }
    }
}
