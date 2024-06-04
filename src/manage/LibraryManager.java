package manage;

import exception.ItemAlreadyBorrowedException;
import exception.ItemIDAreadyExistsException;
import exception.ItemNotAvailableException;
import exception.ItemNotFoundException;
import items.Book;
import items.CD;
import items.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryManager<T extends Item> implements Library {
    private List<T> itemList = new ArrayList<T>();
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public LibraryManager() {
        loadItemsFromFile();
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
                saveItemToFile(item);
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


    public void saveItemToFile(Item item){
        executor.submit(() -> {
            try (FileWriter fileWriter = new FileWriter("Library.txt", true);
                 BufferedWriter writer = new BufferedWriter(fileWriter)) {

                writer.write(item.toString());
                writer.newLine();
                writer.newLine();

                System.out.println("Add item successfully!");
            } catch (IOException e) {
                System.err.println("Error saving item to file: " + e.getMessage());
            }
        });
    }

    public void removeItem(String id) throws ItemNotFoundException {
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
                updateFile();
            } else {
                throw new ItemNotFoundException("Item not found");
            }
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());

        }
    }
    public void updateFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Library.txt"))) {
            for (T item : itemList) {
                writer.write(item.toString());
                writer.newLine();
                writer.newLine();
            }
            System.out.println("File updated successfully!");
        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
        }
    }

    public void updateItem(String id) throws ItemNotFoundException {
        boolean found = false;
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getId().equals(id)) {
                Item newItem = enterItem();
                itemList.set(i, (T) newItem);
                found = true;
                break;
            }
        }
        if (found) {
            updateFile();
        } else {
            throw new ItemNotFoundException("Item not found");
        }
    }

    public void searchItem(String keywords) throws ItemNotFoundException {
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
            System.out.println(e.getMessage());
        }
    }

    public void borrowItem(String id) throws Exception {
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
            System.out.println(e.getMessage());
        }
    }

    public void listAllItems() {
        System.out.println("Threre are " + itemList.size() + " items in library");
        for (T item : itemList) {
            System.out.println(item.toString());
        }
    }

    public void returnItem(String id) throws Exception {
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
            System.out.println(e.getMessage());
        }
    }

    public void loadItemsFromFile() {
        executor.submit(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader("Library.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Item item = parseItemFromLine(line);
                    if (item != null) {
                        itemList.add((T) item);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading items from file: " + e.getMessage());
            }
        });
    }

    public Item parseItemFromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 6) {
            return null;
        }
        if (line.contains("author")) {
            Book book = new Book();
            for (String itemInfo : parts) {
                String[] keyValue = itemInfo.split(": ");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "id":
                        book.setId(String.valueOf(Integer.parseInt(value)));
                        break;
                    case "title":
                        book.setTitle(value);
                        break;
                    case "publisher":
                        book.setPublisher(value);
                        break;
                    case "year":
                        book.setYear(Integer.parseInt(value));
                        break;
                    case "author":
                        book.setAuthor(value);
                        break;
                    case "isBorrowed":
                        book.setStatus(Boolean.parseBoolean(value));
                        break;
                    default:
                        break;
                }
            }
            return book;
        }
        else if (line.contains("artist")) {
            CD cd = new CD();
            for (String itemInfo : parts) {
                String[] keyValue = itemInfo.split(": ");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "id":
                        cd.setId(String.valueOf(Integer.parseInt(value)));
                        break;
                    case "title":
                        cd.setTitle(value);
                        break;
                    case "publisher":
                        cd.setPublisher(value);
                        break;
                    case "year":
                        cd.setYear(Integer.parseInt(value));
                        break;
                    case "artist":
                        cd.setArtist(value);
                        break;
                    case "isBorrowed":
                        cd.setStatus(Boolean.parseBoolean(value));
                        break;
                    default:
                        break;
                }
            }
            return cd;
        }
        return null;
    }
    public void shutdownExecutor() {
        executor.shutdownNow();
    }
}
