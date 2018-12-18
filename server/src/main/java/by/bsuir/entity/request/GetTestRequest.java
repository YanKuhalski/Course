package by.bsuir.entity.request;

public class GetTestRequest implements Request {
    private static  final  long serialVersionUID = 3247823885328313L;
    private int userId;
    private String testName;

    public GetTestRequest(int userId, String testName) {
        this.userId = userId;
        this.testName = testName;
    }

    public int getUserId() {
        return userId;
    }

    public String getTestName() {
        return testName;
    }
}
