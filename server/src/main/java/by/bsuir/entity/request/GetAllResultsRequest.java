package by.bsuir.entity.request;

public class GetAllResultsRequest implements Request {
    private static final long serialVersionUID =324234234140785L;
    private String testName;

    public GetAllResultsRequest(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }
}
