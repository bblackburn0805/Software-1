/*

        Brandon Blackburn
        03/27/20
        C482 - Software 1

 */



package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Manager");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {

        InHousePart part1= new InHousePart(20, "banana", 3.50, 69, 0, 69);
        part1.setMachineID(51);
        InHousePart part2= new InHousePart(29, "apples", 4.50, 25, 2, 17);
        part2.setMachineID(42);
        OutsourcedPart part3 = new OutsourcedPart(30, "pear", 5.50, 40, 2, 6);
        part3.setCompanyName("Brandon");

        Product pro1 = new Product(1234, "Bananana", 2.33, 54, 12, 55);
        pro1.addAssociatedPart(part1);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        Inventory.addProduct(pro1);
        launch(args);
    }
}
