import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Class that simulates percolation threshold
 * 
 * @author SBratic
 *
 */
public class PercolationStats {

  private double[] threshold;

  /**
   * Constructor that runs simulation on n*n grid t number of times
   * 
   * @param size
   *          grid size
   * @param trials
   *          number of trials
   */
  public PercolationStats(int size, int trials) {
    if (size <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }

    threshold = new double[trials];

    for (int i = 0; i < trials; i++) { //t
      Percolation percolation = new Percolation(size);
      
      int count = 0;
      while (!percolation.percolates()) {
        count++;
        int row;
        int col;
        do {
          row = StdRandom.uniform(1, size + 1);
          col = StdRandom.uniform(1, size + 1);
        } while (percolation.isOpen(row, col));

        percolation.open(row, col);
      }

      threshold[i] = count / (double) (size * size);
    }
    
    

  }

  /**
   * Average threshold value
   * 
   * @return average value
   */
  public double mean() {
    return StdStats.mean(threshold);
  }

  /**
   * Standard deviation of threshold
   * 
   * @return standard deviation
   */
  public double stddev() {
    if (threshold.length == 1) {
      return Double.NaN;
    }
    return StdStats.stddev(threshold);
  }

  /**
   * Left side of confidence interval
   * 
   * @return confidence low interval
   */
  public double confidenceLo() {
    return this.mean() - this.stddev() * 1.96 / Math.sqrt(this.threshold.length);
  }

  /**
   * Right side of confidence interval
   * 
   * @return value
   */
  public double confidenceHi() {
    return this.mean() + this.stddev() * 1.96 / Math.sqrt(this.threshold.length);

  }

  /**
   * Main method for percolation stats
   * 
   * @param args
   *          size and trials
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException(
          "Must have two arguments first number of items (N) and second number of trials (T)");
    }

    int size;
    int trials;
    try {
      size = Integer.parseInt(args[0]);
      trials = Integer.parseInt(args[1]);
    } catch (NumberFormatException exception) {
      throw new IllegalArgumentException("Arguments must be integers");
    }
    Stopwatch stopwatch = new Stopwatch();
    PercolationStats stats = new PercolationStats(size, trials);
    StdOut.printf("Elapsed time %f ms\n", stopwatch.elapsedTime() * 1000);
    StdOut.printf("mean \t\t\t= %f \n", stats.mean());
    StdOut.printf("stddev \t\t\t= %f \n", stats.stddev());
    StdOut.print("95% confidence interval = ");
    StdOut.printf("%f %f \n", stats.confidenceLo(), stats.confidenceHi());
  }

}
