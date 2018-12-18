package by.bsuir.entity;

import java.io.Serializable;

public class User implements  Serializable {
    private static final long serialVersionUID = 4343337262121211L;

    private String login;
    private String role;
    private String email;
    private String password;
    private int id;

    public User(String login, String role) {
        this.login = login;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
