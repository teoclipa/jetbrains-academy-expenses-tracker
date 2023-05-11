package sorting;

public class Sorter {

    SortingAlgorithm sortingAlgorithm;

    public Sorter(SortingAlgorithm sortingAlgorithm) {
        this.sortingAlgorithm = sortingAlgorithm;
    }

    public Sorter() {
        this.sortingAlgorithm = null;
    }

    public void performSort() {
        if (sortingAlgorithm != null) {
            sortingAlgorithm.sort();
        }
    }
}