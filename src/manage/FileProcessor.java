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
    private static final String BOOK_FILE_NAME = "book.txt";
    private static final String CD_FILE_NAME = "cd.txt";

    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    public void loadItemsFromFile(List<Item> itemList) {
        executor.submit(() -> {
            synchronized (itemList) {
                try (BufferedReader bookReader = new BufferedReader(new FileReader(BOOK_FILE_NAME));
                     BufferedReader cdReader = new BufferedReader(new FileReader(CD_FILE_NAME))) {

                    String line;
                    // Đọc dữ liệu từ file book.txt
                    while ((line = bookReader.readLine()) != null) {
                        Item item = parseItemFromLine(line);
                        if (item != null) {
                            itemList.add(item);
                        }
                    }

                    // Đọc dữ liệu từ file cd.txt
                    while ((line = cdReader.readLine()) != null) {
                        Item item = parseItemFromLine(line);
                        if (item != null) {
                            itemList.add(item);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error loading items from file: " + e.getMessage());
                }
            }
        });
    }

    public void saveItemToFile(Item item) {
        executor.submit(() -> {
            String fileName = item instanceof Book ? BOOK_FILE_NAME : CD_FILE_NAME;
            try (FileWriter fileWriter = new FileWriter(fileName, true);
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

    public void updateFile(List<Item> itemList) {
        executor.submit(() -> {
            synchronized (itemList) {
                try (BufferedWriter bookWriter = new BufferedWriter(new FileWriter(BOOK_FILE_NAME));
                     BufferedWriter cdWriter = new BufferedWriter(new FileWriter(CD_FILE_NAME))) {

                    for (Item item : itemList) {
                        if (item instanceof Book) {
                            bookWriter.write(item.toString());
                            bookWriter.newLine();
                            bookWriter.newLine();
                        } else if (item instanceof CD) {
                            cdWriter.write(item.toString());
                            cdWriter.newLine();
                            cdWriter.newLine();
                        }
                    }

                    System.out.println("Files updated successfully!");
                } catch (IOException e) {
                    System.err.println("Error updating files: " + e.getMessage());
                }
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
