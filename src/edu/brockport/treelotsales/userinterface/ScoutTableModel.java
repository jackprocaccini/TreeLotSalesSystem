package edu.brockport.treelotsales.userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class ScoutTableModel
{
    private final SimpleStringProperty id;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty middleName;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty troopID;
    private final SimpleStringProperty status;
    private final SimpleStringProperty dateStatusUpdated;

    //----------------------------------------------------------------------------
    public ScoutTableModel(Vector<String> bookData)
    {
        id =  new SimpleStringProperty(bookData.elementAt(0));
        firstName =  new SimpleStringProperty(bookData.elementAt(1));
        lastName =  new SimpleStringProperty(bookData.elementAt(2));
        middleName =  new SimpleStringProperty(bookData.elementAt(3));
        dateOfBirth =  new SimpleStringProperty(bookData.elementAt(4));
        phoneNumber =  new SimpleStringProperty(bookData.elementAt(5));
        email =  new SimpleStringProperty(bookData.elementAt(6));
        troopID =  new SimpleStringProperty(bookData.elementAt(7));
        status =  new SimpleStringProperty(bookData.elementAt(8));
        dateStatusUpdated =  new SimpleStringProperty(bookData.elementAt(9));

    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public SimpleStringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public SimpleStringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getTroopID() {
        return troopID.get();
    }

    public SimpleStringProperty troopIDProperty() {
        return troopID;
    }

    public void setTroopID(String troopID) {
        this.troopID.set(troopID);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getDateStatusUpdated() {
        return dateStatusUpdated.get();
    }

    public SimpleStringProperty dateStatusUpdatedProperty() {
        return dateStatusUpdated;
    }

    public void setDateStatusUpdated(String dateStatusUpdated) {
        this.dateStatusUpdated.set(dateStatusUpdated);
    }
}
