package manage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import items.Book;
import items.CD;
import items.Item;

public class FileProcessor<T extends Item> {
    static final String FILE_NAME = "Library.txt";
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    public void loadItemsFromFile(List<T> itemList) {
        executor.submit(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
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

    public void saveItemToFile(Item item){
        executor.submit(() -> {
            try (FileWriter fileWriter = new FileWriter(FILE_NAME, true);
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

    public void updateFile(List<T> itemList) {
        executor.submit(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (T item : itemList) {
                    writer.write(item.toString());
                    writer.newLine();
                    writer.newLine();
                }
                System.out.println("File updated successfully!");
            } catch (IOException e) {
                System.err.println("Error updating file: " + e.getMessage());
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
