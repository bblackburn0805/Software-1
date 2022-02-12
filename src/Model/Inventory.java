/*

        Brandon Blackburn
        03/27/20
        C482 - Software 1

 */

package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> Part = FXCollections.observableArrayList();
    private static ObservableList<Product> Product = FXCollections.observableArrayList();

    public static void addPart(Part newPart){Part.add(newPart);}
    public static void addProduct(Product newProduct){Product.add(newProduct);}
    public static Part lookupPart(int partId){
        for(Part part : Part){
            if (part.getId() == partId)
                return part;
        }
        return null;
    }
    public static Product lookupProduct(int productID){
        for(Product product : Product){
            if (product.getId() == productID)
                return product;
        }
        return null;

    }
    public static ObservableList<Part> lookupPart (String partName){
        ObservableList<Part> query = FXCollections.observableArrayList();
        for ( Part part : Part)
        {
            if (part.getName().contains(partName))
                Part.add(part);
        }
        return query;
    }
    public static ObservableList<Product> lookupProduct (String productName){
        ObservableList<Product> query = FXCollections.observableArrayList();
        for ( Product product : Product)
        {
            if (product.getName().contains(productName))
                Product.add(product);
        }
        return query;
    }
    public static void updatePart(int index, Part selectedPart){Part.set(index, selectedPart);}
    public static void updateProduct(int index, Product selectedProduct){Product.set(index, selectedProduct);}
    public static boolean deletePart(Part selectedPart) {
        for (Part part : Part) {
            if (part == selectedPart)
                return Part.remove(part);
        }
        return false;
    }
    public static boolean deleteProduct(Product selectedProduct){
        for (Product pro : Product) {
            if (pro == selectedProduct)
                return Product.remove(pro);
    }
        return false;
    }
    public static ObservableList<Part> getAllParts(){return Part;}
    public static ObservableList<Product> getAllProducts(){return Product;}


}
