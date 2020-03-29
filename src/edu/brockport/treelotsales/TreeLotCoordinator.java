package edu.brockport.treelotsales;

import edu.brockport.treelotsales.exception.InvalidPrimaryKeyException;
import edu.brockport.treelotsales.model.Scout;

import java.util.Properties;
import java.util.Scanner;

public class TreeLotCoordinator {

    public static void main(String[] args){
        String firstName = "Phill";
        String lastName = "Diben";
        String middleName ="Bitch";
        String dob = "12-21-1998";
        String phoneNumber ="(585)696-6969";
        String email = "pdibe2@brockport.edu";
        String troopID = "1";

        Properties properties = new Properties();
        Scout oldScout;

        properties.setProperty("FirstName", firstName);
        properties.setProperty("LastName", lastName);
        properties.setProperty("MiddleName", middleName);
        properties.setProperty("DateOfBirth", dob);
        properties.setProperty("PhoneNumber", phoneNumber);
        properties.setProperty("Email", email);
        properties.setProperty("TroopID", troopID);
        properties.setProperty("Status", "Active");

        properties.list(System.out);

        try{
            oldScout = new Scout(properties.getProperty("TroopID"));
            System.out.println("oldScout created");
        } catch(InvalidPrimaryKeyException e){
            System.out.println("catch");
            System.out.println(e.getLocalizedMessage());
        }

        Scout newScout = new Scout(properties);

        System.out.println("Scout created, inserting into database");
        newScout.update();
        System.out.println(newScout.getUpdateStatusMessage());
    }
}
