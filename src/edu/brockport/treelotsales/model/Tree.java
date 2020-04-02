package edu.brockport.treelotsales.model;

import edu.brockport.treelotsales.exception.InvalidPrimaryKeyException;
import edu.brockport.treelotsales.impresario.IModel;
import edu.brockport.treelotsales.userinterface.View;
import edu.brockport.treelotsales.userinterface.ViewFactory;
import edu.brockport.treelotsales.userinterface.WindowPosition;
import javafx.scene.Scene;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class Tree extends EntityBase implements IModel {
    private static final String myTableName = "Tree";
    private String updateStatusMessage = "";

    private Properties dependencies;

    public Tree(String barcode) throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (TroopID = " + barcode + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            if (size == 0) {
                throw new InvalidPrimaryKeyException("No trees matching barcode : "
                        + barcode + " found.");

            } else if (size == 1) {
                Properties retrievedBookData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();

                while (allKeys.hasMoreElements()) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            } else {
                throw new InvalidPrimaryKeyException("Multiple trees matching barcode : "
                        + barcode + " found.");
            }
        } else {
            throw new InvalidPrimaryKeyException("No tree matching id : "
                    + barcode + " found.");
        }
    }

    public Tree(Properties props) {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    public Tree() {
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
    }

    public void setDependencies() {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }


    @Override
    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage")) {
            return updateStatusMessage;
        } else {
            return persistentState.getProperty(key);
        }
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        if (key.equals("ProcessTree")) {
            processTree(value);
        } else {
            myRegistry.updateSubscribers(key, this);
        }
    }

    @Override
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }

    private void processTree(Object props) {
        this.persistentState = (Properties) (props);
        updateStateInDatabase();
    }

    private void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("Barcode") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("Barcode", persistentState.getProperty("Barcode"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Tree data for tree number : " + persistentState.getProperty("Barcode") + " updated successfully in database!";
            } else {
                Integer treeID =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("Barcode", "" + treeID);
                updateStatusMessage = "Tree data for new tree : "
                        + persistentState.getProperty("Barcode")
                        + "installed successfully in database!";
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            updateStatusMessage = "Error in installing tree data in database!";
        }
    }

    public static int compare(Tree a, Tree b) {
        String aNum = (String) a.getState("Barcode");
        String bNum = (String) b.getState("Barcode");

        return aNum.compareTo(bNum);
    }

    public String toString() {
        return "Barcode = " + persistentState.getProperty("Barcode") + "; Tree Type = " + persistentState.getProperty("TreeType") +
                "; Notes = " + persistentState.getProperty("Notes") + "; Status = " + persistentState.getProperty("Status") +
                "; Date Status Updated = " + persistentState.getProperty("DateStatusUpdated");
    }

    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    public Vector<String> getEntryListView() {
        Vector<String> entryList = new Vector();

        entryList.add(persistentState.getProperty("Barcode"));
        entryList.add(persistentState.getProperty("TreeType"));
        entryList.add(persistentState.getProperty("Notes"));
        entryList.add(persistentState.getProperty("Status"));
        entryList.add(persistentState.getProperty("DateStatusUpdated"));

        return entryList;
    }

    public void update() {
        updateStateInDatabase();
    }

    public String getUpdateStatusMessage() {
        return updateStatusMessage;
    }

    public void createAndShowTreeView(){
        Scene currentScene = myViews.get("TreeView");

        if(currentScene == null){
            View view = ViewFactory.createView("TreeView", this);
            // if (view == null) System.out.println("Null book view");
            currentScene = new Scene(view);
            myViews.put("TreeView", currentScene);
        }

        myStage.setScene(currentScene);
        myStage.sizeToScene();

        //Place in center
        WindowPosition.placeCenter(myStage);
    }
}
