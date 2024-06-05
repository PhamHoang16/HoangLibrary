package interact;

import items.Item;
import manage.LibraryManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Interact {
    static LibraryManager libraryManager = new LibraryManager();
    public static void welcome() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("        Welcome to Hoang Library");
        System.out.println("--------------------------------");
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Add a item");
        System.out.println("2. Remove a item");
        System.out.println("3. Update a item");
        System.out.println("4. Search a item");
        System.out.println("5. List all items");
        System.out.println("6. Borrow a item");
        System.out.println("7. Return a item");
        interact();
        System.out.println("--------------------------------");
        System.out.println("Do you want to continue?");
        System.out.println("Type yes to continue - Type any key to exit");
        if (scanner.nextLine().equals("y") || scanner.nextLine().equals("yes")) {
            welcome();
        } else {
            System.out.println("Thank you for using Hoang Library");
            libraryManager.shutdownExecutor();
        }
    }

    public static void interact() throws Exception {
        int activeCount = Thread.activeCount();
        System.out.println("Number of active threads: " + activeCount);
        Scanner scanner = new Scanner(System.in);
        int option = 5;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter option with interger value");
        }
        switch (option) {
            case 1:
                Item item = libraryManager.enterItem();
                libraryManager.addItem(item);
                break;
            case 2:
                System.out.println("Please enter the item id you want to remove");
                String id = scanner.next();
                libraryManager.removeItem(id);
                break;
            case 3:
                System.out.println("Please enter the item details you want to update");
                String id2 = scanner.next();
                libraryManager.updateItem(id2);
                break;
            case 4:
                System.out.println("Please enter the name of the item you want to search");
                String keyword = scanner.next();
                libraryManager.searchItem(keyword);
                break;
            case 5:
                libraryManager.listAllItems();
                break;
            case 6:
                System.out.println("Please enter the id of the item you want to borrow");
                String id1 = scanner.next();
                libraryManager.borrowItem(id1);
                break;
            case 7:
                System.out.println("Please enter the id of the item you want to return");
                String id3 = scanner.next();
                libraryManager.returnItem(id3);
                break;
        }
    }
}
