package by.bsuir.server.base;

import by.bsuir.entity.Answer;
import by.bsuir.entity.Interview;
import by.bsuir.entity.Question;
import by.bsuir.entity.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestBase {
    public List<String> getAllNames(Connection connection) {
        List<String> names = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from test;");
            while (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public Optional<Integer> findTestIdByName(Connection connection, String testToLoadName) {
        Optional<Integer> id = Optional.empty();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select  id from test where name='" + testToLoadName + "'");
            while (resultSet.next()) {
                id = Optional.of(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void saveAllData(Connection connection, FileWriter writer) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from course.test ;");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                writer.write("insert into test (name) values ('" + name + "');");
                writer.write('\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> findAvalableTestNamesForUser(Connection connection, int userId) {
        List<String> names = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = " select t1.name , t2.sname from\n" +
                    "(select  name as name from test ) t1 left join\n" +
                    "(select  t.name as sname from test t cross join result r on  t.id = r.test_id   where user_id = " + userId + ") t2 \n" +
                    "on  t1.name = t2.sname ;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String same = resultSet.getString("sname");
                if (same == null)
                    names.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public Optional<Integer> getTestId(Connection connection, String testName) {
        Optional<Integer> testId = Optional.empty();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from test where name='" + testName + "';");
            while (resultSet.next()) {
                testId = Optional.of(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testId;
    }

    public Test buildTest(Connection connection, int testId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet testResultSet = statement.executeQuery("select name  from test where id=" + testId + ";");
            while (testResultSet.next()) {
                String testName = testResultSet.getString("name");
                Test test = new Test(testId, testName);
                Statement statement1 = connection.createStatement();
                ResultSet questionResultSet = statement1.executeQuery("select id, text, right_answer from question where test_id=" + testId + "; ");
                while (questionResultSet.next()) {
                    int questionId = questionResultSet.getInt("id");
                    String questionText = questionResultSet.getString("text");
                    int rightAnswer = questionResultSet.getInt("right_answer");
                    Question question = new Question(questionId, testId, questionText, rightAnswer);
                    Statement statement2 = connection.createStatement();
                    ResultSet answerResultSet = statement2.executeQuery("select id,text  from answer where question_id=" + questionId + ";");
                    while (answerResultSet.next()) {

                        int answerId = answerResultSet.getInt("id");
                        String answerText = answerResultSet.getString("text");
                        question.addAnswer(new Answer(answerId, answerText, questionId));
                    }
                    test.addQuestion(question);
                }
                return test;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
