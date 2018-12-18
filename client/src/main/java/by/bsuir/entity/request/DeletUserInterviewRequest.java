package by.bsuir.entity.request;

import by.bsuir.entity.Interview;

public class DeletUserInterviewRequest implements Request {
    private static final long serialVersionUID = 344389427190123L;
    private final Interview interview;

    public DeletUserInterviewRequest(Interview interview) {
        this.interview = interview;
    }
}
