package edu.brockport.treelotsales.model;

import edu.brockport.treelotsales.impresario.ModelRegistry;
import edu.brockport.treelotsales.event.Event;
import edu.brockport.treelotsales.impresario.IModel;
import edu.brockport.treelotsales.impresario.IView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import edu.brockport.treelotsales.userinterface.MainStageContainer;
import edu.brockport.treelotsales.userinterface.View;
import edu.brockport.treelotsales.userinterface.ViewFactory;
import edu.brockport.treelotsales.userinterface.WindowPosition;

import java.util.Hashtable;
import java.util.Properties;

public class TLC implements IView, IModel {

    private ModelRegistry myRegistry;

    private Hashtable<String, Scene> myViews;
    private Stage myStage;

    private String transactionErrorMessage = "";

    public TLC(){
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        myRegistry = new ModelRegistry("TLC");

        setDependencies();
        createAndShowLibrarianView();
    }

    private void setDependencies(){
        Properties dependencies = new Properties();
        dependencies.setProperty("RegisterScout", "TransactionError");
        dependencies.setProperty("UpdateScout", "TransactionError");
        dependencies.setProperty("DeleteScout", "TransactionError");
        dependencies.setProperty("AddTree", "TransactionError");
        dependencies.setProperty("UpdateTree", "TransactionError");
        dependencies.setProperty("DeleteTree", "TransactionError");
        dependencies.setProperty("AddTreeType", "TransactionError");
        dependencies.setProperty("UpdateTreeType", "TransactionError");
        dependencies.setProperty("OpenShift", "TransactionError");
        dependencies.setProperty("SellTree", "TransactionError");
        dependencies.setProperty("CloseShift", "TransactionError");
        myRegistry.setDependencies(dependencies);
    }

    public Object getState(String key){
        if(key.equals("TransactionError")){
            return transactionErrorMessage;
        } else {
            return "";
        }
    }

    //Left off here
    public void stateChangeRequest(String key, Object value){
        if(key.equals("RegisterScout")){
            registerNewScout();
        }else if(key.equals("NewPatron")){
            createNewPatron();
        } else if (key.equals("SearchBooks")) {
            createAndShowSearchView("Books");
        } else if(key.equals("DoBookSearch")){
            searchBooks((String)value);
        } else if(key.equals("SearchPatrons")){
            createAndShowSearchView("Patrons");
        } else if(key.equals("DoPatronSearch")){
            searchPatrons((String)value);
        }else if(key.equals("Done")){
            createAndShowLibrarianView();
        }

        myRegistry.updateSubscribers(key, this);
    }

    public void createAndShowLibrarianView(){
        Scene currentScene = myViews.get("LibrarianView");

        if(currentScene == null){
            View newView = ViewFactory.createView("LibrarianView", this);
            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }

        swapToView(currentScene);
    }

    public void swapToView(Scene newScene){
        if (newScene == null) {
            System.out.println("Librarian.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();
        WindowPosition.placeCenter(myStage);
    }

    @Override
    public void subscribe(String key, IView subscriber) {
        myRegistry.subscribe(key, subscriber);
    }

    @Override
    public void unSubscribe(String key, IView subscriber) {
        myRegistry.unSubscribe(key, subscriber);
    }

    @Override
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    private void registerNewScout(){
        Scout scout = new Scout()
        scout.subscribe("TLCView", this);
        scout.createAndShowScoutView();
    }

    private void createNewPatron(){
        Patron patron = new Patron();
        patron.subscribe("LibrarianView", this);
        patron.createAndShowPatronView();
    }

    private void searchBooks(String title){
        BookCollection books = new BookCollection();

        books.findBooksWithTitleLike(title);
        System.out.println(books);
        createAndShowCollectionView("Book", books);
    }

    public void createAndShowSearchView(String type){
        Scene currentScene = myViews.get("Search" + type + "View");

        if(currentScene == null){
            View newView = ViewFactory.createView("Search" + type + "View", this);
            currentScene = new Scene(newView);
            myViews.put(type + "SearchView", currentScene);
        }

        swapToView(currentScene);
    }

    public void createAndShowCollectionView(String type, Object collection){
        Scene currentScene = myViews.get(type + "CollectionView");

        if(currentScene == null){
            View newView = ViewFactory.createView(type + "CollectionView", (IModel)collection);
            currentScene = new Scene(newView);
            myViews.put(type + "CollectionView", currentScene);
        }

        swapToView(currentScene);
    }

    private void searchPatrons(String zip){
        PatronCollection patrons = new PatronCollection();

        patrons.findPatronsAtZipcode(zip);
        createAndShowCollectionView("Patron", patrons);
    }
}
