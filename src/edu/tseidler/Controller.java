package edu.tseidler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class Controller {
    @FXML
    ListView messagesView;
    @FXML
    TextArea inputArea;

    List<String> messages;

    public void initialize() {
        messages = FXCollections.observableArrayList();
    }

    private void handleEnterInInput() {
        String message = inputArea.getText();
        System.out.println("enter pressed: " + message);
        messages.add(message);
        inputArea.clear();
    }

    @FXML
    public void sendMessage(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleEnterInInput();
        }
    }
}
