package edu.brockport.treelotsales.userinterface;

import edu.brockport.treelotsales.impresario.IModel;
import edu.brockport.treelotsales.model.TLC;
import edu.brockport.treelotsales.model.Tree;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class ConfirmCostView extends View {
    private Text prompt;
    private Button yesButton;
    private Button noButton;

    private MessageView statusLog;

    public ConfirmCostView(IModel transaction) {
        super(transaction, "ConfirmCost");

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
            myModel.stateChangeRequest("TransactionView", null);
        });

        noButton = new Button("No");
        //fix this later, hacked for now
        noButton.setOnAction(e -> {
            Tree tree = new Tree();
            tree.createAndShowGetTreeView();
        });
        buttonBox.getChildren().addAll(yesButton, noButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }

    private Node createBody() {
        HBox mainBox = new HBox(10);
        prompt = new Text("Confirm cost: $" + myModel.getState("TransactionAmount"));
        prompt.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        mainBox.getChildren().add(prompt);


        return mainBox;
    }

    private Node createTitle() {
        Text titleText = new Text("Confirm Cost");
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
