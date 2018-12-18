package by.bsuir.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
    private static final long serialVersionUID = 324324126797856546L;
    private int id;
    private int testID;
    private String text;
    private int rightAnswerNumber;
    private List<Answer> answers;

    public Question(int id, int testID, String text, int rightAnswerNumber) {
        this.id = id;
        this.testID = testID;
        this.text = text;
        this.rightAnswerNumber = rightAnswerNumber;
        answers = new ArrayList<>();
    }

    public Question addAnswer(Answer answer) {
        answers.add(answer);
        return this;
    }
}
