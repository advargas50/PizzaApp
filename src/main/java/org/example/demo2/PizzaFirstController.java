package org.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

/**
 * Controller that handles pizza-first-view scene user actions
 * @author Angel Vargas, Maxim Trofimchuk
 */
public class PizzaFirstController {
    private PizzaFirstController pizzaFirstController;
    private PizzaController pizzaController;
    private CheckoutController checkoutController;
    private OrdersController ordersController;
    private Stage stage;
    @FXML
    private Button pizzaButton;
    @FXML
    private Button nyButton;
    @FXML
    private Button currentOrderButton;
    @FXML
    private Button pastOrdersButton;

    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private Order order;
    private Stage primaryStage; //the reference of the main window.
    private Scene primaryScene; //the ref. of the scene set to the primaryStage

    /**
     * Set the reference of the stage and scene before show()
     * @param stage the stage used to display the scene
     * @param scene the scene set to the stage
     */
    public void setPrimaryStage(Stage stage, Scene scene) {
        primaryStage = stage;
        primaryScene = scene;
    }

    /**
     * Returns list of orders
     * @return returns orders list
     */
    public ObservableList<Order> getOrders(){
        return orders;
    }

    /**
     * Sets order object to previous controller's order
     * @param order previous controller's order object
     */
    public void setOrder(Order order){
        this.order = order;
    }

    /**
     * Sets orders list to previous controller's orders list
     * @param orders orders list from previous controller
     */
    public void setOrders(ObservableList<Order> orders){
        this.orders = orders;
    }

    /**
     * Navigates to pizza view
     */
    @FXML
    protected void displaySecondView() {
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pizza-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 600, 600);

            primaryStage.setScene(scene);
            PizzaController pizzaController = loader.getController();
            if(this == null){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Hi");
                alert.setHeaderText(null);
                alert.setContentText("message");
                alert.showAndWait();
            }

            pizzaController.setPizzaFirstController(this, view1, primaryStage, primaryScene, orders, order);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading pizza-view.fxml.");
            alert.setContentText("Couldn't load pizza-view.fxml.");
            alert.showAndWait();
        }
    }

    /**
     * Navigates to checkout-view
     */
    @FXML
    protected void displayCheckoutView() {
        if (order == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No order to checkout");
            alert.showAndWait();
            return;
        }
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 600, 400);

            primaryStage.setScene(scene);
            CheckoutController checkoutController = loader.getController();

            checkoutController.setPizzaFirstController(this, view1, primaryStage, primaryScene, orders, order);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading checkout-view.fxml.");
            alert.setContentText("Couldn't load checkiout-view.fxml.");
            alert.showAndWait();
        }
    }

    /**
     * Navigates to orders-view
     */
    @FXML
    protected void displayOrdersView() {
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("orders-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 600, 400);

            primaryStage.setScene(scene);
            OrdersController ordersController = loader.getController();

            ordersController.setOrders(orders);
            ordersController.setOrder(order);
            ordersController.setPizzaFirstController(this, view1, primaryStage, primaryScene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading checkout-view.fxml.");
            alert.setContentText("Couldn't load checkiout-view.fxml.");
            alert.showAndWait();
        }
    }

    /**
     * onMouseEntered event.
     */
    @FXML
    public void pizzaMouseEnter() {
        pizzaButton.setStyle("-fx-background-color: blue");
    }

    /**
     * onMouseExited event.
     */
    @FXML
    public void pizzaMouseExit() {
        pizzaButton.setStyle("-fx-background-color: black");
    }

    /**
     * onMouseEntered event for orders button
     */
    @FXML
    public void ordersMouseEnter() {
        pastOrdersButton.setStyle("-fx-background-color: blue");
    }

    /**
     * onMouseExited event for orders button
     */
    @FXML
    public void ordersMouseExit() {
        pastOrdersButton.setStyle("-fx-background-color: black");
    }

    /**
     * Get the reference to the PizzaController object along with other objects
     */
    public void setPizzaController (PizzaController controller,
                                         Stage view,
                                         Stage primaryStage,
                                         Scene primaryScene, ObservableList<Order> orders, Order order) {
        this.pizzaController = controller;
        this.stage = view;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.orders = orders;
        this.order = order;
    }

    /**
     * Get the reference to the CheckoutController object along with other objects
     */
    public void setCheckoutController (CheckoutController controller,
                                    Stage view,
                                    Stage primaryStage,
                                    Scene primaryScene, ObservableList<Order> orders, Order order) {
        this.checkoutController = controller;
        this.stage = view;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.orders = orders;
        this.order = order;
    }

    /**
     * Get the reference to the OrdersController object along with other objects
     */
    public void setOrdersController (OrdersController controller,
                                       Stage view,
                                       Stage primaryStage,
                                       Scene primaryScene, ObservableList<Order> orders, Order order) {
        this.ordersController = controller;
        this.stage = view;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.orders = orders;
        this.order = order;
    }
}