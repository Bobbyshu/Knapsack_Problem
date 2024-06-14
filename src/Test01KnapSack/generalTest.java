package Test01KnapSack;

import ZeroOneKnapsack.BruteForce;
import ZeroOneKnapsack.DP;
import ZeroOneKnapsack.RollingArray;

import java.util.*;

public class generalTest {
  public static void main(String[] args) {
    // Set the range of input data sizes to test
    int[] inputSizes = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
    // if test include brute force
    // use these array because brute force in exponential time complexity
    // int[] inputSizes = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

    // Set the number of times to repeat the test for each input size
    int numRepeats = 10;

    for (int size : inputSizes) {
      System.out.println("Testing input size: " + size);

      // Generate random input data
      int[] weight = new int[size];
      int[] value = new int[size];
      int maxWeight = size * 10;
      Random rand = new Random();
      for (int i = 0; i < size; i++) {
        weight[i] = rand.nextInt(maxWeight) + 1;
        value[i] = rand.nextInt(100) + 1;
      }
      int bagSize = maxWeight / 2;

      // Test the brute force implementation
      // test brute force => uncomment these code
//      long bfTime = 0;
//      for (int i = 0; i < numRepeats; i++) {
//        long startTime = System.nanoTime();
//        BruteForce bf = new BruteForce();
//        bf.bruteForce(weight, value, bagSize);
//        long endTime = System.nanoTime();
//        bfTime += endTime - startTime;
//      }
//      bfTime /= numRepeats;
//      double bfTimeMs = bfTime / 1000000.0;

      // Test the dynamic programming implementation
      long dpTime = 0;
      for (int i = 0; i < numRepeats; i++) {
        long startTime = System.nanoTime();
        DP.dp(weight, value, bagSize);
        long endTime = System.nanoTime();
        dpTime += endTime - startTime;
      }
      dpTime /= numRepeats;
      double dpTimeMs = dpTime / 1000000.0;

      // Test the rolling array implementation
      long raTime = 0;
      for (int i = 0; i < numRepeats; i++) {
        long startTime = System.nanoTime();
        RollingArray.rollingArray(weight, value, bagSize);
        long endTime = System.nanoTime();
        raTime += endTime - startTime;
      }
      raTime /= numRepeats;
      double raTimeMs = raTime / 1000000.0;

      // Print the results
//      System.out.printf("Brute force: %.3f ms\n", bfTimeMs);
      System.out.printf("Dynamic programming: %.3f ms\n", dpTimeMs);
      System.out.printf("Rolling array: %.3f ms\n", raTimeMs);
      System.out.println();
    }
  }


}


