package by.bsuir.server.base;

import by.bsuir.entity.Interview;
import by.bsuir.entity.TestResult;
import by.bsuir.entity.User;
import by.bsuir.entity.request.AddInterviewRequest;
import by.bsuir.server.reader.PropertieReader;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseWorker {
    private static BaseWorker instance;
    private Connection connection;
    private UserBase userBase;
    private TestBase testBase;
    private ResultBase resultBase;
    private InterviewBase interviewBase;

    {
        userBase = new UserBase();
        testBase = new TestBase();
        resultBase = new ResultBase();
        interviewBase = new InterviewBase();
    }

    static {
        instance = new BaseWorker();
    }

    private BaseWorker() {
        PropertieReader propertieReader = PropertieReader.getInstance();
        String name = propertieReader.getLogin();
        String password = propertieReader.getPassword();
        String url = propertieReader.getBaseUrl();
        try {
            Class.forName(propertieReader.getDriver());
            connection = DriverManager.getConnection(url, name, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static BaseWorker getInstance() {
        return instance;
    }

    public void saveAllData() {
        try (FileWriter writer = new FileWriter("savedData.doc", false)) {
            writer.write("-------------------USER BASE---------------------");
            writer.append('\n');
            userBase.saveAllData(connection, writer);
            writer.append('\n');
            writer.write("-------------------RESULT BASE---------------------");
            writer.append('\n');
            resultBase.saveAllData(connection, writer);
            writer.append('\n');
            writer.write("-------------------INTERVIEW BASE---------------------");
            writer.append('\n');
            interviewBase.saveAllData(connection, writer);
            writer.append('\n');
            writer.write("-------------------TEST BASE---------------------");
            writer.append('\n');
            testBase.saveAllData(connection, writer);
            writer.flush();
            System.out.println("All data was successfully saved");
        } catch (IOException e) {
            System.out.println("Файл для выгрузки данных не доступен");
        }
    }

    /*                         USER DB WORK                                */
    public List<User> findAllAdminsInfo() {
        List<User> allUsers = userBase.getAllUsersByRole(connection, "admin");
        return allUsers;
    }

    public User findUser(String login, String password) {
        return userBase.findUserByLoginAndPassword(connection, login, password);
    }

    public List<User> findAllUsers(String login) {
        List<User> allUsers = userBase.getAllUsers(connection, login);
        return allUsers;
    }

    public void removeUser(User needToRemove) {
        userBase.removeUser(connection, needToRemove.getLogin());
    }

    public boolean addUser(User user) {
        return userBase.addUser(connection, user);
    }

    public boolean updateUser(String oldName, User user) {
        return userBase.updateUser(connection, oldName, user);
    }

    /*                         TEST DB WORK                                */
    public List<String> findAllTestNames() {
        return testBase.getAllNames(connection);
    }

    public List<String> findAvalableTestNamesForUser(int userId) {
        return testBase.findAvalableTestNamesForUser(connection, userId);
    }
    /*                         RESSULT DB WORK                                */

    public List<TestResult> findAllResults(String testName) {
        return resultBase.getResultsByTestName(connection, testName);
    }

    public void removeResult(TestResult result, String testToLoadName) {
        Optional<Integer> userId = userBase.findUserIdByName(connection, result.getName());
        Optional<Integer> testId = testBase.findTestIdByName(connection, testToLoadName);
        if (userId.isPresent() && testId.isPresent()) {
            int userIdValue = userId.get();
            int testIdValue = testId.get();
            double resultValue = result.getResult();
            resultBase.deleteResult(connection, userIdValue, testIdValue, resultValue);
        }
    }

    public Map<String, Double> findTestStatistics() {
        return resultBase.collectResultStatistics(connection);
    }
    /*                         INTERVIEW DB WORK                                */

    public boolean tryToAddInterview(AddInterviewRequest addInterviewRequest) {
        String name = addInterviewRequest.getName();

        int adminId = addInterviewRequest.getAdminId();
        String time = addInterviewRequest.getTime();
        LocalDate date = addInterviewRequest.getDate();

        Optional<Integer> userId = userBase.findUserIdByName(connection, name);

        if (userId.isPresent()) {
            int userIdValue = userId.get();
            if ((!interviewBase.adminHaveSameInterview(connection, adminId, time, date) && (!interviewBase.userHaveSameInterview(connection, userIdValue, time, date)))) {
                interviewBase.addInterview(connection, adminId, userIdValue, time, date);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public List<Interview> findUserInterviewInfo(String userName) {
        List<Interview> interviews = new ArrayList<>();
        List<Interview> interviewList = interviewBase.findUserInterviews(connection, userName);
        interviews.addAll(interviewList);
        return interviews;
    }

    public List<Interview> findAdminInterviewInfo(String adminLogin) {
        List<Interview> interviews = new ArrayList<>();
        List<Interview> interviewList = interviewBase.findAdminInterviews(connection, adminLogin);
        interviews.addAll(interviewList);
        return interviews;
    }

    public void removeInterview(Interview interview) {
        String userName = interview.getUserName();
        Optional<Integer> userId = userBase.findUserIdByName(connection, userName);
        if (userId.isPresent()) {
            Integer userIdValue = userId.get();
            String time = interview.getTime();
            String date = interview.getDate();
            interviewBase.removeInterview(connection, date, time, userIdValue);
        }

    }

    public Object getTest(int userId, String testName) {
        Optional<Integer> testId = testBase.getTestId(connection, testName);
        if (testId.isPresent()) {

            resultBase.setTesetResult(connection, userId, testId.get(), 0);
            return testBase.buildTest(connection, testId.get());
        } else {
            return null;
        }
    }

    public void updateResults(int userId, int testId, double result) {
        resultBase.updateResults(connection,userId,testId,result);
    }
}
