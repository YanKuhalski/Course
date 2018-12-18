package by.bsuir.entity.request;

public class UpdateResultRequest implements Request {
    private static final long serialVersionUID = 324235235965967L;
    private int userId;
    private int testId;
    private double result;

    public UpdateResultRequest(int userId, int testId, double result) {
        this.userId = userId;
        this.testId = testId;
        this.result = result;
    }

    public int getUserId() {
        return userId;
    }

    public int getTestId() {
        return testId;
    }

    public double getResult() {
        return result;
    }
}
