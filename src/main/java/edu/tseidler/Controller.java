package edu.tseidler;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Controller {
    @FXML
    ListView<String> messagesView;
    @FXML
    TextArea inputArea;
    ObservableList<String> messages;
    private Socket socket;
    private PrintWriter socketWritter;
    private Scanner socketReader;

    public void initialize() throws IOException {
        messages = FXCollections.observableArrayList();
        messagesView.setItems(messages);
        socket = new Socket("localhost", 8189);
        socketWritter = new PrintWriter(socket.getOutputStream());
        socketReader = new Scanner(socket.getInputStream());
        new Thread(() -> {
            while (true) {
                String msg = socketReader.nextLine();
                Platform.runLater(() -> {
                    messages.add(msg);
                    messagesView.scrollTo(messages.size());
                });
            }
        }).start();
    }

    @FXML
    public void sendMessage(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleEnterInInput();
        }
    }

    private void handleEnterInInput() {
        String message = inputArea.getText().trim();
        socketWritter.println(message);
        socketWritter.flush();
        inputArea.clear();
    }
}
