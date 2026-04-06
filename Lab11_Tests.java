import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.*;
import java.io.*;
import java.net.*;
import java.time.*;

public class Lab11_Tests {
    /*
        Complete the test case below that checks to see that threads A and B have both contributed 100 entries respectively
        to the shared ArrayList when they have both finished running.
    */
    @Test
    public void test1() {
        Lab11_Thread threadA = new Lab11_Thread("A1", 100);
        Lab11_Thread threadB = new Lab11_Thread("B1", 100);

        //reset list before this test
        threadA.setData(new ArrayList<String>()); 

        threadA.start();
        threadB.start();
        try {
            threadA.join(); 
            threadB.join();
        } catch (Exception e) {
            System.out.println("Test 1 FAILED");
            e.printStackTrace(); 
        }
        //after completing both, in total 200 results will pass
        int size = threadA.getData().size(); 
        if (size == 200) {
            System.out.println("Test 1 PASSED. ArrayList has 200 entries as expected");
        } else {
            System.out.println("Test 1 FAILED. ArrayList has " + size + " entries");
        }

        assertEquals(200, size);
    }

    /*
        Complete the test case below that checks to see if the shared ArrayList has at least 10 entries after 500ms of system time
    */
    @Test
    public void test2() {

        Lab11_Thread threadA = new Lab11_Thread("A2", 500);
        Lab11_Thread threadB = new Lab11_Thread("B2", 500);

    
        threadA.setData(new ArrayList<String>());

        threadA.start();
        threadB.start();
        try {
            Thread.sleep(500); 
        } catch (Exception e){
            System.out.println("Test 2 FAILED");
            e.printStackTrace();
        }

        int size = threadA.getData().size(); 
        if (size >= 10) {
            System.out.println("Test 2 PASSED: ArrayList has at least 10 entries as expected");
        } else {
            System.out.println("Test 2 FAILED: Arraylist has " + size + " entries");
        }

        assertEquals(true, size >= 10);

         try {
            threadA.join();
            threadB.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Complete the test case below that checks to see if thread A finishes adding its 10 entries before thread B was allowed to 
        add anything to the shared ArrayList
    */
    @Test
    public void test3() {
        Lab11_Thread threadA = new Lab11_Thread("A3", 10);
        Lab11_Thread threadB = new Lab11_Thread("B3", 10);

        threadA.setData(new ArrayList<String>());
        threadB.setData(new ArrayList<String>());

        threadA.start();

        try {
            threadA.join();
        } catch (Exception e){
            System.out.println("Test 3 FAILED");
            e.printStackTrace();
        }
        
        threadB.start();
        try {
            threadB.join(); 
        } catch (Exception e) {
            System.out.println("Test 3 FAILED");
            e.printStackTrace();
        }

        ArrayList<String> result = threadB.getData(); 
        boolean passed = true; 

        for (int i = 0; i < 10; i++) {
            if (!result.get(i).startsWith("A3")) {
                System.out.println("Test 3: FAILED. Expected A3 at index " + i + ", but found " + result.get(i)); 
                passed = false; 
            }
            assertEquals("Expected A3 entry at index " + i, true, result.get(i).startsWith("A3"));
        }

        for (int i = 10; i < 20; i++) {
            if (!result.get(i).startsWith("B3")) {
                System.out.println("Test 3: FAILED. Expected B3 at index " + i + ", but found " + result.get(i));
                passed = false; 
            }
            assertEquals("Expected B3 entry at index " + i, true, result.get(i).startsWith("B3"));
        }

        if (passed) {
            System.out.println("Test 3 Passed. All entries A3 appear before B3");
        }


    }
}
