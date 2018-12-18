package by.bsuir.server.base;

import by.bsuir.entity.Interview;
import by.bsuir.entity.TestResult;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultBase   {
    public List<TestResult> getResultsByTestName(Connection connection, String testname) {
        List<TestResult> results = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select u.login, u.email, r.result \n" +
                    " from result r\n" +
                    " inner join user u on r.user_id = u.id\n" +
                    " inner join test t on r.test_id = t.id\n" +
                    " where t.name ='" + testname + "'\n" +
                    " order by result desc;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                double result = resultSet.getDouble("result");
                results.add(new TestResult(login, email, result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public void deleteResult(Connection connection, int userIdValue, int testIdValue, double resultValue) {
        try {
            Statement statement = connection.createStatement();
            String sql = "delete  from result where user_id=" + userIdValue + " and  test_id=" + testIdValue +
                    " and result=" + resultValue + " ;";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Double> collectResultStatistics(Connection connection) {
        Map<String, Double> resultStatistics = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select t.name, SUM(result)/count(result) as mid_result from  result r  inner join test t on r.test_id= t.id GROUP BY test_id;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double mid_result = resultSet.getDouble("mid_result");
                resultStatistics.put(name, mid_result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultStatistics;
    }

    public void saveAllData(Connection connection, FileWriter writer) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from course.result;");
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String testId = resultSet.getString("test_id");
                String result = resultSet.getString("result");
                writer.write("insert into result  (user_id ,test_id,result) values  (" + userId + "," + testId + "," + result + ");");
                writer.write('\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTesetResult(Connection connection, int userId, int testId, double i) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select  id from result where user_id=" + userId + " and test_id=" + testId + ";");
            while (resultSet.next()) {
                int resuletId = resultSet.getInt("id");
                statement.execute("update  result set result=" + i + " where id=" + resuletId + ";");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateResults(Connection connection, int userId, int testId, double result) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select  * from result where  user_id=" + userId + " and test_id=" + testId + " ;");
            boolean next = resultSet.next();
            if (next)
                statement.execute("update result set result=" + result + " where  user_id=" + userId + " and test_id=" + testId + " ;");
            else
                statement.execute("insert into result  (user_id ,test_id,result) values (" + userId + " ," + testId + "," + result + ");");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
