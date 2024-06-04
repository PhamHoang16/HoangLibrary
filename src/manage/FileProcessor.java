package manage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import items.Book;
import items.CD;
import items.Item;

public class FileProcessor {
    static final String FILE_NAME = "Library.txt";


    public void readFile(List<Item> itemList, ItemReadCallback callback) {
        new Thread(() -> {
            List<Item> tempList = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("Library.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Item item = parseItemFromLine(line);
                    if (item != null) {
                        tempList.add(item);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading items from file: " + e.getMessage());
            }

            if (callback != null) {
                callback.onItemsRead(tempList); // Notify main thread with updated data
            }
        }).start();
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

    public void writeFile(Item item) {
        new Thread(() -> {
            try (FileWriter fileWriter = new FileWriter("Library.txt", true);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            writer.write(item.toString());
            writer.newLine();
            writer.newLine();

            System.out.println("Add item successfully!");
        } catch (IOException e) {
            System.err.println("Error saving item to file: " + e.getMessage());
        }
        }).start();
    }
}
