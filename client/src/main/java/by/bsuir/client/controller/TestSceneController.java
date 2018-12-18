package by.bsuir.client.controller;

import by.bsuir.client.mediator.ButtonMediator;
import by.bsuir.entity.Question;
import by.bsuir.entity.Test;
import by.bsuir.entity.request.UpdateResultRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class TestSceneController extends SceneController implements Initializable {
    @FXML
    public TextArea question;
    @FXML
    public Label firstText;
    @FXML
    public Label secondText;
    @FXML
    public Label thirdText;
    @FXML
    public Label fourthText;
    @FXML
    public RadioButton firstRadiButton;
    @FXML
    public RadioButton secondRadiButton;
    @FXML
    public RadioButton thirdRadiButton;
    @FXML
    public RadioButton fourthRadiButton;
    @FXML
    public Button nextButton;

    private ButtonMediator mediator;
    private Stack<Question> questions;
    private double result;
    private int rightAnswer;
    private int amountOfQuestion;
    private int testId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        result = 0;
        questions = new Stack<>();
        mediator = new ButtonMediator();
        Test test = answerHandler.waitForTest(connection.getIn());
        testId = test.getId();
        amountOfQuestion = test.getQuestions().size();
        this.questions.addAll(test.getQuestions());
        Question firstQuestion = this.questions.pop();
        rightAnswer = firstQuestion.getRightAnswerNumber();
        mediator.addButtons(firstRadiButton, secondRadiButton, thirdRadiButton, fourthRadiButton);
        setQuestion(firstQuestion);
    }

    public void endTest(ActionEvent event) {
        int selectedNumber = mediator.getSelectedNumber();
        if (rightAnswer == selectedNumber) {
            result = result + 100 / amountOfQuestion;
        }
        connection.sendRequest(new UpdateResultRequest(connection.getUser().getId(), testId, result));
        answerHandler.waitForCollection(connection.getIn());
        setScene(event, "/fxml/UserMainMenu.fxml");
    }

    public void nextQuestion(ActionEvent event) {
        int selectedNumber = mediator.getSelectedNumber();
        if (rightAnswer == selectedNumber) {
            result = result + 100 / amountOfQuestion;
        }
        Question nextQuestion = questions.pop();
        rightAnswer = nextQuestion.getRightAnswerNumber();
        setQuestion(nextQuestion);
        mediator.setAllUnactive();
        if (questions.isEmpty()) {
            nextButton.setVisible(false);
        }
        connection.sendRequest(new UpdateResultRequest(connection.getUser().getId(), testId, result));
        answerHandler.waitForCollection(connection.getIn());
    }

    public void setQuestion(Question question) {
        this.question.setText(question.getText());
        firstText.setText(question.getAnswers().get(0).getText());
        secondText.setText(question.getAnswers().get(1).getText());
        thirdText.setText(question.getAnswers().get(2).getText());
        fourthText.setText(question.getAnswers().get(3).getText());
    }
}
