package by.bsuir.server.base;

import by.bsuir.entity.Interview;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InterviewBase {
    public void addInterview(Connection connection, int adminId, int userIdValue, String time, LocalDate date) {
        try {
            Statement statement = connection.createStatement();
            String str = "insert into interview (hr_id,user_id,time,date) values (" + adminId + "," + userIdValue + ",'" + time + "','" + date + "');";
            statement.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userHaveSameInterview(Connection connection, int userIdValue, String time, LocalDate date) {
        try {
            Statement statement = connection.createStatement();
            String str = "select  * from interview where  user_id=" + userIdValue + " and time='" + time + "' and date='" + date + "';";
            ResultSet resultSet = statement.executeQuery(str);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean adminHaveSameInterview(Connection connection, int adminId, String time, LocalDate date) {
        try {
            Statement statement = connection.createStatement();
            String str = "select  * from interview where hr_id=" + adminId + " and time='" + time + "' and date='" + date + "';";
            ResultSet resultSet = statement.executeQuery(str);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Interview> findUserInterviews(Connection connection, String userName) {
        List<Interview> interviews = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select i.date, i.time, a.login, a.email " +
                    "from interview i " +
                    "inner join user u on i.user_id = u.id " +
                    "inner join user a on i.hr_id = a.id " +
                    "where u.login ='" + userName + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String time = resultSet.getString("time");
                String date = resultSet.getString("date");
                String adminName = resultSet.getString("login");
                String email = resultSet.getString("email");
                Interview interview = new Interview(time, date, userName, adminName);
                interview.setAdminEmail(email);
                interviews.add(interview);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return interviews;
    }

    public List<Interview> findAdminInterviews(Connection connection, String adminLogin) {
        List<Interview> interviews = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select i.date, i.time, u.login " +
                    "from interview i " +
                    "inner join user u on i.user_id = u.id " +
                    "inner join user a on i.hr_id = a.id " +
                    "where a.login ='" + adminLogin + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String time = resultSet.getString("time");
                String date = resultSet.getString("date");
                String userName = resultSet.getString("login");
                interviews.add(new Interview(time, date, userName, adminLogin));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return interviews;
    }

    public void removeInterview(Connection connection, String date, String time, Integer userIdValue) {
        try {
            Statement statement = connection.createStatement();
            String sql = "delete  from interview where  date='" + date + "' and " +
                    "time='" + time + "' and user_id=" + userIdValue + ";";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAllData(Connection connection, FileWriter writer) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from course.interview;");
            while (resultSet.next()) {
                String time = resultSet.getString("time");
                String date = resultSet.getString("date");
                String userId = resultSet.getString("user_id");
                String hrId = resultSet.getString("hr_id");
                writer.write("insert into interview user (time,date,user_id,hr_id) values ('" + time + "','" + date + "','" + userId + "','" + hrId + "');");
                writer.write('\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
