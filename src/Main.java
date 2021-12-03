import controller.DatasetController;
import controller.InputContoller;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length==0){
            System.out.println("File path to Stock Inventory/Dataset not provided. Please enter one.");
            System.exit(0);
        }
        processStart(args);
    }
    private static void processStart(String[] args) throws IOException {
        DatasetController datasetController = new DatasetController(args[0]);
        datasetController.datasetCreation();
        startOrder(args[1]);
    }

    private static void startOrder(String path){
        InputContoller inputContoller = new InputContoller(path);
        if(inputContoller.startOrder()){
            if(inputContoller.checkOrder()){
                inputContoller.calculateTotal();
                if(inputContoller.getTotal()>0){
                    inputContoller.checkoutOrder();
                    System.out.println("The total amount of the order is $" + inputContoller.getTotal());
                }
            }else {
                System.out.println("Error log created. Please look into it");
                inputContoller.generateOutputFile();
            }
        }else {
            System.out.println("Order file not found");
        }
    }
}
