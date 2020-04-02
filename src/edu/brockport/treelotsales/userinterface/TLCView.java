package edu.brockport.treelotsales.userinterface;

import edu.brockport.treelotsales.impresario.IModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TLCView extends View {
    private Button registerScoutButton;
    private Button updateScoutButton;
    private Button deleteScoutButton;

    private MessageView statusLog;

    public TLCView(IModel tlc){
        super(tlc, "TLCView");
        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 10, 10, 10));
        container.getChildren().add(createTitle());
        container.getChildren().add(createButtons());
        container.getChildren().add(createStatusLog("     "));

        getChildren().add(container);

        myModel.subscribe("TransactionError", this);
    }

    private Node createTitle(){
        Text titleText = new Text("Tree Lot Sales System");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);

        return titleText;
    }

    private Node createButtons(){
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        registerScoutButton = new Button("Register Scout");
        updateScoutButton = new Button("Update Scout");
        deleteScoutButton = new Button("Delete Scout");

        registerScoutButton.setOnAction(e -> {
            myModel.stateChangeRequest("RegisterScout", null);
        });

        updateScoutButton.setOnAction(e -> {
            myModel.stateChangeRequest("UpdateScout", null);
        });

        deleteScoutButton.setOnAction(e -> {
            myModel.stateChangeRequest("DeleteScout", null);
        });

        buttonBox.getChildren().addAll(registerScoutButton, updateScoutButton, deleteScoutButton);

        return buttonBox;
    }

    @Override
    public void updateState(String key, Object value) {
        if(key.equals("TransactionError")){
            displayErrorMessage((String)value);
        }
    }

    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }
}