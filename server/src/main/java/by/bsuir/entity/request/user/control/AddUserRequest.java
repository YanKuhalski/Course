package by.bsuir.entity.request.user.control;

import by.bsuir.entity.User;
import by.bsuir.entity.request.Request;

public class AddUserRequest implements Request {
    private  static  final  long serialVersionUID = 3243242343214866545L;
    private User user ;

    public AddUserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
