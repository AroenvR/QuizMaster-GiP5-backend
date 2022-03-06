package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "member", schema = "quiz_master")
public class Member {

    @Id
    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "email")
    private String emailAddress;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    //constructors
    public Member(String emailAddress, String username, String password) {
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
    }

    public Member() {
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;

        Member member = (Member) o;

        if (!getEmailAddress().equals(member.getEmailAddress())) return false;
        return getUsername().equals(member.getUsername());
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", emailAddress='" + emailAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
