package sorting;

import budget.Category;
import budget.Purchase;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SortCertainTypeAlgorithm implements SortingAlgorithm {

    private final Map<Category, List<Purchase>> map;
    private final Scanner scanner;

    public SortCertainTypeAlgorithm(Map<Category, List<Purchase>> map, Scanner scanner) {
        this.map = map;
        this.scanner = scanner;
    }

    private void menuOptions(String read) {
        switch (Integer.parseInt(read)) {
            case 1 -> performSort(Category.FOOD);
            case 2 -> performSort(Category.CLOTHES);
            case 3 -> performSort(Category.ENTERTAINMENT);
            case 4 -> performSort(Category.OTHER);
        }
        System.out.println();
    }

    private void performSort(Category category) {
        List<Purchase> list = map.getOrDefault(category, Collections.emptyList());
        list.sort(Comparator.reverseOrder());

        if (list.isEmpty()) {
            System.out.println("The purchase list is empty!");
            return;
        }

        double sum = 0;
        System.out.println(category + ":");
        for (Purchase purchase : list) {
            sum += purchase.getUnitPrice();
            System.out.println(purchase);
        }
        System.out.printf("Total sum: $%.2f%n", sum);
    }

    private void printMenu() {
        System.out.println("Choose the type of purchase" + System.lineSeparator()
                + "1) Food" + System.lineSeparator()
                + "2) Clothes" + System.lineSeparator()
                + "3) Entertainment" + System.lineSeparator()
                + "4) Other");
    }

    @Override
    public void sort() {
        boolean isRunning = true;

        while (isRunning) {
            printMenu();
            String read = scanner.nextLine();
            if (!read.matches("^[1-4]$")) {
                System.out.println("Enter only numbers");
                continue;
            }
            System.out.println();
            menuOptions(read);
            isRunning = false;
        }
    }
}