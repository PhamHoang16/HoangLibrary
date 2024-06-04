import console.Interact;
import items.Book;
import items.Item;
import manage.FileProcessor;
import manage.ItemReadCallback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
//        FileProcessor fileProcessor = new FileProcessor();
//        List<Item> itemList = new ArrayList<Item>();
//
//        fileProcessor.readFile(itemList, new ItemReadCallback() {
//            @Override
//            public void onItemsRead(List<Item> items) {
//                synchronized (itemList) {
//                    itemList.clear();
//                    itemList.addAll(items);
//                    System.out.println("Items updated in main thread: " + itemList);
//                }
//            }
//        });
        Interact.welcome();
    }
}
