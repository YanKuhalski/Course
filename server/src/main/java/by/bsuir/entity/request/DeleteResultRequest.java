package by.bsuir.entity.request;

import by.bsuir.entity.TestResult;

public class DeleteResultRequest implements Request {
    private static final long serialVersionUID = 2343249495721194028L;
    private TestResult result;
    private String testToLoadName;

    public DeleteResultRequest(String testToLoadName, TestResult result) {
        this.testToLoadName = testToLoadName;
        this.result = result;
    }
    public TestResult getResult() {
        return result;
    }

    public String getTestToLoadName() {
        return testToLoadName;
    }
}
