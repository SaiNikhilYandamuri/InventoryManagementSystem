package stateHandler;

import Database.Database;
import model.OrderItem;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemPresenceValidation implements ValidationHandler{
    private ValidationHandler next = null;
    @Override
    public boolean validate(ArrayList<OrderItem> items) {
        Database database = Database.getInstance();
        for(OrderItem orderItem: items){
            if(!database.getItemsMap().containsKey(orderItem.getName()))
                return false;
        }
        return true;
    }
}
