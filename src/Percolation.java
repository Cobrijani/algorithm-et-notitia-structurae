
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author SBratic
 *
 */
public class Percolation {

  private WeightedQuickUnionUF virtualUf;
  private WeightedQuickUnionUF uf;

  private boolean[] grid;

  private int numberOfElements;

  private final int virtualParentTop;
  private final int virtualParentBottom;

  /**
   * Required constructor
   * 
   * Performance: n^2+2+n^2 = 2*n^2 + 2 = O(n^2)
   * 
   * @param size
   *          n*n elements that grid consists of
   */
  public Percolation(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException();
    }
    this.numberOfElements = size;
    // n * n grid plus two virtual parents
    virtualUf = new WeightedQuickUnionUF(numberOfElements * numberOfElements + 2); // n^2+2
    uf = new WeightedQuickUnionUF(numberOfElements * numberOfElements + 1);

    grid = new boolean[numberOfElements * numberOfElements]; // mem: 2b*n^2

    for (int i = 0; i < numberOfElements; i++) { // n^2
      for (int j = 0; j < numberOfElements; j++) {
        grid[i * this.numberOfElements + j] = false;
      }
    }

    virtualParentTop = numberOfElements * numberOfElements;
    virtualParentBottom = numberOfElements * numberOfElements + 1;

  }

  /**
   * Opens a site if not open already
   * 
   * @param row
   *          row
   * @param col
   *          column
   */
  public void open(int row, int col) {
    validateIndexOutOfBounds(row, col);
    grid[getIndex(row, col)] = true; // O(1)

    /*
     * if its first row make connection to virtual parent from top (index 0) if
     */
    if (row == 1) {
      virtualUf.union(virtualParentTop, getIndex(row, col));
      uf.union(virtualParentTop, getIndex(row, col));
    }

    if (row == numberOfElements) {
      virtualUf.union(virtualParentBottom, getIndex(row, col));
    }
    unionOpenedNeighbour(row, col, virtualUf);
    unionOpenedNeighbour(row, col, uf);

  }

  private void unionOpenedNeighbour(int row, int col, WeightedQuickUnionUF union) {

    // 4x = O(1)
    if (col < numberOfElements && isOpen(row, col + 1)) {
      union.union(getIndex(row, col + 1), getIndex(row, col));
    }
    if (col > 1 && isOpen(row, col - 1)) {
      union.union(getIndex(row, col - 1), getIndex(row, col));
    }
    if (row < numberOfElements && isOpen(row + 1, col)) {
      union.union(getIndex(row + 1, col), getIndex(row, col));
    }
    if (row > 1 && isOpen(row - 1, col)) {
      union.union(getIndex(row - 1, col), getIndex(row, col));
    }

  }

  /**
   * Checks whether site is open
   * 
   * @param row
   *          row
   * @param col
   *          column
   * @return if is opened
   */
  public boolean isOpen(int row, int col) {
    validateIndexOutOfBounds(row, col);
    return grid[getIndex(row, col)]; // constant
  }

  /**
   * 
   * @param row
   *          row
   * @param col
   *          column
   * @return is full or not
   */
  public boolean isFull(int row, int col) {
    validateIndexOutOfBounds(row, col);
    return uf.connected(getIndex(row, col), virtualParentTop); // constant
  }

  /**
   * Checks if percolates
   * 
   * @return if percolates
   */
  public boolean percolates() {
    return virtualUf.connected(virtualParentTop, virtualParentBottom);
  }

  private void validateIndexOutOfBounds(int row, int col) {
    if (row < 1 || row > numberOfElements) {
      throw new IndexOutOfBoundsException();
    }
    if (col < 1 || col > numberOfElements) {
      throw new IndexOutOfBoundsException();
    }

  }

  /**
   * Retrieve index of matrix
   * 
   * @param row
   *          row
   * @param col
   *          column
   * @return index
   */
  private int getIndex(int row, int col) {
    validateIndexOutOfBounds(row, col);
    return (row - 1) * numberOfElements + (col - 1);
  }

  /**
   * @param args
   *          nothing
   */
  public static void main(String[] args) {

  }

}
