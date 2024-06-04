package items;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Item implements Comparable<Item>{
    private String id;
    private String title;
    private String publisher;
    private int year;
    private boolean isBorrowed = false;

    public Item(String id, String title, String publisher, int year) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.year = year;
    }

    public Item() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean getStatus() {
        return isBorrowed;
    }

    public void setStatus(boolean status) {
        this.isBorrowed = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(Item otherItem) {
        if (this.id == otherItem.getId()) return 0;
        return 1;
    }

    public void enterDetail() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id");
        String id = scanner.next();
        scanner.nextLine();
        setId(id);

        System.out.println("Enter title");
        String title = scanner.nextLine();
        setTitle(title);
        System.out.println("Enter publisher");
        String publisher = scanner.nextLine();
        setPublisher(publisher);
        System.out.println("Enter book's year");
        int year = 0;
        try {
            year = scanner.nextInt();
            setYear(year);
        } catch (InputMismatchException e) {
            int attempts = 3;

            while (attempts > 0) {
                System.err.println("Invalid input for year. Please enter a valid integer. You have " + attempts + " attempts left.");
                scanner.next();
                System.out.println("Enter year: ");

                try {
                    year = scanner.nextInt();
                    setYear(year);
                    break;
                } catch (InputMismatchException ex) {
                    attempts--;
                }
            }
            if (attempts == 0) {
                System.err.println("Error: Maximum attempts exceeded. Exiting.");
                return;
            }
        }
    }
}
