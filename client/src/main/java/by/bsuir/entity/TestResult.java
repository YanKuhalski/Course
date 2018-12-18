package by.bsuir.entity;

import java.io.Serializable;

public class TestResult implements  Serializable  {
    private static final long serialVersionUID = 89323899830912L;
    private  String name;
    private  String email;
    private  double result;

    public TestResult(String name, String email, double result) {
        this.name = name;
        this.email = email;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getResult() {
        return result;
    }
}
