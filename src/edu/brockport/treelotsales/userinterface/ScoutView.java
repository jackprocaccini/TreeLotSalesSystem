package edu.brockport.treelotsales.userinterface;

import edu.brockport.treelotsales.impresario.IModel;
import edu.brockport.treelotsales.model.TLC;
import javafx.geometry.Insets;
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

public class ScoutView extends View{
    private Label lastNameLabel;
    private Label firstNameLabel;
    private Label middleNameLabel;
    private Label dateOfBirthLabel;
    private Label phoneNumberLabel;
    private Label emailLabel;
    private Label troopIDLabel;
    private Label statusLabel;

    private TextField lastNameTF;
    private TextField firstNameTF;
    private TextField middleNameTF;
    private TextField dateOfBirthTF;
    private TextField phoneNumberTF;
    private TextField emailTF;
    private TextField troopIDTF;
    private ComboBox<String> statusCB;

    private Button submitButton;
    private Button cancelButton;

    private MessageView statusLog;

    public ScoutView(IModel book){
        super(book, "ScoutView");

        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 10, 10, 10));
        container.getChildren().add(createTitle());
        container.getChildren().add(createBody());
        statusLog = new MessageView("     ");

        container.getChildren().add(createFooter());
        container.getChildren().add(statusLog);
        getChildren().add(container);
    }

    private Node createFooter(){
        HBox buttonBox = new HBox(10);
        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            verifyInputs();
        });

        cancelButton = new Button("Done");
        //fix this later, hacked for now
        cancelButton.setOnAction(e -> {
            TLC l = new TLC();
            l.createAndShowTLCView();
        });
        buttonBox.getChildren().addAll(submitButton, cancelButton);
        return buttonBox;
    }

    private Node createBody(){
        HBox mainBox = new HBox(10);
        VBox labelsBox = new VBox(10);
        VBox tfBox = new VBox();

        lastNameLabel = new Label("Last Name");
        middleNameLabel = new Label("Middle Name");
        firstNameLabel = new Label("First Name");
        dateOfBirthLabel = new Label("DOB (YYYY-MM-DD)");
        phoneNumberLabel = new Label("Phone Number (digits only)");
        emailLabel = new Label("Email");
        troopIDLabel = new Label("Troop ID");
        statusLabel = new Label("Status");
        labelsBox.getChildren().addAll(lastNameLabel, middleNameLabel, firstNameLabel, dateOfBirthLabel,
                phoneNumberLabel, emailLabel, troopIDLabel, statusLabel);

        lastNameTF = new TextField();
        middleNameTF = new TextField();
        firstNameTF = new TextField();
        dateOfBirthTF = new TextField();
        phoneNumberTF = new TextField();
        emailTF = new TextField();
        troopIDTF = new TextField();
        statusCB = new ComboBox<String>();
        statusCB.getItems().addAll("Active", " Inactive");
        statusCB.setValue("Active");
        tfBox.getChildren().addAll(lastNameTF, middleNameTF, firstNameTF, dateOfBirthTF, phoneNumberTF,
                emailTF, troopIDTF, statusCB);

        mainBox.getChildren().addAll(labelsBox, tfBox);
        return mainBox;
    }

    private Node createTitle(){
        Text titleText = new Text("Register a Scout");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);

        return titleText;
    }

    @Override
    public void updateState(String key, Object value) {

    }

    private void verifyInputs(){
        String lastName = lastNameTF.getText();
        String firstName = firstNameTF.getText();
        String middleName = middleNameTF.getText();
        String DOB = dateOfBirthTF.getText();
        String phoneNumber = phoneNumberTF.getText();
        String email = emailTF.getText();
        String troopID = troopIDTF.getText();
        String status = statusCB.getValue();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String dateStatusUpdated =  df.format(date);

        boolean isError = false;

        if(lastName == null || firstName == null || lastName.equals("") || firstName.equals("")){
            displayErrorMessage("first and last Name fields must not be null");
            isError = true;
        }

//        int yearInt = 0;
//        try {
//            yearInt = Integer.parseInt(DOB);
//        } catch(Exception e){
//            displayErrorMessage("Date must be an integer" + " " + middleName + " " + yearInt);
//            isError = true;
//        }
//
//        if(yearInt < 1800 || yearInt > 2020){
//            displayErrorMessage("Date must be an integer between 1800 and 2020, inclusive" + " " + middleName + " " + yearInt);
//            isError = true;
//        }

        if(status.equals("") || status == null){
            displayErrorMessage("Please select a status");
            isError = true;
        }
        if(!isError) {
            Properties props = new Properties();
            System.out.println(lastName + " " + middleName + " " + status + " " + firstName);
            props.setProperty("LastName", lastName);
            props.setProperty("MiddleName", middleName);
            props.setProperty("Status", status);
            props.setProperty("FirstName", firstName);
            props.setProperty("DateOfBirth", DOB);
            props.setProperty("PhoneNumber", phoneNumber);
            props.setProperty("Email", email);
            props.setProperty("TroopID", troopID);
            props.setProperty("DateStatusUpdated", dateStatusUpdated);

            myModel.stateChangeRequest("ProcessScout", props);
            displayMessage("Scout added successfully");
        }

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