package by.bsuir.entity.request;

public class GetTimetableForUserRequest implements Request {
    private static final long serialVersionUID = 32432315684365L;
    private String userName;

    public GetTimetableForUserRequest(String userName) {
        this.userName = userName;
    }
}
