/*

        Brandon Blackburn
        03/27/20
        C482 - Software 1

 */

package Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController{
    Stage stage;
    Parent scene;

    @FXML
    private Label labelInOut;

    @FXML
    private TextField textID;

    @FXML
    private TextField textName;

    @FXML
    private TextField textInventory;

    @FXML
    private TextField textPriceCost;

    @FXML
    private TextField textMax;

    @FXML
    private TextField textCompMach;

    @FXML
    private TextField textMin;

    @FXML
    private RadioButton radioIn;

    @FXML
    private ToggleGroup toggleInOut;

    @FXML
    private RadioButton radioOut;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonCancel;

    @FXML
    void onActionIn(ActionEvent event) {
        labelInOut.setText("Machine ID");
    }

    @FXML
    void onActionOut(ActionEvent event) {
        labelInOut.setText("Company");
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {           //TODO Unique ID check, confirm empty fields, Min < inv < Max
            try {

                int id = Integer.parseInt(textID.getText());
                String name = textName.getText();
                double price = Double.parseDouble(textPriceCost.getText());
                int stock = Integer.parseInt(textInventory.getText());
                int min = Integer.parseInt(textMin.getText());
                int max = Integer.parseInt(textMax.getText());
                int machineId;
                String company;
                    if (min < max && max > min) {                                                       //SET 1: preventing the minimum field from having a value above the maximum field
                        if (radioIn.isSelected()) {                                                     //SET 1: preventing the maximum field from having a value below the minimum field
                            machineId = Integer.parseInt(textCompMach.getText());
                            InHousePart newIn = new InHousePart(id, name, price, stock, min, max);
                            newIn.setMachineID(machineId);
                            Inventory.addPart(newIn);
                        } else {
                            company = textCompMach.getText();
                            OutsourcedPart newOut = new OutsourcedPart(id, name, price, stock, min, max);
                            newOut.setCompanyName(company);
                            Inventory.addPart(newOut);
                        }

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Entry");
                        alert.setContentText("Please ensure Max and Min are proper values.");
                        alert.showAndWait();
                    }
                } catch(NumberFormatException error){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Entry");
                    alert.setContentText("Please enter valid values");
                    alert.showAndWait();

            }
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
