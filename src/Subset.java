import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

  public static void main(String[] args) {
    if (args.length < 1) {
      throw new IllegalArgumentException("Must define k");
    }

    RandomizedQueue<String> queue = new RandomizedQueue<>();

    int k = Integer.parseInt(args[0]);

    while (!StdIn.isEmpty()) {
      queue.enqueue(StdIn.readString());
    }

    if (k > queue.size() || 0 > k) {
      throw new IllegalArgumentException("K cannot be less than 0 or bigger than queue size");
    }

    for (int i = 0; i < k; i++) {
      StdOut.println(queue.dequeue());
    }

  }

}
