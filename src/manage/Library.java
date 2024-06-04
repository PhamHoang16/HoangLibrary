package manage;

import items.Book;
import items.CD;
import items.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library<T extends Item> {
    private List<T> itemList = new ArrayList<T>();

    public Library() {
        loadItemsFromFile();
    }

    public void addItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add your new item");
        System.out.println("Select type of item");
        System.out.println("1. Book");
        System.out.println("2. CD");
        int option = scanner.nextInt();
        if (option == 1) {
            System.out.println("Enter book's id");
            String id = scanner.next();
            scanner.nextLine();

            System.out.println("Enter book's title");
            String title = scanner.nextLine();

            System.out.println("Enter book's publisher");
            String publisher = scanner.nextLine();

            System.out.println("Enter book's author");
            String author = scanner.nextLine();

            System.out.println("Enter book's year");
            int year = scanner.nextInt();
            if (checkID(id)) {
                System.out.println("ID already exists");
            } else {
                Item newItem = new Book(id, title, publisher, year, author);
                itemList.add((T) newItem);
                saveItemToFile((T) newItem);
            }
        } else if (option == 2) {
            System.out.println("Enter CD's id");
            String id = scanner.next();
            scanner.nextLine();

            System.out.println("Enter CD's title");
            String title = scanner.nextLine();

            System.out.println("Enter CD's publisher");
            String publisher = scanner.nextLine();

            System.out.println("Enter CD's artist");
            String artist = scanner.nextLine();

            System.out.println("Enter CD's year");
            int year = scanner.nextInt();
            if (checkID(id)) {
                System.out.println("ID already exists");
            } else {
                Item newItem = new CD(id, title, publisher, year, artist);
                itemList.add((T) newItem);
                saveItemToFile((T) newItem);
            }

        }
    }

    private boolean checkID(String id) {
        for (T item : itemList) {
            if (item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private void saveItemToFile(T item){
        try (FileWriter fileWriter = new FileWriter("Library.txt", true);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            writer.write(item.toString());
            writer.newLine();
            writer.newLine();

            System.out.println("Add item successfully!");
        } catch (IOException e) {
            System.err.println("Error saving item to file: " + e.getMessage());
        }
    }

    public void removeItem(String id) {
        for (T item : itemList) {
            if (item.getId().equals(id)) {
                itemList.remove(item);
                return;
            } else {
                System.out.println("Item not found: " + id);
            }
        }
    }

    public void updateItem(String id, T newItem) {
        for (T item : itemList) {
            if (item.compareTo(newItem) == 0) {
                itemList.remove(item);
                itemList.add(newItem);
                return;
            } else {
                System.out.println("Item not found: " + id);
            }
        }
    }

    public void searchItem(String keywords) {
        int countItem = 0;
        for (T item : itemList) {
            if (item.getTitle().contains(keywords) || item.getPublisher().contains(keywords)) {
                System.out.println(item.toString());
                countItem++;
            }
        }
        if (countItem != 0) {
            System.out.println("Found total" + countItem + " items");
        }
        else System.out.println("Item not found: " + keywords);
    }

    public void borrowItem(String id) {
        for (T item : itemList) {
            if (item.getId().equals(id)) {
                if (item.getStatus()) System.out.println("Item is being borrowed");
                else {
                    item.setStatus(true);
                    System.out.println("Borrow successfully");
                }
            } else {
                System.out.println("Item not found: " + id);
            }
        }
    }

    public void listAllItems() {
        System.out.println("Threre are " + itemList.size() + " items in library");
        for (T item : itemList) {
            System.out.println(item.toString());
        }
    }

    public void returnItem(String id) {
        for (T item : itemList) {
            if (item.getId().equals(id)) {
                if (item.getStatus()) {
                    item.setStatus(false);
                    System.out.println("Return successfully");
                } else System.out.println("Item is not borrowed");
            } else {
                System.out.println("Item not found: " + id);
            }
        }
    }

    private void loadItemsFromFile() {
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
    }

    private Item parseItemFromLine(String line) {
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
}
