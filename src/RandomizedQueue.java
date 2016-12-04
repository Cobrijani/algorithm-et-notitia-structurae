import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] content;

  private int size = 0;

  public RandomizedQueue() {
    content = (Item[]) new Object[12];
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  /**
   * Add new item to queue
   * 
   * @param item
   *          new item to be added
   */
  public void enqueue(Item item) {
    validateNotNull(item);
    if (size == content.length) {
      resize(content.length * 2);
    }
    content[size++] = item;

  }

  /**
   * Remove and return random item from queue
   * 
   * @return random item
   */
  public Item dequeue() {
    validateNotEmpty();
    int number = getRandomIndex();

    final Item itemToBeRemoved = content[number];
    content[number] = content[size - 1];
    content[size - 1] = null;

    --size;

    if (size > 0 && size == content.length / 4) {
      resize(content.length / 2);
    }

    return itemToBeRemoved;
  } // remove and return a random item

  private int getRandomIndex() {
    if (size == 1) {
      return 0;
    } else {
      return StdRandom.uniform(0, size);
    }
  }

  /**
   * Get random item from queue
   * 
   * @return random item
   */
  public Item sample() {
    validateNotEmpty();
    return content[getRandomIndex()];
  }

  /**
   * shift array to left starting from position
   * 
   * @param start
   *          position from which to shift
   */
  private void shiftLeft(int start) {
    for (int i = start + 1; i < size; ++i) {
      content[i - 1] = content[i];

    }
    content[size - 1] = null;
  }

  private void resize(int newCapacity) {
    assert size < newCapacity;

    Item[] copy = (Item[]) new Object[newCapacity];
    for (int i = 0; i < size; i++) {
      copy[i] = content[i];
    }
    content = copy;
  }

  private void validateNotEmpty() {
    if (isEmpty()) {
      throw new NoSuchElementException("queue is empty element does not exist");
    }
  }

  private void validateNotNull(Item item) {
    if (item == null) {
      throw new NullPointerException("item cannot be null");
    }
  }

  @Override
  public Iterator<Item> iterator() {
    return new RandomArrayIterator(content, size);
  }

  private class RandomArrayIterator implements Iterator<Item> {

    private Item[] items;
    private int currentItemIndex = 0;

    public RandomArrayIterator(Item[] copyContent, int contentSize) {
      items = (Item[]) new Object[contentSize];
      for (int i = 0; i < contentSize; ++i) {
        items[i] = copyContent[i];
      }

      StdRandom.shuffle(items);
    }

    @Override
    public boolean hasNext() {
      return currentItemIndex < items.length;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return items[currentItemIndex++];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

  }

  public static void main(String... args) {
    RandomizedQueue<String> rq = new RandomizedQueue<>();

    rq.enqueue("LPVCLGLINI");
    rq.enqueue("QWECNLIQKY");
    rq.enqueue("GCYZZCGRXM");
    rq.dequeue();
    rq.dequeue();
    rq.sample();
    rq.dequeue();

  }

}
