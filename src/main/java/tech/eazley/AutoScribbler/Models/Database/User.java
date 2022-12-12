package tech.eazley.AutoScribbler.Models.Database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity()
@Table(name = "users")
public class User {
    @Id
    @Column
    @GeneratedValue
    private int id;

    @Column
    private String email;
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public User()
    {

    }

    public User(String email,String password)
    {

    }

    public void updatePassword(String password)
    {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
