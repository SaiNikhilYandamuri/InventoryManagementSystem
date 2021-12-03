package controller;

import Database.Database;
import helper.FileHelper;
import model.Items;
import model.Order;
import model.OrderItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class InputContoller {
    private Database database = Database.getInstance();
    private Order currentOrder = new Order();
    private FileHelper fileHelper;
    private ArrayList<String> output = new ArrayList<>();
    private ArrayList<OrderItem> items = new ArrayList<>();
    private HashSet<String> creditCards = new HashSet<>();
    private double total = 0;

    public InputContoller(String filePath){
        fileHelper = new FileHelper(filePath);
    }

    public boolean startOrder() {
        try{
            fileHelper.fileReader(true);
        }catch (Exception e){
            return false;
        }
        getItems(fileHelper.getContentFile());
        return true;
    }
    public boolean checkOrder() {
        checkItemStock();
        return output.isEmpty();
    }

    public void calculateTotal() {
        for(OrderItem item: items){
            total += item.getQuantity()*database.getItemsMap().get(item.getName()).getPrice();
        }
        currentOrder.setTotalPrice(total);
    }

    public double getTotal() {
    return currentOrder.getTotalPrice();
    }

    public void checkoutOrder() {
        database.getOrdersList().add(currentOrder);
        for(OrderItem orderItem: items){
            Items item = database.getItemsMap().get(orderItem.getName());
            item.setQuantity(item.getQuantity()-orderItem.getQuantity());
        }
        for(String credit:creditCards){
            if(!database.getCardsSet().contains(credit)){
                database.getCardsSet().add(credit);
            }
        }
        generateOutputFile();
    }

    public void printMessage() {
        for(String line: output){
            System.out.println(line);
        }
    }

    public void getItems(ArrayList<String> fileContent){
        for(String line: fileContent){
            String[] item = line.split(",");
            if(database.getItemsMap().containsKey(item[0])){
                items.add(new OrderItem(item[0],Integer.parseInt(item[1]),item[2].replaceAll("\\s+","")));
            }else{
                output.add("Item "+item[0]+" not found");
            }
        }
        if(!output.isEmpty()){
            items.clear();
        }
    }

    public boolean checkItemStock() {
        StringBuilder sb = new StringBuilder();
        for(OrderItem orderItem: items){
            Items item = database.getItemsMap().get(orderItem.getName());
            if(item.getQuantity()<orderItem.getQuantity()){
                if(sb.length()>0)
                    sb.append(",");
                sb.append(orderItem.getName()+"("+item.getQuantity()+")");
            }else{
                if(!creditCards.contains(orderItem.getCardDetails()))
                    creditCards.add(orderItem.getCardDetails());
            }
        }
        if(sb.length()>0){
            output.add("Please correct quantities");
            output.add(sb.toString());
        }
        return sb.length()==0;
    }

    public void generateOutputFile(){
        System.out.println("Zing Zing Amazing");
        if(output.isEmpty()){
            output.add("Amout Paid");
            output.add(Double.toString((currentOrder.getTotalPrice())));
            try{
                fileHelper.writeOuput(output,false);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                fileHelper.writeOuput(output,true);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
