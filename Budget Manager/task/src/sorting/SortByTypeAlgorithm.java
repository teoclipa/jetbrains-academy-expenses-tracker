package sorting;

import budget.Category;
import budget.Purchase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SortByTypeAlgorithm implements SortingAlgorithm {

    Map<Category, List<Purchase>> map;

    public SortByTypeAlgorithm(
            Map<Category, List<Purchase>> map) {
        this.map = map;
    }

    static class TypeOfPurchase implements Comparable<TypeOfPurchase> {

        String type;
        double sum;

        public TypeOfPurchase(String type, double sum) {
            this.type = type;
            this.sum = sum;
        }

        public String getType() {
            return type;
        }

        public double getSum() {
            return sum;
        }

        @Override
        public String toString() {
            return String.format("%s - $%.2f", type, sum);
        }

        @Override
        public int compareTo(TypeOfPurchase typeOfPurchase) {
            return Double.compare(sum, typeOfPurchase.sum);
        }
    }

    @Override
    public void sort() {
        List<TypeOfPurchase> list = new ArrayList<>();

        double totalSum = 0;

        for (Category category : Category.values()) {
            if (category == Category.ALL) {
                continue;
            }
            List<Purchase> purchases = map.getOrDefault(category, Collections.emptyList());
            double sum = 0;
            for (Purchase purchase : purchases) {
                sum += purchase.getUnitPrice();
            }
            totalSum += sum;
            list.add(new TypeOfPurchase(category.toString(), sum));
        }
        list.sort(Comparator.reverseOrder());

        System.out.println("Types:");
        for (TypeOfPurchase typeOfPurchase : list) {
            System.out.println(typeOfPurchase);
        }
        System.out.println("Total sum: " + totalSum);
    }
}