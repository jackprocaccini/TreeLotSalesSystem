package edu.brockport.treelotsales.model;

import edu.brockport.treelotsales.exception.InvalidPrimaryKeyException;
import edu.brockport.treelotsales.impresario.IModel;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class Transaction extends EntityBase implements IModel {
    private static final String myTableName = "Transaction";
    private String updateStatusMessage = "";

    private Properties dependencies;

    public Transaction(String ID) throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (ID = " + ID + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            if (size == 0) {
                throw new InvalidPrimaryKeyException("No transactions matching ID : "
                        + ID + " found.");

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
                throw new InvalidPrimaryKeyException("Multiple transactions matching ID : "
                        + ID + " found.");
            }
        } else {
            throw new InvalidPrimaryKeyException("No transactions matching ID : "
                    + ID + " found.");
        }
    }

    public Transaction(Properties props) {
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

    public Transaction() {
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
        if (key.equals("ProcessTransaction")) {
            processTransaction(value);
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

    private void processTransaction(Object props) {
        this.persistentState = (Properties) (props);
        updateStateInDatabase();
    }

    private void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("ID") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("ID", persistentState.getProperty("ID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Data for transaction : " + persistentState.getProperty("ID") + " updated successfully in database!";
            } else {
                Integer treeTypeID =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("ID", "" + treeTypeID);
                updateStatusMessage = "Data for transaction : "
                        + persistentState.getProperty("ID")
                        + "installed successfully in database!";
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            updateStatusMessage = "Error in installing transaction data in database!";
        }
    }

    public static int compare(Transaction a, Transaction b) {
        String aNum = (String) a.getState("ID");
        String bNum = (String) b.getState("ID");

        return aNum.compareTo(bNum);
    }

    public String toString() {
        return "ID = " + persistentState.getProperty("ID") + "; Sesssion ID = " + persistentState.getProperty("SessionID") +
                "; Transaction Type = " + persistentState.getProperty("TransactionType") + "; Barcode = " + persistentState.getProperty("Barcode") +
                "; Transaction Amount = " + persistentState.getProperty("TransactionAmount") + "; Payment Method = " + persistentState.getProperty("PaymentMethod") +
                "; Customer Name = " + persistentState.getProperty("CustomerName") + "; Customer Phone = " + persistentState.getProperty("CustomerPhone") +
                "; Customer Email = " + persistentState.getProperty("CustomerEmail") + "; Transaction Date = " + persistentState.getProperty("TransactionDate") +
                "; Transaction Time = " + persistentState.getProperty("TransactionTime") + "; Date Status Updated" + persistentState.getProperty("DateStatusUpdated");
    }

    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    public Vector<String> getEntryListView() {
        Vector<String> entryList = new Vector();

        entryList.add(persistentState.getProperty("ID"));
        entryList.add(persistentState.getProperty("SessionID"));
        entryList.add(persistentState.getProperty("TransactionType"));
        entryList.add(persistentState.getProperty("Barcode"));
        entryList.add(persistentState.getProperty("TransactionAmount"));
        entryList.add(persistentState.getProperty("PaymentMethod"));
        entryList.add(persistentState.getProperty("CustomerName"));
        entryList.add(persistentState.getProperty("CustomerPhone"));
        entryList.add(persistentState.getProperty("CustomerEmail"));
        entryList.add(persistentState.getProperty("TransactionEmail"));
        entryList.add(persistentState.getProperty("TransactionTime"));
        entryList.add(persistentState.getProperty("DateStatusUpdated"));

        return entryList;
    }

    public void update() {
        updateStateInDatabase();
    }

    public String getUpdateStatusMessage() {
        return updateStatusMessage;
    }
}
