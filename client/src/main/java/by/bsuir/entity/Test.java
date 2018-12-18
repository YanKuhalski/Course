package by.bsuir.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test  implements  Serializable  {
    private  static  final  long serialVersionUID =43242343242341567L;
    private  int id;
    private String name;
    private List<Question> questions ;

    public Test(int id, String name) {
        this.id = id;
        this.name = name;
        questions = new ArrayList<>();
    }

    public Test addQuestion(Question question){
        questions.add(question);
        return  this;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getId() {
        return id;
    }
}
