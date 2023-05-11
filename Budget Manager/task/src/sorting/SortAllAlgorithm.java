package sorting;

import budget.Category;
import budget.Purchase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SortAllAlgorithm implements SortingAlgorithm {

    List<Purchase> list;

    public SortAllAlgorithm(Map<Category, List<Purchase>> map) {
        this.list = new ArrayList<>();
        for (var m : map.entrySet()) {
            list.addAll(m.getValue());
        }
        list.sort(Comparator.reverseOrder());
    }

    @Override
    public void sort() {
        double total = 0;

        System.out.println("All:");
        for (Purchase purchase : list) {
            total += purchase.getUnitPrice();
            System.out.println(purchase);
        }
        System.out.printf("Total: $%.2f%n", total);
    }
}