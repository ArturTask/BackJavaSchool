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
        this.login=log;
        this.password = pas;
        this.role=role;
    }
//    public User(String log, String pas){
//        this.login=log;
//        this.password = pas;
//        this.role=Role.ROLE_CUSTOMER;
//    }

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

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
