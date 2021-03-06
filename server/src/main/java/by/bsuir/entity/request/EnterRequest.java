package by.bsuir.entity.request;

import by.bsuir.entity.User;

public class EnterRequest implements  Request {
    private static final long serialVersionUID = 598633726487420111L;

    private String name;
    private String password;
    private User user;

    public EnterRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
