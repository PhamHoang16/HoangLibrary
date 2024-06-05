package manage;

import exception.ItemAlreadyBorrowedException;
import exception.ItemIDAreadyExistsException;
import exception.ItemNotAvailableException;
import exception.ItemNotFoundException;
import items.Book;
import items.CD;
import items.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManager<T extends Item> implements Library {
    private List<T> itemList = new ArrayList<T>();
    FileProcessor fileProcessor = new FileProcessor();
    public LibraryManager() {
        fileProcessor.loadItemsFromFile(itemList);
    }
    public T enterItem() {
        int activeCount = Thread.activeCount();
        System.out.println("Number of active threads: " + activeCount);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add your new item");
        System.out.println("Select type of item");
        System.out.println("1. Book");
        System.out.println("2. CD");
        int option = scanner.nextInt();
        if (option == 1) {
            Book book = new Book();
            book.enterDetail();
            return (T) book;
        }
        CD cd = new CD();
        cd.enterDetail();
        return (T) cd;
    }

    @Override
    public void addItem(Item item) {
        try {
            if (checkID(item.getId())) {
                throw new ItemIDAreadyExistsException("ID already exists");
            } else {
                itemList.add((T) item);
                fileProcessor.saveItemToFile(item);
            }
        } catch (ItemIDAreadyExistsException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean checkID(String id) {
        for (T item : itemList) {
            if (item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void removeItem(String id) {
        boolean found = false;
        T itemToRemove = null;
        try {
            for (T item : itemList) {
                if (item.getId().equals(id)) {
                    itemToRemove = item;
                    found = true;
                    break;
                }
            }
            if (found) {
                itemList.remove(itemToRemove);
                fileProcessor.updateFile(itemList);
            } else {
                throw new ItemNotFoundException("Item not found");
            }
        } catch (ItemNotFoundException e) {
            System.err.println(e.getMessage());

        }
    }

    public void updateItem(String id) {
        boolean found = false;
        try {
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).getId().equals(id)) {
                    Item newItem = enterItem();
                    itemList.set(i, (T) newItem);
                    found = true;
                    break;
                }
            }
            if (found) {
                fileProcessor.updateFile(itemList);
            } else {
                throw new ItemNotFoundException("Item not found");
            }
        } catch (ItemNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }



    public void searchItem(String keywords) {
        int countItem = 0;
        try {
            for (T item : itemList) {
                if (item.getTitle().contains(keywords) || item.getPublisher().contains(keywords)) {
                    System.out.println(item.toString());
                    countItem++;
                }
            }
            if (countItem != 0) {
                System.out.println("Found total" + countItem + " items");
            }
            else throw new ItemNotFoundException("No items found");
        } catch (ItemNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void borrowItem(String id) {
        boolean found = false;
        try {
            for (T item : itemList) {
                if (item.getId().equals(id)) {
                    found = true;
                    if (item.getStatus()) throw new ItemAlreadyBorrowedException("Item already borrowed");
                    else {
                        item.setStatus(true);
                        System.out.println("Borrow successfully");
                    }
                }
            }
            if (!found) {
                throw new ItemNotFoundException("Item not found");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void listAllItems() {
        System.out.println("Threre are " + itemList.size() + " items in library");
        for (T item : itemList) {
            System.out.println(item.toString());
        }
    }

    public void returnItem(String id) {
        boolean found = false;
        try {
            for (T item : itemList) {
                if (item.getId().equals(id)) {
                    found = true;
                    if (item.getStatus()) {
                        item.setStatus(false);
                        System.out.println("Return successfully");
                    } else throw new ItemNotAvailableException("Item is not available for return");
                }
            }
            if (!found) throw new ItemNotFoundException("Item not found");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void shutdownExecutor() {
        fileProcessor.shutdownExecutor();
    }
}
