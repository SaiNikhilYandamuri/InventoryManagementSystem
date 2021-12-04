package stateHandler;

import Database.Database;
import model.OrderItem;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemCategoryCapValidation implements ValidationHandler{
    private final int luxCap = 3;
    private final int essCap = 5;
    private final int miscCap = 6;
    @Override
    public boolean validate(ArrayList<OrderItem> items) {
        HashMap<String,Integer> map = new HashMap<>();
        Database database = Database.getInstance();
        for(OrderItem orderItem: items){
            map.put(database.getItemsMap().get(orderItem.getName()).getCategory(),map.getOrDefault(database.getItemsMap().get(orderItem.getName()).getCategory(),0)+orderItem.getQuantity());
        }

        if(map.getOrDefault("Luxury",0)>luxCap)
            return false;
        else if(map.getOrDefault("Essential",0)>essCap)
            return false;
        else if(map.getOrDefault("Misc",0)>miscCap)
            return false;
//        System.out.println(map.getOrDefault("Luxury",0));
//        System.out.println(map.getOrDefault("Essential",0));
//        System.out.println(map.getOrDefault("Misc",0));
        return true;
    }
}
