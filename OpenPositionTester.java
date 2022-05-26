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
import java.util.NoSuchElementException;

/**
 * This class implements unit test methods to check the correctness of Application,
 * ApplicationIterator, ApplicationQueue and OpenPosition classes in the assignment.
 *
 */
public class OpenPositionTester {

  /**
   * This method tests and makes use of the Application constructor, getter methods,
   * toString() and compareTo() methods.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testApplication() {
    // create an Application with valid input
    try{
      Application app2 = new Application("Name", "email@gmail.com", 50);
    } catch (Exception e){
      return false;
    }
    // create an Application with invalid input:
    // blank name
    try{
      Application app2 = new Application("", "email@gmail.com", 50);
      return false;
    } catch (IllegalArgumentException e){
      // Passed
    } catch (Exception e){
      return false;
    }
    try{
      Application app2 = new Application(null, "email@gmail.com", 50);
      return false;
    } catch (IllegalArgumentException e){
      // Passed
    } catch (Exception e){
      return false;
    }

    // null email
    try{
      Application app2 = new Application("Name", null, 50);
      return false;
    } catch (IllegalArgumentException e){
      // Passed
    } catch (Exception e){
      return false;
    }
    try{
      Application app2 = new Application("Name", "", 50);
      return false;
    } catch (IllegalArgumentException e){
      // Passed
    } catch (Exception e){
      return false;
    }

    // no @ email
    try{
      Application app2 = new Application("Name", "emailgmail.com", 50);
      return false;
    } catch (IllegalArgumentException e){
      // Passed
    } catch (Exception e){
      return false;
    }

    // too many @ email
    try{
      Application app2 = new Application("Name", "@email@gmail.com", 50);
      return false;
    } catch (IllegalArgumentException e){
      // Passed
    } catch (Exception e){
      return false;
    }

    // invalid score
    try{
      Application app2 = new Application("Name", "email@gmail.com", -1);
      return false;
    } catch (IllegalArgumentException e){
      // Passed
    } catch (Exception e){
      return false;
    }
    try{
      Application app2 = new Application("Name", "email@gmail.com", 101);
      return false;
    } catch (IllegalArgumentException e){
      // Passed
    } catch (Exception e){
      return false;
    }
    try{
      Application app2 = new Application("Name", "email@gmail.com", 100);
      // passed
    } catch (IllegalArgumentException e){
      return false;
    }
    try{
      Application app2 = new Application("Name", "email@gmail.com", 0);
      // passed
    } catch (IllegalArgumentException e){
      return false;
    }

    // verify getters
    Application app = new Application("Name", "email@gmail.com", 50);
    if(app.getScore() != 50) return false;
    if(!app.getEmail().equals("email@gmail.com")) return false;
    if(!app.getName().equals("Name")) return false;

    // verify compareTo
    Application smallerApp = new Application("small", "email@gmail.com", 25);
    Application sameApp = new Application("small", "email@gmail.com", 25);
    if(smallerApp.compareTo(app) >= 0) return false;
    if(app.compareTo(smallerApp)  <= 0) return false;
    if(sameApp.compareTo(smallerApp) != 0) return false;

    // verify toString
    if(!app.toString().trim().equals("Name:email@gmail.com:50")) return false;

    // All tests passed return true
    return true;
  }

  /**
   * This method tests and makes use of the ApplicationIterator class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testApplicationIterator() {
    // create an ApplicationQueue with capacity at least 3
    ApplicationQueue queue = new ApplicationQueue(3);
    // and at least 3 Applications with different scores
    // add those Applications to the queue
    Application app1 = new Application("app1", "m@email.com", 10);
    Application app2 = new Application("app2", "m@email.com", 20);
    Application app3 = new Application("app3", "m@email.com", 5);
    queue.enqueue(app1);
    queue.enqueue(app2);
    queue.enqueue(app3);

    // verify that iterating through the queue gives you the applications in order of
    // INCREASING score
    ApplicationIterator iterator = new ApplicationIterator(queue);
    Application smallest = iterator.next();
    Application middle = iterator.next();
    Application largest = iterator.next();

    if(smallest.compareTo(middle) > 0) return false;
    if(middle.compareTo(largest) >0) return false;

    return true;
  }

  /**
   * This method tests and makes use of the enqueue() and dequeue() methods
   * in the ApplicationQueue class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testEnqueueDequeue() {
    // create an ApplicationQueue with capacity 3
    ApplicationQueue queue = new ApplicationQueue(3);
    // and at least 4 Applications with different scores
    Application app1 = new Application("app1", "m@email.com", 10);
    Application app2 = new Application("app2", "m@email.com", 20);
    Application app3 = new Application("app3", "m@email.com", 5);
    Application app4 = new Application("app4", "m@email.com", 1);
    // enqueue an invalid value (null)
    try {
      queue.enqueue(null);
      return false;
    } catch (NullPointerException e){
      // Passed
    } catch (Exception e){
      return false;
    }
    // enqueue one valid application
    queue.enqueue(app1);
    if(queue.size() != 1) return false;
    if(!queue.peek().toString().equals("app1:m@email.com:10")) return false;

    // enqueue two more valid applications
    queue.enqueue(app2);
    queue.enqueue(app3);

    if(queue.size() != 3) return false;
    if(!queue.peek().toString().equals("app3:m@email.com:5")) return false;

    // enqueue one more application (exceeds capacity)
    try {
      queue.enqueue(app4);
      return false;
    } catch (IllegalStateException e){
      // Passed
    } catch (Exception e){
      return false;
    }

    // dequeue one application (should be the lowest score)
    if(!queue.dequeue().toString().equals("app3:m@email.com:5")) return false;
    if(queue.size() != 2) return false;

    // dequeue all applications
    if(!queue.dequeue().toString().equals("app1:m@email.com:10")) return false;
    if(!queue.dequeue().toString().equals("app2:m@email.com:20")) return false;
    if(queue.size() != 0) return false;

    // dequeue from an empty queue
    try {
      queue.dequeue();
      return false;
    } catch (NoSuchElementException e){
      // Passed
    } catch (Exception e){
      return false;
    }

    // test bigger capacity
    Application app5 = new Application("app5", "m@email.com", 12);
    Application app6 = new Application("app6", "m@email.com", 4);
    Application app7 = new Application("app7", "m@email.com", 0);
    Application app8 = new Application("app8", "m@email.com", 3);

    ApplicationQueue queue2 = new ApplicationQueue(8);
    queue2.enqueue(app1);
    queue2.enqueue(app2);
    queue2.enqueue(app3);
    queue2.enqueue(app4);
    queue2.enqueue(app5);
    queue2.enqueue(app6);
    queue2.enqueue(app7);
    queue2.enqueue(app8);

    if(queue2.dequeue() != app7) return false;
    if(queue2.peek() != app4) return false;

    return true;
  }

  /**
   * This method tests and makes use of the common methods (isEmpty(), size(), peek())
   * in the ApplicationQueue class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testCommonMethods() {
    // create an ApplicationQueue with 0 capacity (should fail)
    try {
      ApplicationQueue queue = new ApplicationQueue(0);
      return false;
    } catch (IllegalArgumentException e){
      // Passed
    } catch (Exception e){
      return false;
    }

    // create an ApplicationQueue with capacity 3
    // and at least 3 Applications with different scores
    ApplicationQueue queue = new ApplicationQueue(3);
    Application app1 = new Application("app1", "m@email.com", 10);
    Application app2 = new Application("app2", "m@email.com", 20);
    Application app3 = new Application("app3", "m@email.com", 5);

    // verify the methods' behaviors on an empty queue
    if(!queue.isEmpty()) return false;
    try {
      queue.dequeue();
      return false;
    } catch (NoSuchElementException e){
      // Passed
    }
    // add one Application and verify the methods' behaviors
    queue.enqueue(app1);
    if(queue.peek() != app1) return false;

    // add the rest of the Applications and verify the methods' behaviors
    queue.enqueue(app2);
    queue.enqueue(app3);
    if(queue.peek() != app3) return false;

    return true;
  }

  /**
   * This method tests and makes use of OpenPosition class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testOpenPosition() {
    // create an OpenPosition with 0 capacity (should fail)
    try {
      OpenPosition op = new OpenPosition("Name", 0);
      return false;
    } catch (IllegalArgumentException e){
      // Passed
    } catch (Exception e){
      return false;
    }

    // create an OpenPosition with capacity 3
    OpenPosition op = new OpenPosition("Name", 3);
    // and at least 5 Applications with different scores
    Application app1 = new Application("app1", "email@gmail.com", 1);
    Application app2 = new Application("app2", "email@gmail.com", 9);
    Application app3 = new Application("app3", "email@gmail.com", 6);
    Application app4 = new Application("app4", "email@gmail.com", 4);
    Application app5 = new Application("app5", "email@gmail.com", 20);
    // verify that the 3 MIDDLE-scoring Applications can be added
    // don't use the highest and lowest scoring applications YET
    if(!op.add(app2)) return false;
    if(!op.add(app3)) return false;
    if(!op.add(app4)) return false;

    // verify that getApplications returns the correct value for your input
    if(!op.getApplications().trim().equals("app4:email@gmail.com:4\n" +
            "app3:email@gmail.com:6\n" +
            "app2:email@gmail.com:9")) {
      return false;
    }

    // verify that the result of getTotalScore is the sum of all 3 Application scores
    if(op.getTotalScore() != 19) return false;

    // verify that the lowest-scoring application is NOT added to the OpenPosition
    if(op.add(app1)) return false;

    // verify that the highest-scoring application IS added to the OpenPosition
    if(!op.add(app5)) return false;

    // verify that getApplications has changed correctly
    if(!op.getApplications().trim().equals("app3:email@gmail.com:6\n" +
            "app2:email@gmail.com:9\n" +
            "app5:email@gmail.com:20")) return false;


    // verify that the result of getTotalScore has changed correctly
    if(op.getTotalScore() != 35) return false;

    return true;
  }

  /**
   * This method calls all the test methods defined and implemented in your OpenPositionTester class.
   *
   * @return true if all the test methods defined in this class pass, and false otherwise.
   */
  public static boolean runAllTests() {
    return testApplication() && testApplicationIterator()
            && testEnqueueDequeue() && testCommonMethods()
            && testOpenPosition();
  }

  /**
   * Driver method defined in this OpenPositionTester class
   *
   * @param args input arguments if any.
   */
  public static void main(String[] args) {
    System.out.print(runAllTests());
  }

}
