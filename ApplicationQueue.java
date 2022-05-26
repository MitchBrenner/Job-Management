//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P10 Open Position
// Course:   CS 300 Spring 2022
//
// Author:   Mitchell Brenner
// Email:    mkbrenner3@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array-based heap implementation of a priority queue containing Applications. Guarantees the
 * min-heap invariant, so that the Application at the root should have the lowest score, and
 * children always have a higher or equal score as their parent. The root of a non-empty queue
 * is always at index 0 of this array-heap.
 */
public class ApplicationQueue implements PriorityQueueADT<Application>, Iterable<Application> {
  private Application[] queue; // array min-heap of applications representing this priority queue
  private int size;            // size of this priority queue

  /**
   * Creates a new empty ApplicationQueue with the given capacity
   *
   * @param capacity Capacity of this ApplicationQueue
   * @throws IllegalArgumentException with a descriptive error message if the capacity is not a
   *                                  positive integer
   */
  public ApplicationQueue(int capacity) {
    //  verify the capacity
    if(capacity <= 0) throw new IllegalArgumentException("Invalid capacity");

    //  initialize fields appropriately
    queue = new Application[capacity];
    size = 0;
  }

  /**
   * Checks whether this ApplicationQueue is empty
   *
   * @return {@code true} if this ApplicationQueue is empty
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns the size of this ApplicationQueue
   *
   * @return the size of this ApplicationQueue
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Adds the given Application to this ApplicationQueue and use the percolateUp() method to
   * maintain min-heap invariant of ApplicationQueue. Application should be compared using
   * the Application.compareTo() method.
   *
   *
   * @param o Application to add to this ApplicationQueue
   * @throws NullPointerException if the given Application is null
   * @throws IllegalStateException with a descriptive error message if this ApplicationQueue is full
   */
  @Override
  public void enqueue(Application o) {
    //  verify the application
    if(o == null) throw new NullPointerException("Null Application");

    //  verify that the queue is not full
    if(size >= queue.length) throw new IllegalStateException("ApplicationQueue is full");

    //  if allowed, add the application to the queue and percolate to restore the heap condition
    size++;
    queue[size - 1] = o;

    percolateUp(size - 1);

  }

  /**
   * Removes and returns the Application at the root of this ApplicationQueue, i.e. the Application
   * with the lowest score.
   *
   * @return the Application in this ApplicationQueue with the smallest score
   * @throws NoSuchElementException with a descriptive error message if this ApplicationQueue is
   *                                empty
   */
  @Override
  public Application dequeue() {
    //  verify that the queue is not empty
    if(isEmpty()) throw new NoSuchElementException("Empty ApplicationQueue");

    //  save the lowest-scoring application
    Application returnApp = queue[0];

    //  replace the root of the heap and percolate to restore the heap condition
    swap(0, size - 1);
    queue[size - 1] = null;
    size--;
    percolateDown(0);

    //  return the lowest-scoring application
    return returnApp;  
  }

  /**
   * An implementation of percolateDown() method. Restores the min-heap invariant of a given
   * subtree by percolating its root down the tree. If the element at the given index does not
   * violate the min-heap invariant (it is due before its children), then this method does not
   * modify the heap. Otherwise, if there is a heap violation, then swap the element with the
   * correct child and continue percolating the element down the heap.
   *
   * This method may be implemented recursively OR iteratively.
   *
   * @param i index of the element in the heap to percolate downwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  private void percolateDown(int i) {

    // implement the min-heap percolate down algorithm to modify the heap in place
    // if left child is greater than size than left child DNE
    if(getLeftChildIndex(i) >= size || queue[getLeftChildIndex(i)] == null){
      return;
    }

    int compareIndex;

    if(hasLeftChild(i) && hasRightChild(i)){
      int left = getLeftChildIndex(i);
      int right = getRightChildIndex(i);

      if(queue[left].compareTo(queue[right]) <= 0){
        compareIndex = left;
      } else {
        compareIndex = right;
      }
    } else {
      compareIndex = getLeftChildIndex(i);
    }

    if(queue[i].compareTo(queue[compareIndex]) > 0){
      swap(i, compareIndex);
      percolateDown(compareIndex);
    }

  }

  /**
   * An implementation of percolateUp() method. Restores the min-heap invariant of the tree
   * by percolating a leaf up the tree. If the element at the given index does not violate the
   * min-heap invariant (it occurs after its parent), then this method does not modify the heap.
   * Otherwise, if there is a heap violation, swap the element with its parent and continue
   * percolating the element up the heap.
   *
   * This method may be implemented recursively OR iteratively.
   *
   * Feel free to add private helper methods if you need them.
   *
   * @param i index of the element in the heap to percolate upwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  private void percolateUp(int i) {

    // if parent index is less than 0 then i is the root
    if(getParentIndex(i) < 0 || queue[getParentIndex(i)] == null) return;

    // If app at i is less than at i's parent swap them
    if(queue[i].compareTo(queue[getParentIndex(i)]) < 0){
      swap(i, getParentIndex(i));
      // then recursively call percolateUp at the parent index of i, which is now i
      percolateUp(getParentIndex(i));
    }
  }

  /**
   * Returns the Application at the root of this ApplicationQueue, i.e. the Application with
   * the lowest score.
   *
   * @return the Application in this ApplicationQueue with the smallest score
   * @throws NoSuchElementException if this ApplicationQueue is empty
   */
  @Override
  public Application peek() {
    //  verify that the queue is not empty
    if(isEmpty()) throw new NoSuchElementException("Queue is empty");
    //  return the lowest-scoring application
    return queue[0];
  }

  /**
   * Returns a deep copy of this ApplicationQueue containing all of its elements in the same order.
   * This method does not return the deepest copy, meaning that you do not need to duplicate
   * applications. Only the instance of the heap (including the array and its size) will be duplicated.
   *
   * @return a deep copy of this ApplicationQueue. The returned new application queue has the same
   *         length and size as this queue.
   */
  public ApplicationQueue deepCopy() {

    ApplicationQueue deepCopyQueue = new ApplicationQueue(queue.length);

    for (Application application : queue) {
      deepCopyQueue.enqueue(application);
    }
    return deepCopyQueue;

  }

  /**
   * Returns a String representing this ApplicationQueue, where each element (application) of the
   * queue is listed on a separate line, in order from the lowest score to the highest score.
   *
   * This implementation is provided.
   *
   * @see Application#toString()
   * @see ApplicationIterator
   * @return a String representing this ApplicationQueue
   */
  @Override
  public String toString() {
    StringBuilder val = new StringBuilder();

    for (Application a : this) {
      val.append(a).append("\n");
    }

    return val.toString();

  }

  /**
   * Returns an Iterator for this ApplicationQueue which proceeds from the lowest-scored to the
   * highest-scored Application in the queue.
   *
   * This implementation is provided.
   *
   * @see ApplicationIterator
   * @return an Iterator for this ApplicationQueue
   */
  @Override
  public Iterator<Application> iterator() {
    return new ApplicationIterator(this);
  }

  /**
   * Helper Method: Swaps the elements at indices i and j
   *
   * @param i index to swap with
   * @param j index to swap with
   * @throws IndexOutOfBoundsException if i or j is not in the range 0..size-1
   */
  private void swap(int i, int j) throws IndexOutOfBoundsException {
    Application temp = queue[i];
    queue[i] = queue[j];
    queue[j] = temp;
  }

  /**
   * helper method Returns the index of the parent of the node at position j of the heap
   *
   * @param c index of a node (child)
   * @return index of the parent of node at position j
   */
  private int getParentIndex(int c) {
    return (c - 1) / 2;
  }

  /**
   * helper method Returns the index of the left child of the node at index p in the heap
   *
   * @param p index of a node (parent aka internal node)
   * @return index of the left child of p
   */
  private int getLeftChildIndex(int p) {
    return 2 * p + 1;
  }

  /**
   * helper method Returns the index of the right child of the node at index p in the heap
   *
   * @param p index of a node
   * @return index of the right child of p
   */
  private int getRightChildIndex(int p) {
    return 2 * p + 2;
  }

  /**
   * helper method Checks whether the node of index p has a left child or not
   *
   * @param p index of a node/element stored in the heap
   * @return true if the node of index p has a left child, false otherwise
   */
  private boolean hasLeftChild(int p) {
    return getLeftChildIndex(p) < size;
  }

  /**
   * Checks whether the node of index p has a right child
   *
   * @param p index of a particular node stored in the heap
   * @return true if the node of index p has a left child, false otherwise
   */
  private boolean hasRightChild(int p) {
    return getRightChildIndex(p) < size;
  }

}
