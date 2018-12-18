package by.bsuir.entity.request.user.control;

import by.bsuir.entity.User;
import by.bsuir.entity.request.Request;

public class UpdateUserRequest implements Request {
    private static final long serialVersionUID = 578539837438590L;
    private User user;
    private String oldUserName;

    public UpdateUserRequest(String oldNameToUpdate, User user) {
        this.user = user;
        oldUserName = oldNameToUpdate;
    }

    public User getUser() {
        return user;
    }

    public String getOldUserName() {
        return oldUserName;
    }
}
