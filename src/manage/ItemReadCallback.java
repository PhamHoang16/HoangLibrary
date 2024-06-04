package manage;

import items.Item;

import java.util.List;

public interface ItemReadCallback {
    void onItemsRead(List<Item> items);
}
