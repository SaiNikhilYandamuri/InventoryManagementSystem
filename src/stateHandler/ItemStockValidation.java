package stateHandler;

import Database.Database;
import controller.InputContoller;
import model.OrderItem;

import java.util.ArrayList;

public class ItemStockValidation implements ValidationHandler{
    private ValidationHandler next= null;
    @Override
    public boolean validate(ArrayList<OrderItem> items) {
        Database database = Database.getInstance();
        for(OrderItem orderItem: items){
            if(database.getItemsMap().get(orderItem.getName()).getQuantity()<orderItem.getQuantity()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void nextHandler(ValidationHandler next) {
        this.next = next ;
    }
}
