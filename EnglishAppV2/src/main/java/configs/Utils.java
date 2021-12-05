package configs;

import javafx.scene.control.Alert;

public class Utils {
    public static void getAlertBox(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.show();
    }
}
