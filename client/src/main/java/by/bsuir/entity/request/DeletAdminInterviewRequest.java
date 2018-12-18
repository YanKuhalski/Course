package by.bsuir.entity.request;

import by.bsuir.entity.Interview;
import by.bsuir.entity.request.Request;

public class DeletAdminInterviewRequest implements Request {
    private static final long serialVersionUID = 34332332427190123L;
    private final Interview interview;

    public DeletAdminInterviewRequest(Interview interview) {
        this.interview = interview;
    }
}
