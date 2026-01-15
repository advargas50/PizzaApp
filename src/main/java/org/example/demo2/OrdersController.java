package org.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import java.io.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Controller that handles checkout-view scene user actions
 * @author Angel Vargas, Maxim Trofimchuk
 */
public class OrdersController {
    private PizzaFirstController pizzaFirstController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;
    private static final double TAX_RATE = 0.06625;
    @FXML private ComboBox orderNumber;
    @FXML private TextField orderTotal;
    @FXML private ListView<Pizza> orderListView;
    @FXML private Button cancelOrderButton;
    @FXML private Button exportOrdersButton;
    @FXML private Button menuButton;
    private ObservableList<Order> orders;
    private Order order;

    /**
     * Sets this controller's orders list to the orders list from previous controller
     * @param orders list containing application's placed and removed orders
     */
    public void setOrders(ObservableList<Order> orders){
        this.orders = orders;

        for (Order order : orders){
            if (order != null) {
                orderNumber.getItems().add(order.getNumber());
            }
        }

    }

    /**
     * Sets this controller's current order object to the order object from previous controller
     * @param order object representing the current order being worked on
     */
    public void setOrder(Order order){
        if(order == null){
            return;
        }
        else{
            this.order = order;

        }
    }

    /**
     * Cancels a selected order when cancel button is pressed
     */
    @FXML
    private void handleCancelButton(){
        if (orderNumber.getValue() == null || orderListView.getItems().isEmpty()){
            return;
        }

        int orderNumIndex = (Integer) orderNumber.getSelectionModel().getSelectedIndex();
        int orderNum = (Integer) orderNumber.getValue();

        orderNumber.getItems().remove(orderNumIndex);
        orders.set((orderNum - 1), null);
        orderListView.getItems().clear();
        orderTotal.setText("");

    }

    /**
     * Exports all existing orders information to a selected .txt file
     * @throws FileNotFoundException exception when selected file is not found
     */
    @FXML
    private void handleExportButton() throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Target File for the Export");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File targetFile = chooser.showSaveDialog(stage);

        if (targetFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {
                for (Order order : orders) {
                    if (order != null) {
                        writer.write(order.toString());
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Initializes the scene
     */
    @FXML
    private void initialize(){

    }

    /**
     * Updates information displayed in scene whenever a new order number is selected from the combo box
     */
    @FXML
    private void handleOrderNumBox() {
        if(orderNumber.getValue() == null){
            return;
        }

        int orderNum = (Integer) orderNumber.getValue();
        order = orders.get(orderNum - 1);

        String total = String.format("$%.2f", order.calculateTotalPrice() * (1 + TAX_RATE));
        orderTotal.setText(total);

        ObservableList<Pizza> pizzas = FXCollections.observableArrayList();

        pizzas.addAll(order.getPizzas());
        orderListView.setItems(pizzas);

    }



    /**
     * Get the reference to the PizzaFirstController object along with other objects
     */
    public void setPizzaFirstController (PizzaFirstController controller,
                                         Stage view,
                                         Stage primaryStage,
                                         Scene primaryScene) {
        pizzaFirstController = controller;
        this.stage = view;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
    }

    /**
     * Navigate back to the main view.
     */
    @FXML
    protected void displayMain() {
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pizza-first-view.fxml"));
            root = (BorderPane) loader.load();

            Scene scene = new Scene(root, 600, 400);

            primaryStage.setScene(scene);
            PizzaFirstController pizzaFirstController = loader.getController();

            pizzaFirstController.setOrder(order);
            pizzaFirstController.setOrders(orders);
            pizzaFirstController.setOrdersController(this, view1, primaryStage, primaryScene, orders, order);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading pizza-first-view.fxml.");
            alert.setContentText("Couldn't load pizza-first-view.fxml.");
            alert.showAndWait();
        }
    }

}