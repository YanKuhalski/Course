package by.bsuir.entity.request;

import by.bsuir.entity.Interview;
import by.bsuir.entity.User;
import by.bsuir.entity.request.user.control.AddUserRequest;
import by.bsuir.entity.request.user.control.GetAllUserInfoRequest;
import by.bsuir.entity.request.user.control.RemoveUserRequest;
import by.bsuir.entity.request.user.control.UpdateUserRequest;
import by.bsuir.server.base.BaseWorker;

import java.util.ArrayList;

public class RequestHandler {
    public Object handle(Request request) {
        BaseWorker baseWorker = BaseWorker.getInstance();
        switch (request.getClass().getSimpleName()) {
            case "EnterRequest":
                EnterRequest enterRequest = (EnterRequest) request;
                String name = enterRequest.getName();
                String password = enterRequest.getPassword();
                User user = baseWorker.findUser(name, password);
                enterRequest.setUser(user);
                return enterRequest;
            case "GetAllUserInfoRequest":
                GetAllUserInfoRequest getAllUserInfoRequest = (GetAllUserInfoRequest) request;
                return baseWorker.findAllUsers(getAllUserInfoRequest.getUser().getLogin());
            case "RemoveUserRequest":
                RemoveUserRequest removeUserRequest = (RemoveUserRequest) request;
                baseWorker.removeUser(removeUserRequest.getNeedToRemove());
                return baseWorker.findAllUsers(removeUserRequest.getUser().getLogin());
            case "GetTestNamesRequest":
                return baseWorker.findAllTestNames();
            case "GetAllResultsRequest":
                GetAllResultsRequest getAllResultsRequest = (GetAllResultsRequest) request;
                String testName = getAllResultsRequest.getTestName();
                return baseWorker.findAllResults(testName);
            case "AddInterviewRequest":
                AddInterviewRequest addInterviewRequest = (AddInterviewRequest) request;
                return baseWorker.tryToAddInterview(addInterviewRequest);
            case "GetTimetableForUserRequest":
                GetTimetableForUserRequest getTimetableForUserRequest = (GetTimetableForUserRequest) request;
                String userName = getTimetableForUserRequest.getUserName();
                return baseWorker.findUserInterviewInfo(userName);
            case "GetTimetableForAdminRequest":
                GetTimetableForAdminRequest getTimetableForAdminRequest = (GetTimetableForAdminRequest) request;
                String adminLogin = getTimetableForAdminRequest.getAdminLogin();
                return baseWorker.findAdminInterviewInfo(adminLogin);
            case "AddUserRequest":
                AddUserRequest addUserRequest = (AddUserRequest) request;
                User userToAdd = addUserRequest.getUser();
                return baseWorker.addUser(userToAdd);
            case "UpdateUserRequest":
                UpdateUserRequest updateUserRequest = (UpdateUserRequest) request;
                String oldUserName = updateUserRequest.getOldUserName();
                User userToUpdate = updateUserRequest.getUser();
                return baseWorker.updateUser(oldUserName, userToUpdate);
            case "DeletUserInterviewRequest":
                DeletUserInterviewRequest deletUserInterviewRequest = (DeletUserInterviewRequest) request;
                Interview interview = deletUserInterviewRequest.getInterview();
                baseWorker.removeInterview(interview);
                return baseWorker.findUserInterviewInfo(interview.getUserName());
            case "DeletAdminInterviewRequest":
                DeletAdminInterviewRequest deletAdminInterviewRequest = (DeletAdminInterviewRequest) request;
                Interview adminInterview = deletAdminInterviewRequest.getInterview();
                baseWorker.removeInterview(adminInterview);
                return baseWorker.findAdminInterviewInfo(adminInterview.getAdminName());
            case "DeleteResultRequest":
                DeleteResultRequest deleteResultRequest = (DeleteResultRequest) request;
                baseWorker.removeResult(deleteResultRequest.getResult(), deleteResultRequest.getTestToLoadName());
                return baseWorker.findAllResults(deleteResultRequest.getTestToLoadName());
            case "GetTestStatisticsRequest":
                return baseWorker.findTestStatistics();
            case "GetAllAdminsInfoRequest":
                return baseWorker.findAllAdminsInfo();
            case "GetAvailableTestNamesRequest":
                GetAvailableTestNamesRequest getAvailableTestNamesRequest = (GetAvailableTestNamesRequest) request;
                return baseWorker.findAvalableTestNamesForUser(getAvailableTestNamesRequest.getUserId());
            case "GetTestRequest":
                GetTestRequest getTestRequest = (GetTestRequest) request;
                int userId = getTestRequest.getUserId();
                String test = getTestRequest.getTestName();
                return baseWorker.getTest(userId, test);
            case "UpdateResultRequest":
                UpdateResultRequest updateResultRequest = (UpdateResultRequest) request;
                int userId1 = updateResultRequest.getUserId();
                int testId = updateResultRequest.getTestId();
                double result = updateResultRequest.getResult();
                baseWorker.updateResults(userId1, testId, result);
                return new ArrayList<>();
            default:
                return null;
        }
    }
}
