package by.bsuir.entity.request.user.control;

import by.bsuir.entity.User;
import by.bsuir.entity.request.Request;

public class GetAllUserInfoRequest implements Request {
    private static final long serialVersionUID = 324879236579694242L;
    private  User user;

    public GetAllUserInfoRequest(User user) {
        this.user = user;
    }
}
