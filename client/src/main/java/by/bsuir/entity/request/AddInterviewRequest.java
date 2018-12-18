package by.bsuir.entity.request;

import java.time.LocalDate;

public class AddInterviewRequest implements Request {
    private static final long serialVersionUID = 3432499267244L;

    private String name;
    private String time;
    private LocalDate date;
    private int adminId;

    public AddInterviewRequest(String name, String time, LocalDate date, int adminId) {
        this.name = name;
        this.time = time;
        this.date = date;
        this.adminId = adminId;
    }
}
