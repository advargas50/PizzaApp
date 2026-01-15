package org.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller that handles checkout-view scene user actions
 * @author Angel Vargas, Maxim Trofimchuk
 */
public class CheckoutController {
    private PizzaFirstController pizzaFirstController;
    private PizzaController pizzaController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;
    static final Double TAX_RATE = 0.06625;
    @FXML private Button menuButton;
    @FXML private TextField orderNumber;
    @FXML private TextField subTotal;
    @FXML private TextField salesTax;
    @FXML private TextField orderTotal;
    @FXML private ListView<Pizza> orderListView;
    @FXML private Button placeOrderButton;
    @FXML private Button removePizzaButton;
    @FXML private Button clearOrderButton;
    private ObservableList<Order> orders;
    private Order order;


    /**
     * Sets this controller's orders list to the orders list from previous controller
     * @param orders list containing application's placed and removed orders
     */
    public void setOrders(ObservableList<Order> orders){
        this.orders = orders;

    }

    /**
     * Sets this controller's current order object to the order object from previous controller
     * @param order object representing the current order being worked on
     */
    public void setOrder(Order order){
        if(order == null){
            clearOrder();
        }
        else{
            this.order = order;
            updateView();
        }
    }

    /**
     * Updates the displayed information about the current order in the checkout-view
     */
    private void updateView(){
        int num = order.getNumber();
        orderNumber.setText(String.valueOf(num));

        ObservableList<Pizza> pizzas = FXCollections.observableArrayList();

        pizzas.addAll(order.getPizzas());
        orderListView.setItems(pizzas);

        double total = order.calculateTotalPrice();
        String sub = String.format("$%.2f", total);
        String tax = String.format("$%.2f", total * TAX_RATE);
        String totalPrice = String.format("$%.2f", total * (1 + TAX_RATE));
        subTotal.setText(sub);
        salesTax.setText(tax);
        orderTotal.setText(totalPrice);
    }

    /**
     * Initializes CheckoutController buttons and specifies events to be executed on their action
     */
    @FXML
    private void initialize(){
        removePizzaButton.setOnAction(event -> handleRemovePizzaButton());
        placeOrderButton.setOnAction(event -> handlePlaceOrderButton());
        clearOrderButton.setOnAction(event -> clearOrder());
    }

    /**
     * Removes a pizza object from the current order's list of pizzas when removePizzaButton is pressed
     */
    @FXML
    private void handleRemovePizzaButton(){
        Pizza selectedPizza = orderListView.getSelectionModel().getSelectedItem();

        if (selectedPizza == null) {
            return;
        }

        if (orderListView.getItems().isEmpty()){

        }

        orderListView.getItems().remove(selectedPizza);
        order.removePizza(selectedPizza);

        if(orderListView.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // You can change the type to WARNING, CONFIRMATION, etc.
            alert.setTitle("Order");
            alert.setHeaderText(null); // Optional, can be null if no header is needed
            alert.setContentText("All pizzas removed. Start a new order");
            alert.showAndWait();
            clearOrder();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION); // You can change the type to WARNING, CONFIRMATION, etc.
        alert.setTitle("ORDER AFTER REMOVE");
        alert.setHeaderText(null); // Optional, can be null if no header is needed
        alert.setContentText(order.toString());
        alert.showAndWait();

        updateView();
    }

    /**
     * Places an order and adds that order to orders list when placeOrderButton is pressed
     */
    @FXML
    private void handlePlaceOrderButton(){
        if (orderListView.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // You can change the type to WARNING, CONFIRMATION, etc.
            alert.setTitle("PLACED ORDER");
            alert.setHeaderText(null); // Optional, can be null if no header is needed
            alert.setContentText("Order has no pizzas. Start a new order");
            alert.showAndWait();
            return;
        }

        orders.add(order);

        Alert alert = new Alert(Alert.AlertType.INFORMATION); // You can change the type to WARNING, CONFIRMATION, etc.
        alert.setTitle("PLACED ORDER");
        alert.setHeaderText(null); // Optional, can be null if no header is needed
        alert.setContentText(order.toString() + " PLACED");
        alert.showAndWait();

        clearOrder();
    }

    /**
     * Clears the current order information when all pizzas from an order are removed or when clearOrderButton is pressed
     */
    private void clearOrder(){
        orderNumber.setText("");

        orderListView.getItems().clear();

        subTotal.setText("");
        salesTax.setText("");
        orderTotal.setText("");

        order = new Order();
    }

    /**
     * Gets the reference to the PizzaFirstController object along with other objects
     */
    public void setPizzaFirstController (PizzaFirstController controller,
                                         Stage stage,
                                         Stage primaryStage,
                                         Scene primaryScene, ObservableList<Order> orders, Order order) {
        this.pizzaFirstController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.orders = orders;
        this.order = order;

        if (order ==  null){
            clearOrder();
        }
        else{
            updateView();
        }
    }

    /**
     * Get the reference to the PizzaController object along with other objects
     */
    public void setPizzaController (PizzaController controller,
                                         Stage stage, Stage primaryStage,
                                    Scene primaryScene) {
        this.pizzaController = controller;
        this.stage = stage;
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
            pizzaFirstController.setCheckoutController(this, view1, primaryStage, primaryScene, orders, order);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading pizza-first-view.fxml.");
            alert.setContentText("Couldn't load pizza-first-view.fxml.");
            alert.showAndWait();
        }
    }

}
