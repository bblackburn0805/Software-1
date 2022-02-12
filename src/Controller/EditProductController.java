/*

        Brandon Blackburn
        03/27/20
        C482 - Software 1

 */

package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProductController implements Initializable {
    Stage stage;
    Parent scene;
    int index;
    Product editProduct = new Product(0,"", 0, 0, 0, 0);
    ObservableList<Part> filterPart = FXCollections.observableArrayList();
    ObservableList<Part> unAssociated = FXCollections.observableArrayList();

    @FXML
    private TextField textID;

    @FXML
    private TextField textName;

    @FXML
    private TextField textInv;

    @FXML
    private TextField textPrice;

    @FXML
    private TextField textMin;

    @FXML
    private TextField textMax;

    @FXML
    private Button buttonSearchPart;

    @FXML
    private TextField textSearchPart;

    @FXML
    private TableView<Part> tableViewUnassociated;

    @FXML
    private Button buttonAdd;

    @FXML
    private TableView<Part> tableViewAssociated;

    @FXML
    private Button buttonRemove;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonCancel;

    @FXML
    private TableColumn<Product, Integer> noId;

    @FXML
    private TableColumn<Product, String> noName;

    @FXML
    private TableColumn<Product, Integer> noInv;

    @FXML
    private TableColumn<Product, Double> noPrice;

    @FXML
    private TableColumn<Product, Integer> yesId;

    @FXML
    private TableColumn<Product, String> yesName;

    @FXML
    private TableColumn<Product, Integer> yesInv;

    @FXML
    private TableColumn<Product, Double> yesPrice;

    @FXML
    void onActionSearchEnter(ActionEvent event) {onActionSearch(event);}


    @FXML
    void onActionAdd(ActionEvent event) {
        if (!tableViewUnassociated.getSelectionModel().isEmpty())
            editProduct.addAssociatedPart((Part)tableViewUnassociated.getSelectionModel().getSelectedItem());
        setMenu();
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    @FXML
    void onActionRemove(ActionEvent event) {
        if (!tableViewAssociated.getSelectionModel().isEmpty())
            editProduct.deleteAssociatedPart((Part)tableViewAssociated.getSelectionModel().getSelectedItem());
        setMenu();
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException
    {
        try {
            int id = Integer.parseInt(textID.getText());
            String name = textName.getText();
            double price = Double.parseDouble(textPrice.getText());
            int stock = Integer.parseInt(textInv.getText());
            int min = Integer.parseInt(textMin.getText());
            int max = Integer.parseInt(textMax.getText());
            double partPrice = 0;
            if (min < max && max > min) {                                                           //SET 1: preventing the maximum field from having a value below the minimum field
                Product product = new Product(id, name, price, stock, min, max);                    //SET 1: preventing the minimum field from having a value above the maximum field
                for (Part part : editProduct.getAllAssociatedParts())
                    product.addAssociatedPart(part);
                for (Part part : product.getAllAssociatedParts()){
                    partPrice = partPrice + part.getPrice();
                }
                if (price > partPrice) {                                                             //SET 2: ensuring that the price of a product cannot be less than the cost of the parts
                    Inventory.updateProduct(index, product);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Entry");
                    alert.setContentText("Product Price must be more than total Price of Parts.");
                    alert.showAndWait();
                }
            }
            else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Entry");
                    alert.setContentText("Please ensure Max and Min are proper values.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Entry");
            alert.setContentText("Please enter valid values");
            alert.showAndWait();
        }
    }


    @FXML
    void onActionSearch(ActionEvent event) {
        filterPart.clear();
        if (!textSearchPart.getText().isEmpty()) {
            try {
                for (Part part : unAssociated) {
                    if (part.getId() == Integer.parseInt(textSearchPart.getText()))
                        filterPart.add(part);
                }
            } catch (NumberFormatException error) {
                for (Part part : unAssociated) {
                    if ((part.getName().toLowerCase()).contains(textSearchPart.getText().toLowerCase()))
                        filterPart.add(part);
                }
            }
        }
        setMenu();
    }



    /*----------------------------My own methods here--------------------------*/

    @Override
    public void initialize(URL url, ResourceBundle rb){
        unAssociated.setAll(editProduct.getAllAssociatedParts());
        filterPart.setAll(unAssociated);
        setMenu();
    }


    public void sendProduct(Product product){
        textID.setText(String.valueOf(product.getId()));
        textName.setText(product.getName());
        textPrice.setText(String.valueOf(product.getPrice()));
        textInv.setText(String.valueOf(product.getStock()));
        textMin.setText(String.valueOf(product.getMin()));
        textMax.setText(String.valueOf(product.getMax()));
        index = setIndex(product);
        tableViewAssociated.setItems(product.getAllAssociatedParts());

        editProduct = product;
        setMenu();

    }

    public int setIndex(Product product){
        int x = -1;
        for (Product finder : Inventory.getAllProducts())
        {
            x++;
            if (finder == product)
                return x;
        }
        return x++;
    }


    public void setMenu(){

        setUnAssociated();
        if (textSearchPart.getText().isBlank())
            filterPart.setAll(unAssociated);
        tableViewUnassociated.setItems(filterPart);
        noId.setCellValueFactory(new PropertyValueFactory<>("id"));
        noInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        noName.setCellValueFactory(new PropertyValueFactory<>("name"));
        noPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        if (!editProduct.getAllAssociatedParts().isEmpty())
            tableViewAssociated.setItems(editProduct.getAllAssociatedParts());
        yesId.setCellValueFactory(new PropertyValueFactory<>("id"));
        yesInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        yesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        yesPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void setUnAssociated(){
        unAssociated.setAll(Inventory.getAllParts());                   //unAssociated is set with all inventory parts
        for (Part parts : Inventory.getAllParts()) {                    //goes through all parts in inventory
            for (Part unPart : editProduct.getAllAssociatedParts()){    //goes through all parts associated with this product
                if (unPart == parts)
                    unAssociated.remove(parts);                            //if a part is associated with the product, it is removed from unassociated
            }
        }
    }
}


