package manage;

import exception.ItemIDAreadyExistsException;
import exception.ItemNotFoundException;
import items.Item;

public interface Library<T extends Item> {
    T enterItem();
    void addItem(T item) throws ItemIDAreadyExistsException;
    boolean checkID(String id);
    void saveItemToFile(T item);
    void removeItem(String id) throws ItemNotFoundException;
    void updateFile();
    void updateItem(String id) throws ItemNotFoundException;
    void searchItem(String keywords) throws ItemNotFoundException;
    void borrowItem(String id) throws Exception;
    void listAllItems();
    void returnItem(String id) throws Exception;
    void loadItemsFromFile();
    Item parseItemFromLine(String line);

}
