package by.bsuir.entity.request.user.control;

import by.bsuir.entity.User;
import by.bsuir.entity.request.Request;

public class RemoveUserRequest implements Request {
    private static final long serialVersionUID = 939366438484931229L;
    private User user;
    private User needToRemove;

    public RemoveUserRequest(User user, User selectedItem) {
        this.user = user;
        needToRemove = selectedItem;
    }
}
