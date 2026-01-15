package org.example.demo2;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller that handles pizza-view scene user actions
 * @author Angel Vargas, Maxim Trofimchuk
 */
public class PizzaController {
    private PizzaFirstController pizzaFirstController;
    private PizzaController pizzaController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;
    private static final int MAX_TOPPINGS = 7;
    private static final double TOPPING_COST = 1.69;
    @FXML private Button menuButton;
    @FXML private CheckBox nyStyleBox;
    @FXML private CheckBox chicagoBox;
    @FXML private ComboBox pizzaTypeBox;
    @FXML private ComboBox sizeBox;
    @FXML private Button printOrdersButton;
    @FXML private Button startOrderButton;
    @FXML private Button addPizzaButton;
    @FXML private TextArea outputArea;
    @FXML private Button buildYourOwnButton;
    @FXML private ImageView pizzaImageView;
    @FXML private Label totalLabel;
    @FXML private TextField runningTotal;
    @FXML private ListView<String> availableToppings;
    @FXML private ListView<String> selectedToppings;
    @FXML private Button addToppingButton;
    @FXML private Button removeToppingButton;
    @FXML private Button checkOutButton;
    @FXML private TextField crustText;
    private ObservableList<Pizza> currentOrderPizzas = FXCollections.observableArrayList();
    private ObservableList<String> toppingsSelected = FXCollections.observableArrayList();
    private ObservableList<Order> orders;
    private Order order;
    private boolean activeOrder=false;


    /**
     * Disables chicago style checkbox when ny style is selected
     */
    @FXML
    private void handleNYStyleSelection() {
        if (nyStyleBox.isSelected()) {
            chicagoBox.setDisable(true);
            chicagoBox.setSelected(false);
        } else {
            chicagoBox.setDisable(false);
        }
    }

    /**
     * Initializes combo boxes, imageview, toppings listview, and other controls
     */
    @FXML
    private void initialize(){
        ObservableList<String> toppings = FXCollections.observableArrayList();

        for (Topping topping : Topping.values()) {
            toppings.add(topping.getType());
        }

        availableToppings.setItems(toppings);
        selectedToppings.setItems(toppingsSelected);
        nyStyleBox.setOnAction(event -> {
            handleNYStyleSelection();
            updateListView();
            setPizzaImage();
        });
        chicagoBox.setOnAction(event -> {
            handleChicagoBoxSelection();
            updateListView();
            setPizzaImage();
        });
        startOrderButton.setOnAction(event -> {
            handleStartOrderButton();
            handleRunningTotal();
        });
        addPizzaButton.setOnAction(event -> {
            addPizzaOrderButton();
            handleRunningTotal();
        });
        printOrdersButton.setOnAction(event -> handlePrintOrderButton());

        addToppingButton.setOnAction(event -> {
            handleAddToppingButton();
            handleRunningTotal();
        });
        removeToppingButton.setOnAction(event -> {
            handleRemoveToppingButton();
            handleRunningTotal();
        });

        sizeBox.getItems().addAll(Size.SMALL, Size.MEDIUM, Size.LARGE);
        pizzaTypeBox.getItems().addAll("BBQ Chicken", "Meatzza", "Deluxe", "Build Your Own");

        availableToppings.setDisable(true);
        addToppingButton.setDisable(true);
        removeToppingButton.setDisable(true);

        pizzaTypeBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            availableToppings.setDisable(!"Build Your Own".equals(newValue));
            addToppingButton.setDisable(!"Build Your Own".equals(newValue));
            removeToppingButton.setDisable(!"Build Your Own".equals(newValue));
        });


        pizzaTypeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateListView();
            setPizzaImage();
            handleRunningTotal();
        });
        sizeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateListView();
            handleRunningTotal();
        });
    }

    /**
     * Updates the running total of the order as user customizes pizza options
     */
    public void handleRunningTotal() {
        double total = 0;
        Size size = (Size) sizeBox.getValue();
        String pizzaType= (String) pizzaTypeBox.getValue();

        if (size == null || pizzaTypeBox.getValue() == null){
            runningTotal.setText("0.00");
            return;
        }

        PizzaFactory pizzaFactory = new NYPizza();
        if (pizzaType.equals("BBQ Chicken")) {
            Pizza pizza = pizzaFactory.createBBQChicken(size);
            total = pizza.price();
        }
        if (pizzaType.equals("Deluxe")) {
            Pizza pizza = pizzaFactory.createDeluxe(size);
            total = pizza.price();
        }
        if (pizzaType.equals("Meatzza")) {
            Pizza pizza = pizzaFactory.createMeatzza(size);
            total = pizza.price();
        }
        if (pizzaType.equals("Build Your Own")){
            Pizza pizza = buildPizza(pizzaFactory, size);
            total = pizza.price();
        }

        String totalPrice = String.format("$%.2f", total);
        runningTotal.setText(totalPrice);
    }

    /**
     * Adds selected topping option in available toppings list view to selected toppings listview
     */
    public void handleAddToppingButton(){
        String selectedTopping = availableToppings.getSelectionModel().getSelectedItem();

        if (selectedTopping == null) {
            return;
        }

        toppingsSelected = selectedToppings.getItems();

        if (toppingsSelected.contains(selectedTopping)){
            outputArea.setText("Topping already added");
            return;
        }

        if(selectedToppings.getItems().size() >= MAX_TOPPINGS){
            outputArea.setText("Cannot add more than 7 toppings");
            return;
        }

        toppingsSelected.add(selectedTopping);
    }

    /**
     * Removes a selected topping from selected toppings listview
     */
    public void handleRemoveToppingButton(){
        String selectedTopping = selectedToppings.getSelectionModel().getSelectedItem();

        if (selectedTopping == null) {
            return;
        }

        selectedToppings.getItems().remove(selectedTopping);
    }

    /**
     * Updates selected toppings listview as user changes pizza type
     */
    public void updateListView() {
        Size size = (Size) sizeBox.getValue();
        if(size != null && pizzaTypeBox.getValue() != null){
            String pizzaType= (String) pizzaTypeBox.getValue();
            PizzaFactory pizzaFactory = new NYPizza();
            if (pizzaType.equals("BBQ Chicken")) {
                Pizza pizza = pizzaFactory.createBBQChicken(size);
                setSelectedToppings(pizza);
            }
            if (pizzaType.equals("Deluxe")) {
                Pizza pizza = pizzaFactory.createDeluxe(size);
                setSelectedToppings(pizza);
            }
            if (pizzaType.equals("Meatzza")) {
                Pizza pizza = pizzaFactory.createMeatzza(size);
                setSelectedToppings(pizza);
            }
            if (pizzaType.equals("Build Your Own")){
                selectedToppings.getItems().clear();
            }
        }
    }

    /**
     * Get the reference to the PizzaFirstController object along with other objects
     */
    public void setPizzaFirstController (PizzaFirstController controller,
                                         Stage view,
                                         Stage primaryStage,
                                         Scene primaryScene, ObservableList<Order> orders, Order order) {
        pizzaFirstController = controller;
        this.stage = view;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.orders = orders;
        this.order = order;
    }

    /**
     * Disables ny style box when chicago style is selected
     */
    @FXML
    private void handleChicagoBoxSelection() {
        if (chicagoBox.isSelected()) {
            nyStyleBox.setDisable(true);
            nyStyleBox.setSelected(false);
        } else {
            nyStyleBox.setDisable(false);
        }
    }

    /**
     * Sets the selected toppings listview with passed pizza objects toppings
     * @param pizza pizza passed to retrieve unique toppings
     */
    private void setSelectedToppings(Pizza pizza){
        if (pizza == null || pizza.getToppings() == null) {
            outputArea.setText("No toppings available.");
            selectedToppings.setItems(FXCollections.observableArrayList()); // Clear list view
            return;
        }

        ObservableList<String> toppings = FXCollections.observableArrayList();
        for (Topping topping : pizza.getToppings()) {
            toppings.add(topping.toString());
        }

        selectedToppings.setItems(toppings);
    }

    /**
     * Creates and returns a build your own pizza
     * @param pizzaFactory pizza factory interface
     * @param size size of the pizza
     * @return returns a build your own pizza object
     */
    public Pizza buildPizza(PizzaFactory pizzaFactory, Size size){
        Pizza pizza = pizzaFactory.createBuildYourOwn(size);
        BuildYourOwn byoPizza = (BuildYourOwn) pizza;
        ArrayList<Topping> toppings = new ArrayList<Topping>();
        for (String toppingName : selectedToppings.getItems()) {
            Topping topping = Topping.valueOf(toppingName.toUpperCase().replace("-", "_"));
            toppings.add(topping);
        }
        byoPizza.setToppings(toppings);
        return byoPizza;
    }

    /**
     * Adds a pizza to the order and displays pizza information
     * @param pizza pizza that is added to the order
     */
    private void addPizzaHelper(Pizza pizza){
        order.addPizza(pizza);
        outputArea.setText(pizza.toString()+ " added to order");
    }

    /**
     * Creates specified pizza object to be added to the order when add pizza button is pressed
     */
    @FXML
    private void addPizzaOrderButton(){
        if (!activeOrder){
            outputArea.setText("Start an order");
            return;
        }
        if (sizeBox.getValue()==null || pizzaTypeBox.getValue()==null){
            outputArea.setText("Please enter both a size and a type of pizza please!");
            return;
        }
        Size size= (Size) sizeBox.getValue();
        String pizzaType= (String) pizzaTypeBox.getValue();
        if (nyStyleBox.isSelected()){
            PizzaFactory pizzaFactory = new NYPizza();
            if (pizzaType.equals("BBQ Chicken")){
                Pizza pizza = pizzaFactory.createBBQChicken(size);
                addPizzaHelper(pizza);
            }
            if (pizzaType.equals("Deluxe")){
                Pizza pizza = pizzaFactory.createDeluxe(size);
                addPizzaHelper(pizza);
            }
            if (pizzaType.equals("Meatzza")){
                Pizza pizza = pizzaFactory.createMeatzza(size);
                addPizzaHelper(pizza);
            }
            if (pizzaType.equals("Build Your Own")){
                Pizza pizza = buildPizza(pizzaFactory, size);
                addPizzaHelper(pizza);
            }
        }
        if (chicagoBox.isSelected()){
            PizzaFactory pizzaFactory = new ChicagoPizza();
            if (pizzaType.equals("BBQ Chicken")){
                Pizza pizza = pizzaFactory.createBBQChicken(size);
                addPizzaHelper(pizza);
            }
            if (pizzaType.equals("Deluxe")){
                Pizza pizza = pizzaFactory.createDeluxe(size);
                addPizzaHelper(pizza);
            }
            if (pizzaType.equals("Meatzza")){
                Pizza pizza = pizzaFactory.createMeatzza(size);
                addPizzaHelper(pizza);
            }
            if (pizzaType.equals("Build Your Own")){
                Pizza pizza = buildPizza(pizzaFactory, size);
                addPizzaHelper(pizza);
            }
        }
    }

    /**
     * Starts an order by creating a new order object and updates its order number
     */
    @FXML
    private void handleStartOrderButton(){

        order = new Order();
        activeOrder=true;

        int orderNum = pizzaFirstController.getOrders().size() + 1;
        order.setNumber(orderNum);

        outputArea.setText("Order number "+ orderNum + " started");
    }

    /**
     * Prints order information to text area when print order button is pressed
     */
    @FXML
    private void handlePrintOrderButton(){
        if (activeOrder && order.getPizzas().size()>=1){
            outputArea.setText("Pizza Order "+ order.getNumber()+ " so far: \n" + order.toString());
        }
        else{
            outputArea.setText ("No orders to print.");
        }

    }

    /**
     * Updates the image field to an image of specified pizza based on selected pizza style and type
     */
    private void setPizzaImage() {
        String imagePath = "";
        String pizzaType= (String) pizzaTypeBox.getValue();

        if (nyStyleBox.isSelected()) {
            imagePath = "NYPizza.png";
            if (pizzaType != null) {
                if (pizzaType.equals("BBQ Chicken")) {
                    imagePath = "NYBBQ.png";
                    crustText.setText("Thin");
                }
                if (pizzaType.equals("Deluxe")) {
                    imagePath = "NYDeluxe.png";
                    crustText.setText("Brooklyn");
                }
                if (pizzaType.equals("Meatzza")) {
                    imagePath = "NYMeatzza.png";
                    crustText.setText("Hand-tossed");
                }
                if (pizzaType.equals("Build Your Own")){
                    crustText.setText("Hand-tossed");
                }
            }
        } else if (chicagoBox.isSelected()) {
            imagePath = "ChicagoPizza.png";
            if (pizzaType != null) {
                if (pizzaType.equals("BBQ Chicken")) {
                    imagePath = "ChiBBQ.png";
                    crustText.setText("Pan");
                }
                if (pizzaType.equals("Deluxe")) {
                    imagePath = "ChiDeluxe.png";
                    crustText.setText("Deep Dish");
                }
                if (pizzaType.equals("Meatzza")) {
                    imagePath = "ChiMeatzza.png";
                    crustText.setText("Stuffed");
                }
                if (pizzaType.equals("Build Your Own")){
                    crustText.setText("Pan");
                }
            }
        }

        if (!imagePath.isEmpty()) {
            Image pizzaImage = new Image(getClass().getResource(imagePath).toExternalForm());
            pizzaImageView.setImage(pizzaImage);
        }
    }

    /**
     * Navigate back to pizza-first-view
     */
    @FXML
    protected void displayMainView() {
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pizza-first-view.fxml"));
            root = (BorderPane) loader.load();

            Scene scene = new Scene(root, 600, 400);

            primaryStage.setScene(scene);
            PizzaFirstController pizzaFirstController = loader.getController();

            pizzaFirstController.setPizzaController(this, view1, primaryStage, primaryScene, orders, order);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading pizza-first-view.fxml.");
            alert.setContentText("Couldn't load pizza-first-view.fxml.");
            alert.showAndWait();
        }
    }


    /**
     * Navigate to checkout-view
     */
    @FXML
    protected void displayCheckoutView() {
        if (!activeOrder) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Hi");
            alert.setHeaderText(null);
            alert.setContentText("No order to checkout");
            alert.showAndWait();
            return;
        }
        Stage view1 = new Stage();
        BorderPane root;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout-view.fxml"));
            root = (BorderPane) loader.load();
            CheckoutController checkoutController = loader.getController();

            Scene scene = new Scene(root, 600, 400);

            primaryStage.setScene(scene);

            checkoutController.setPizzaController(this, view1, primaryStage, primaryScene);
            checkoutController.setOrders(pizzaFirstController.getOrders());
            checkoutController.setOrder(order);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading pizza-first-view.fxml.");
            alert.setContentText("Couldn't load pizza-first-view.fxml.");
            alert.showAndWait();
        }

    }
}