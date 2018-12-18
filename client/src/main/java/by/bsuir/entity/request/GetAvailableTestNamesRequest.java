package by.bsuir.entity.request;

public class GetAvailableTestNamesRequest implements Request {
    private static final long serialVersionUID = 3423423423968581405L;
    private final int userId;

    public GetAvailableTestNamesRequest(int userId) {
        this.userId = userId;
    }
}
