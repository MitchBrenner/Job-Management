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

/**
 * An application handler of an open position using priority queue. Only saves a new Application
 * when the queue is not full, or when it can replace older, lower-scored ones with its higher
 * scores.
 */
public class OpenPosition {
  private String positionName;
  private ApplicationQueue applications; // the priority queue of all applications
  private int capacity;                  // the number of vacancies

  /**
   * Creates a new open position with the given capacity
   *
   * @param capacity the number of vacancies of this position
   * @throws IllegalArgumentException with a descriptive error message if the capacity is not a
   *                                  positive integer
   */
  public OpenPosition(String positionName, int capacity) {
    //  verify the value of capacity
    if(capacity <= 0 ) throw new IllegalArgumentException("Invalid Capacity");

    //  initialize the data fields appropriately
    this.positionName = positionName;
    this.capacity = capacity;

    applications = new ApplicationQueue(capacity);
  }

  /**
   * Gets the position name
   *
   * @return the position name
   */
  public String getPositionName() { return this.positionName; }

  /**
   * Tries to add the given Application to the priority queue of this position.
   * return False when the new Application has a lower score than the lowest-scored Application
   * in the queue.
   *
   * @return Whether the given Application was added successfully
   */
  public boolean add(Application application) {
    //  if the queue is full, determine whether this application has a higher score than
    // the current lowest-scoring application; if not, do not add it
    if(applications.size() == capacity) {
      if(application.compareTo(applications.peek()) > 0){
        applications.dequeue();
        applications.enqueue(application);
        return true;
      }
    } else if(applications.size() < capacity) {
      applications.enqueue(application);
      return true;
    }
    return false;
  }

  /**
   * Returns the list of Applications in the priority queue.
   *
   * @return The list of Applications in the priority queue, in increasing order of the
   * scores.
   */
  public String getApplications() {
    return applications.toString();
  }

  /**
   * Returns the total score of Applications in the priority queue.
   *
   * @return The total score of Applications in the priority queue.
   */
  public int getTotalScore() {
    //  calculate the total score of all applications currently in the queue
    int totalScore = 0;

    ApplicationIterator iterator = new ApplicationIterator(applications);

    while(iterator.hasNext()){
      totalScore += iterator.next().getScore();
    }

    return totalScore;
  }

}
