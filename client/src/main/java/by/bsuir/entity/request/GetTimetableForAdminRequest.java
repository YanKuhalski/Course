package by.bsuir.entity.request;

public class GetTimetableForAdminRequest implements Request {
    private static final long serialVersionUID = 323243532334214L;
    private final String adminLogin;

    public GetTimetableForAdminRequest(String adminLogin) {
        this.adminLogin = adminLogin;
    }
}
