package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class MainController {

    @FXML
    void practiceHandler(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxpractice.fxml"));
        Parent root = fxmlLoader.load();
        PracticeController practiceController = fxmlLoader.getController();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Enter question number to practice: ");
        Optional<String> optional = dialog.showAndWait();
        if (optional.isPresent()) {
            practiceController.setNumber(Integer.parseInt(optional.get()));
            practiceController.loadQuestionList();
            practiceController.loadCurrentQuestion();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Practice");
            stage.show();
        }
    }

    @FXML
    void questionManagementHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxquestion.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Question Management");
        stage.show();
    }

}
