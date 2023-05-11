package budget;

public class Purchase implements Comparable<Purchase> {

    private String unit;
    private double unitPrice;
    private Category category;

    public Purchase(Category category, String unit, double unitPrice) {
        this.category = category;
        this.unit = unit;
        this.unitPrice = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", this.unit, this.unitPrice);
    }

    @Override
    public int compareTo(Purchase purchase) {
        int comp = Double.compare(this.unitPrice, purchase.unitPrice);
        if (comp == 0) {
            return this.unit.compareTo(purchase.unit);
        }
        return comp;
    }
}