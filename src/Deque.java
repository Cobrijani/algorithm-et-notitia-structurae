import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node head;
  private Node tail;
  private int queueSize;

  private final class Node {
    private Node previous = null;
    private Item item = null;
    private Node next = null;

    public boolean isTail() {
      return next == null;
    }

    public boolean isHead() {
      return previous == null;
    }

  }

  /**
   * Default constructor
   */
  public Deque() {
    head = null;
    tail = null;
    queueSize = 0;
  }

  public boolean isEmpty() {
    return queueSize == 0;
  }

  public int size() {
    return queueSize;
  } // return the number of items on the deque

  /**
   * Add item at the beginning of the queue
   * 
   * @param item
   *          item to be added
   */
  public void addFirst(Item item) {
    validateNotNull(item);
    Node newNode = new Node();
    newNode.item = item;
    newNode.next = head;
    if (isEmpty()) {
      tail = newNode;
    } else {
      head.previous = newNode;
    }
    head = newNode;
    ++queueSize;
  } // add the item to the front

  /**
   * Add element at the end of queue
   * 
   * @param item
   *          item to be added
   */
  public void addLast(Item item) {
    validateNotNull(item);
    Node newNode = new Node();
    newNode.item = item;
    newNode.previous = tail;

    if (isEmpty()) {
      head = newNode;
    } else {
      tail.next = newNode;
    }
    tail = newNode;
    ++queueSize;
  } // add the item to the end

  /**
   * Removes first element
   * 
   * @return element to be removed
   */
  public Item removeFirst() {
    validateNotEmpty();

    Item retVal = head.item;
    head = head.next;
    --queueSize;
    if (isEmpty()) {
      tail = null;
    } else {
      head.previous = null;
    }

    return retVal;
  } // remove and return the item from the front

  /**
   * Removes last element
   * 
   * @return element to be removed
   */
  public Item removeLast() {
    validateNotEmpty();

    Item retVal = tail.item;

    tail = tail.previous;
    --queueSize;
    if (isEmpty()) {
      head = null;
    } else {
      tail.next = null;
    }

    return retVal;
  } // remove and return the item from the end

  private void validateNotEmpty() {
    if (isEmpty()) {
      throw new NoSuchElementException("Element does not exist");
    }
  }

  private void validateNotNull(Item item) {
    if (item == null) {
      throw new NullPointerException("Item must not be null");
    }
  }

  @Override
  public Iterator<Item> iterator() {
    return new DequeIterator(head);
  }

  private class DequeIterator implements Iterator<Item> {
    private Node current;

    public DequeIterator(Node current) {
      this.current = current;
    }

    @Override
    public boolean hasNext() {
      return current != null && !isEmpty();
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item retVal = current.item;
      current = current.next;
      return retVal;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Testing main
   * 
   * @param args
   *          command line arguments
   */
  public static void main(String[] args) {

  }

}
