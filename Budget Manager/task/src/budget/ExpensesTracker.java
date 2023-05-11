package budget;

import sorting.SortAllAlgorithm;
import sorting.SortByTypeAlgorithm;
import sorting.SortCertainTypeAlgorithm;
import sorting.Sorter;

import java.io.*;
import java.util.*;

public class ExpensesTracker {

    private static final String FILENAME = "purchases.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private final Map<Category, List<Purchase>> map;
    private double total;
    private double balance;

    public ExpensesTracker() {
        this.map = new HashMap<>();
        this.total = 0;
        this.balance = 0;
    }

    private void addIncome() {
        System.out.println("Enter income:");
        while (true) {
            try {
                double income = scanner.nextDouble();
                balance += income;
                System.out.println("Income was added!");
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Enter only numbers");
            }
        }

    }

    private void showListOfPurchases(Category category) {
        if (category == Category.ALL) {
            System.out.println(category + ":");
            for (Map.Entry<Category, List<Purchase>> entry : map.entrySet()) {
                for (Purchase purchase : entry.getValue()) {
                    System.out.println(purchase);
                }
            }
            System.out.printf("Total: $%.2f%n", total);
        } else {
            List<Purchase> list = map.getOrDefault(category, Collections.emptyList());
            System.out.println(category + ":");
            if (list.isEmpty()) {
                System.out.println("The purchase list is empty!");
            } else {
                double categoryTotal = 0;
                for (Purchase purchase : list) {
                    categoryTotal += purchase.getUnitPrice();
                    System.out.println(purchase);
                }
                System.out.printf("Total: $%.2f%n", categoryTotal);
            }
        }

    }

    private void showListPurchasesMenu() {
        if (map.isEmpty()) {
            System.out.println("The purchase list is empty!");
            return;
        }

        while (true) {
            printListPurchasesMenu();
            String read = scanner.nextLine();
            if (!read.matches("^[1-6]$")) {
                System.out.println("Enter only numbers from 1 to 6");
                continue;
            }
            System.out.println();
            switch (Integer.parseInt(read)) {
                case 1:
                    showListOfPurchases(Category.FOOD);
                    break;
                case 2:
                    showListOfPurchases(Category.CLOTHES);
                    break;
                case 3:
                    showListOfPurchases(Category.ENTERTAINMENT);
                    break;
                case 4:
                    showListOfPurchases(Category.OTHER);
                    break;
                case 5:
                    showListOfPurchases(Category.ALL);
                    break;
                case 6:
                    return;
            }
            System.out.println();
        }
    }

    private void showBalance() {
        System.out.printf("Balance: $%.2f%n", balance);
    }

    private void printMainMenu() {
        System.out.println("Choose your action:" + System.lineSeparator()
                + "1) Add income" + System.lineSeparator()
                + "2) Add purchase" + System.lineSeparator()
                + "3) Show list of purchases" + System.lineSeparator()
                + "4) Balance" + System.lineSeparator()
                + "5) Save" + System.lineSeparator()
                + "6) Load" + System.lineSeparator()
                + "7) Analyze (Sort)" + System.lineSeparator()
                + "0) Exit");
    }

    private void printListPurchasesMenu() {
        System.out.println("Choose the type of purchases" + System.lineSeparator()
                + "1) Food" + System.lineSeparator()
                + "2) Clothes" + System.lineSeparator()
                + "3) Entertainment" + System.lineSeparator()
                + "4) Other" + System.lineSeparator()
                + "5) All" + System.lineSeparator()
                + "6) Back");
    }

    private void printPurchaseMenu() {
        System.out.println("Choose the type of purchase" + System.lineSeparator()
                + "1) Food" + System.lineSeparator()
                + "2) Clothes" + System.lineSeparator()
                + "3) Entertainment" + System.lineSeparator()
                + "4) Other" + System.lineSeparator()
                + "5) Back");
    }

    private void addPurchase(Category category) {
        System.out.println("Enter purchase name:");
        String unit = scanner.nextLine();
        while (true) {
            System.out.println("Enter its price:");
            String price = scanner.nextLine();
            if (!price.matches("^\\d+(\\.\\d+)?$")) {
                System.out.println("Enter only numbers");
                continue;
            }
            double priceUnit = Double.parseDouble(price);
            List<Purchase> list;
            if (map.containsKey(category)) {
                list = map.get(category);
                list.add(new Purchase(category, unit, priceUnit));
            } else {
                list = new ArrayList<>();
                list.add(new Purchase(category, unit, priceUnit));
                map.put(category, list);
            }
            total += priceUnit;
            balance -= priceUnit;
            System.out.println("Purchase was added!");
            break;
        }
        System.out.println();
    }

    private void showPurchaseMenu() {
        while (true) {
            printPurchaseMenu();
            String read = scanner.nextLine();
            if (!read.matches("^[1-5]$")) {
                System.out.println("Enter only numbers from 1 to 5");
                continue;
            }
            System.out.println();
            switch (Integer.parseInt(read)) {
                case 1:
                    addPurchase(Category.FOOD);
                    break;
                case 2:
                    addPurchase(Category.CLOTHES);
                    break;
                case 3:
                    addPurchase(Category.ENTERTAINMENT);
                    break;
                case 4:
                    addPurchase(Category.OTHER);
                    break;
                case 5:
                    return;
            }
        }
    }

    private void writeToFile(File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("Balance=" + balance + System.lineSeparator());
            for (Category category : Category.values()) {
                List<Purchase> list = map.getOrDefault(category, Collections.emptyList());
                fileWriter.write(category + " " + list.size() + System.lineSeparator());

                for (Purchase purchase : list) {
                    fileWriter.write(purchase.getUnit()
                            + "="
                            + purchase.getUnitPrice()
                            + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't write to: " + FILENAME);
        }
    }

    private void save() {
        try {
            File file = new File(FILENAME);
            if (file.exists()) {
                file.delete();
            }
            boolean isCreated = file.createNewFile();
            if (isCreated) {
                writeToFile(file);
                System.out.println("Purchases were saved!");
            }
        } catch (IOException e) {
            System.out.println("Error creating file");
        }
    }

    private void parseUnitFromArray(Category category, List<Purchase> list, String[] rec) {
        double unitPrice = Double.parseDouble(rec[1]);
        list.add(new Purchase(category, rec[0], unitPrice));
        total += unitPrice;
    }

    private void load() {
        try {
            File file = new File(FILENAME);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            map.clear();

            String line = bufferedReader.readLine();

            String[] s = line.split("=");
            balance = Double.parseDouble(s[1]);

            while ((line = bufferedReader.readLine()) != null) {
                s = line.split(" ");

                int count = Integer.parseInt(s[1]);
                if (count == 0) {
                    continue;
                }
                Category category = Category.valueOf(s[0].toUpperCase(Locale.ROOT));
                List<Purchase> list = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    String[] rec = bufferedReader.readLine().split("=");
                    parseUnitFromArray(category, list, rec);
                }
                map.put(category, list);
            }

            bufferedReader.close();
            fileReader.close();

            System.out.println("Purchases were loaded!");
        } catch (IOException e) {
            System.out.println("Couldn't open the file: " + FILENAME);
        } catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Not valid file content");
        }
    }

    private boolean sortMenuOptions(String read) {
        Sorter sorter;

        switch (Integer.parseInt(read)) {
            case 1:
                if (map.isEmpty()) {
                    System.out.println("The purchase list is empty!" + System.lineSeparator());
                    return true;
                }
                sorter = new Sorter(new SortAllAlgorithm(map));
                break;
            case 2:
                sorter = new Sorter(new SortByTypeAlgorithm(map));
                break;
            case 3:
                sorter = new Sorter(new SortCertainTypeAlgorithm(map, scanner));
                break;
            case 4:
                return false;
            default:
                sorter = new Sorter();
        }
        sorter.performSort();
        System.out.println();
        return true;
    }

    private void printSortMenu() {
        System.out.println("1) Sort all purchases" + System.lineSeparator()
                + "2) Sort by type" + System.lineSeparator()
                + "3) Sort certain type" + System.lineSeparator()
                + "4) Back");
    }

    private void sort() {
        boolean isRunning = true;

        while (isRunning) {
            printSortMenu();
            String read = scanner.nextLine();
            if (!read.matches("^[1-4]$")) {
                System.out.println("Enter only numbers from 1 to 4");
                continue;
            }
            System.out.println();
            isRunning = sortMenuOptions(read);
        }
    }

    private boolean mainMenuOptions(String input) {
        switch (Integer.parseInt(input)) {
            case 1:
                addIncome();
                break;
            case 2:
                showPurchaseMenu();
                return true;
            case 3:
                showListPurchasesMenu();
                return true;
            case 4:
                showBalance();
                break;
            case 5:
                save();
                break;
            case 6:
                load();
                break;
            case 7:
                sort();
                break;
            case 0:
                System.out.println("Bye!");
                return false;
        }
        System.out.println();
        return true;
    }

    public void showMainMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {
                printMainMenu();
                String read = scanner.nextLine();
                if (!read.matches("^[0-7]$")) {
                    System.out.println("Enter only numbers");
                    continue;
                }
                System.out.println();
                isRunning = mainMenuOptions(read);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
