package edu.brockport.treelotsales.model;

import edu.brockport.treelotsales.impresario.IView;

import java.util.Properties;
import java.util.Vector;

public class ScoutCollection extends EntityBase implements IView {
    private static final String tableName = "Scout";

    private Vector<Scout> scouts;

    public ScoutCollection() {
        super(tableName);
        scouts = new Vector<Scout>();
    }

    //Probably doesn't work as intended, need to fix later.
    public void findScoutsOlderThanDate(String year) {
        String query = "SELECT * FROM " + tableName + " WHERE (DateOfBirth < " + year + ")";
        Vector allDataRetrieved = getSelectQueryResult(query);

        for(int i = 0; i < allDataRetrieved.size(); i++) {
            Scout s = new Scout((Properties)(allDataRetrieved.get(i)));
            addScout(s);
        }

    }

    private void addScout(Scout s)
    {
        //accounts.add(a);
        int index = findIndexToAdd(s);
        scouts.insertElementAt(s,index); // To build up a collection sorted on some key
    }

    private int findIndexToAdd(Scout a)
    {
        //users.add(u);
        int low=0;
        int high = scouts.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Scout midSession = scouts.elementAt(middle);

            int result = Scout.compare(a,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }

    @Override
    public Object getState(String key)
    {
        if (key.equals("Scouts"))
            return scouts;
        else
        if (key.equals("ScoutList"))
            return this;
        return null;
    }

    @Override
    public void stateChangeRequest(String key, Object value)
    {
        myRegistry.updateSubscribers(key, this);
    }

    @Override
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public String toString() {
        String s = "";

        for(Scout b : scouts) {
            s += b.toString() + "\n";
        }

        return s;
    }


    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }
}
