package by.bsuir.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 4343337262121211L;
    private int id;
    private String login;
    private String role;
    private String email;
    private String password;

    public User(String login, String role) {
        this.login = login;
        this.role = role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
