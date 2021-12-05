package org.example;

import configs.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import pojo.Choice;
import pojo.Question;
import services.QuestionService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PracticeController implements Initializable {

    @FXML
    private RadioButton rdnA;

    @FXML
    private RadioButton rdnB;

    @FXML
    private RadioButton rdnC;

    @FXML
    private RadioButton rdnD;

    @FXML
    private ToggleGroup rdnToggle;

    @FXML
    private Label txtQuestion;
    private List<Question> questionList;
    private int number;
    private int currentQuestionIndex;

    @FXML
    public void checkAnswerHandler(ActionEvent event) {
        RadioButton[] radioButtons = new RadioButton[]{rdnA, rdnB, rdnC, rdnD};
        for (int i = 0; i < radioButtons.length; i++) {
            if (this.questionList.get(this.currentQuestionIndex).getChoiceList().get(i).getIsCorrect()) {
                if (radioButtons[i].isSelected())
                    Utils.getAlertBox("EXACTLY !!!", Alert.AlertType.INFORMATION);
                else Utils.getAlertBox("WRONGLY !!!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void nextHandler(ActionEvent event) throws SQLException {
        if (this.currentQuestionIndex < this.questionList.size() - 1)
            this.currentQuestionIndex++;
        else this.currentQuestionIndex = 0;
        loadCurrentQuestion();
    }

    @FXML
    public void previousHandler(ActionEvent event) throws SQLException {
        if (this.currentQuestionIndex > 0)
            this.currentQuestionIndex--;
        else this.currentQuestionIndex = this.questionList.size() - 1;
        loadCurrentQuestion();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void loadCurrentQuestion() throws SQLException {
        RadioButton[] radioButtons = new RadioButton[]{rdnA, rdnB, rdnC, rdnD};
        Question currentQuestion = this.questionList.get(this.currentQuestionIndex);
        if (currentQuestion.getChoiceList() == null)
            currentQuestion.setChoiceList(QuestionService.getChoiceListByQuestionId(currentQuestion.getQuestionId()));
        this.txtQuestion.setText(currentQuestion.getContent());
        for (int i = 0; i < currentQuestion.getChoiceList().size(); i++) {
            radioButtons[i].setText(currentQuestion.getChoiceList().get(i).getContent());
        }
    }

    public void loadQuestionList() throws SQLException {
        this.questionList = QuestionService.getQuestionListByNumber(this.number);
    }
}
