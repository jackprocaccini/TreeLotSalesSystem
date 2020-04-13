package edu.brockport.treelotsales.main;
//Test commit
//src/resources/javafx/javafx-sdk-13.0.2/lib

import edu.brockport.treelotsales.event.Event;
import edu.brockport.treelotsales.model.TLC;
import edu.brockport.treelotsales.model.Tree;
import edu.brockport.treelotsales.userinterface.MainStageContainer;
import edu.brockport.treelotsales.userinterface.WindowPosition;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Properties; //

public class TreeLotSalesSystem extends Application {

    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

//        String barcode = "test";
//        String treeType = "1";
//        String notes ="test notes";
//        String status = "active";
//        String dateLastUpdated ="today";
//
//        Properties properties = new Properties();
//
//        properties.setProperty("Barcode", barcode);
//        properties.setProperty("TreeType", treeType);
//        properties.setProperty("Notes", notes);
//        properties.setProperty("Status", status);
//        properties.setProperty("DateStatusUpdated", dateLastUpdated);
//
//        properties.list(System.out);
//
//        Tree tree = new Tree(properties);
//        tree.update("New");
//        System.out.println("End of program");

        MainStageContainer.setStage(stage, "Tree Lot Sales System");
        Stage mainStage = MainStageContainer.getInstance();

        mainStage.setOnCloseRequest(e -> {
            System.exit(0);
        });

        try{
            TLC t = new TLC();
        } catch(Exception e){
            System.err.println("Could not create TLC");
            new Event(Event.getLeafLevelClassName(this), "TLCSystem.<init>", "Unable to create TLC object", Event.ERROR);
            e.printStackTrace();
        }

        WindowPosition.placeCenter(mainStage);
        mainStage.show();
    }
}
