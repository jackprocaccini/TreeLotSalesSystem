package edu.brockport.treelotsales.userinterface;

import edu.brockport.treelotsales.impresario.IModel;
import edu.brockport.treelotsales.model.TLC;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class DeleteScoutView extends View {
    private Text prompt;
    private Button yesButton;
    private Button noButton;

    private MessageView statusLog;

    public DeleteScoutView(IModel scout) {
        super(scout, "DeleteScout");

        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 10, 10, 10));
        container.getChildren().add(createTitle());
        container.getChildren().add(createBody());
        statusLog = new MessageView("     ");

        container.getChildren().add(createFooter());
        container.getChildren().add(statusLog);
        getChildren().add(container);
    }

    private Node createFooter() {
        HBox buttonBox = new HBox(10);
        yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            myModel.stateChangeRequest("DeleteScout", null);
            TLC l = new TLC();
            l.createAndShowTLCView();
        });

        noButton = new Button("No");
        //fix this later, hacked for now
        noButton.setOnAction(e -> {
            TLC l = new TLC();
            l.createAndShowTLCView();
        });
        buttonBox.getChildren().addAll(yesButton, noButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }

    private Node createBody() {
        HBox mainBox = new HBox(10);
        prompt = new Text("Are you sure you want to delete this scout?: " + (String)myModel.getState("FirstName") + " " + (String)myModel.getState("LastName"));
        prompt.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        mainBox.getChildren().add(prompt);


        return mainBox;
    }

    private Node createTitle() {
        Text titleText = new Text("Delete a Scout");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);

        return titleText;
    }

    @Override
    public void updateState(String key, Object value) {

    }




    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    private void displayMessage(String message) {
        statusLog.displayMessage(message);
    }
}
