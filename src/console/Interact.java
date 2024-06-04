package console;

import manage.Library;

import java.util.Scanner;

public class Interact {
    static Library library = new Library();
    public static void welcome() throws InterruptedException {
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
        }
    }
    public static void interact() {
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                library.addItem();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                System.out.println("Please enter the name of the item you want to search");
                String keyword = scanner.next();
                library.searchItem(keyword);
                break;
            case 5:
                library.listAllItems();
                break;
            case 6:
                System.out.println("Please enter the id of the item you want to borrow");
                String id = scanner.next();
                library.borrowItem(id);
                break;
            case 7:
                break;

        }
    }
}
