package by.bsuir.entity;

import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 235235239696334L;
    private int id;
    private String text;
    private int question_id;

    public Answer(int id, String text, int question_id) {
        this.id = id;
        this.text = text;
        this.question_id = question_id;
    }
}
