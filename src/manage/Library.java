package manage;
import items.Item;

public interface Library<T extends Item> {
    T enterItem();
    void addItem(T item);
    boolean checkID(String id);
    void removeItem(String id);
    void updateItem(String id);
    void searchItem(String keywords);
    void borrowItem(String id);
    void listAllItems();
    void returnItem(String id);
}
