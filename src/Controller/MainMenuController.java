/*

        Brandon Blackburn
        03/27/20
        C482 - Software 1

 */

package Controller;

import Model.Inventory;
import Model.Part;
import Model.Main;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    Stage stage;
    Parent scene;
    public ObservableList<Part> filterPart = FXCollections.observableArrayList();
    ObservableList<Product> filterProduct = FXCollections.observableArrayList();


    @FXML
    private Button buttonSearchPart;

    @FXML
    private TextField textSearchPart;

    @FXML
    private Button buttonAddPart;

    @FXML
    private Button buttonEditPart;

    @FXML
    private Button buttonRemovePart;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonSearchProduct;

    @FXML
    private TextField textSearchProduct;

    @FXML
    private Button buttonAddProduct;

    @FXML
    private Button buttonEditProduct;

    @FXML
    private Button buttonRemoveProduct;

    @FXML
    private Button buttonExit1;

    @FXML
    private TableView<Part> tableView;

    @FXML
    private TableColumn<Part, Integer> colPartID;

    @FXML
    private TableColumn<Part, String> colPartName;

    @FXML
    private TableColumn<Part, Integer> colPartInv;

    @FXML
    private TableColumn<Part, Double> colPartPrice;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> colProductID;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Integer> colProductInv;

    @FXML
    private TableColumn<Product, Double> colProductPrice;

    @FXML
    void onActionAddPart(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionEditPart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/EditPart.fxml"));
        loader.load();

        EditPartController EPController = loader.getController();
        EPController.sendPart((Part)tableView.getSelectionModel().getSelectedItem());


        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }
    @FXML
    void onActionEditProduct(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/EditProduct.fxml"));
        loader.load();

        EditProductController EPController = loader.getController();
        EPController.sendProduct((Product)productTableView.getSelectionModel().getSelectedItem());


        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionRemovePart(ActionEvent event) {
        Inventory.deletePart((Part)tableView.getSelectionModel().getSelectedItem());
        setMenu();
    }

    @FXML
    void onActionRemoveProduct(ActionEvent event) {
        Inventory.deleteProduct((Product) productTableView.getSelectionModel().getSelectedItem());
        setMenu();
    }

    @FXML
    void onActionSearchPart(ActionEvent event) {
        filterPart.clear();
        if (!textSearchPart.getText().isEmpty()) {
            try {
                for (Part part : Inventory.getAllParts()) {
                    if (part.getId() == Integer.parseInt(textSearchPart.getText()))
                        filterPart.add(part);
                }
            } catch (NumberFormatException error) {
                for (Part part : Inventory.getAllParts()) {
                    if ((part.getName().toLowerCase()).contains(textSearchPart.getText().toLowerCase()))
                        filterPart.add(part);
                }
            }
        }
        setMenu();
    }

    @FXML
    void onActionSearchProduct(ActionEvent event) {
        filterProduct.clear();
        if (!textSearchProduct.getText().isEmpty()) {
            try {
                for (Product pro : Inventory.getAllProducts()) {
                    if (pro.getId() == Integer.parseInt(textSearchProduct.getText()))
                        filterProduct.add(pro);
                }
            } catch (NumberFormatException error) {
                for (Product pro : Inventory.getAllProducts()) {
                    if ((pro.getName().toLowerCase()).contains(textSearchProduct.getText().toLowerCase()))
                        filterProduct.add(pro);
                }
            }
        }
        setMenu();
    }

    @FXML
    void onActionSearchProductEnter(ActionEvent event) {
        onActionSearchProduct(event);
    }


    @FXML
    void onActionSearchPartEnter(ActionEvent event) {
        onActionSearchPart(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        filterPart.removeAll();
        filterProduct.clear();
        setMenu();
    }

    public void setMenu(){

        if (textSearchPart.getText().isBlank())
            filterPart.setAll(Inventory.getAllParts());
        tableView.setItems(filterPart);
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        if (textSearchProduct.getText().isBlank())
            filterProduct.setAll(Inventory.getAllProducts());
        productTableView.setItems(filterProduct);
        colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
