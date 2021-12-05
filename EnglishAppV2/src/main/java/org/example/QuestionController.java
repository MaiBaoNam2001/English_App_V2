package org.example;

import configs.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Category;
import pojo.Choice;
import pojo.Question;
import services.CategoryService;
import services.QuestionService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class QuestionController implements Initializable {

    @FXML
    private ComboBox<Category> cbxCategory;

    @FXML
    private RadioButton rdnA;

    @FXML
    private RadioButton rdnB;

    @FXML
    private RadioButton rdnC;

    @FXML
    private RadioButton rdnD;

    @FXML
    private TableView<Question> tbvQuestion;

    @FXML
    private TextField txtA;

    @FXML
    private TextField txtB;

    @FXML
    private TextField txtC;

    @FXML
    private TextField txtD;

    @FXML
    private TextField txtKeyword;

    @FXML
    private TextField txtQuestion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.cbxCategory.setItems(FXCollections.observableArrayList(CategoryService.getCategoryList()));
            this.cbxCategory.getSelectionModel().selectFirst();
            this.loadTableColumns();
            this.loadTableData(null);
            this.txtKeyword.textProperty().addListener(event -> {
                try {
                    loadTableData(this.txtKeyword.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addQuestionHandler(ActionEvent actionEvent) throws SQLException {
        Question question = new Question(UUID.randomUUID().toString(), this.txtQuestion.getText(), this.cbxCategory.getSelectionModel().getSelectedItem().getCategoryId());
        List<Choice> choiceList = new ArrayList<>();
        choiceList.add(new Choice(UUID.randomUUID().toString(), this.txtA.getText(), this.rdnA.isSelected(), question.getQuestionId()));
        choiceList.add(new Choice(UUID.randomUUID().toString(), this.txtB.getText(), this.rdnB.isSelected(), question.getQuestionId()));
        choiceList.add(new Choice(UUID.randomUUID().toString(), this.txtC.getText(), this.rdnC.isSelected(), question.getQuestionId()));
        choiceList.add(new Choice(UUID.randomUUID().toString(), this.txtD.getText(), this.rdnD.isSelected(), question.getQuestionId()));
        try {
            QuestionService.addQuestion(question, choiceList);
            Utils.getAlertBox("Add question successful !!!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.getAlertBox("Add question failed !!!", Alert.AlertType.ERROR);
        }
    }

    public void resetHandler(ActionEvent actionEvent) {
        this.txtQuestion.clear();
        this.cbxCategory.getSelectionModel().selectFirst();
        this.rdnA.setSelected(false);
        this.rdnB.setSelected(false);
        this.rdnC.setSelected(false);
        this.rdnD.setSelected(false);
        this.txtA.clear();
        this.txtB.clear();
        this.txtC.clear();
        this.txtD.clear();
    }

    public void loadTableColumns() {
        TableColumn<Question, String> colQuestion = new TableColumn<>("Question content");
        colQuestion.setCellValueFactory(new PropertyValueFactory<>("content"));
        colQuestion.setPrefWidth(400);
        TableColumn<Question, Integer> colCategoryId = new TableColumn<>("Category Id");
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colCategoryId.setPrefWidth(200);
        this.tbvQuestion.getColumns().add(colQuestion);
        this.tbvQuestion.getColumns().add(colCategoryId);
    }

    public void loadTableData(String keyword) throws SQLException {
        this.tbvQuestion.setItems(FXCollections.observableArrayList(QuestionService.getQuestionListByKeyword(keyword)));
    }
}
