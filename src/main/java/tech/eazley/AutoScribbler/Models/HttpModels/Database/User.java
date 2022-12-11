package tech.eazley.AutoScribbler.Models.HttpModels.Database;

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
    private String password;

    public User(String email,String password)
    {

    }
}
