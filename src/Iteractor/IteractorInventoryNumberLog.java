package Iteractor;

import Model.Inventory_number.InventoryNumberLog;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class IteractorInventoryNumberLog extends GenericIteractor<InventoryNumberLog> {

    private static String sURI="/inventory_log_servlet";

    public IteractorInventoryNumberLog() {
        super(sURI, InventoryNumberLog.class, new TypeToken<ArrayList<InventoryNumberLog>>(){}.getType());
    }

}
