package ru.javaSchoolProject.models;

import ru.javaSchoolProject.enums.Role;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="users")
@Data
public class User implements Serializable {
    public User(){
    }
    public User(String log, String pas, Role role){
        this.login = log;
        this.password = pas;
        this.role = role;
        this.isBlocked = false;
        this.isBlockedByAdmin = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "token")
    private String token;

    @Column(name = "is_blocked")// default false
    private boolean isBlocked;

    @Column(name = "is_blocked_by_admin")// default false
    private boolean isBlockedByAdmin;

}
