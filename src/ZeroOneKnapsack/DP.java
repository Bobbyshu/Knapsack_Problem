package ZeroOneKnapsack;

public class DP {
  public static void main(String[] args) {
    int[] weight = {1, 3, 4};
    int[] value = {15, 20, 30};
    int bagSize = 4;
    dp(weight, value, bagSize);
  }

  /**
   * Dynamic programming method.
   *
   * @param weight  weight of item
   * @param value   value of item
   * @param capacity capacity of knapsack
   */
  public static void dp(int[] weight, int[] value, int capacity) {

    // create dp table
    int n = weight.length;
    int[][] dp = new int[n][capacity + 1];

    // initialize array
    // we don't need to initialize row because
    // the array default number is zero in java

    // initialize column
    for (int j = weight[0]; j <= capacity; j++) {
      dp[0][j] = value[0];
    }

    // fill table
    for (int i = 1; i < weight.length; i++) {
      for (int j = 1; j <= capacity; j++) {
        // when current capacity less than weight of current item
        if (j < weight[i]) {
          // we don't choose this item
          // so the value if dp[i - 1][j]
          dp[i][j] = dp[i - 1][j];
        } else {  // when current capacity more than weight of current item
          // we have 2 option
          // 1. choose current item: dp[i - 1][j - weight[i]] + value[i]
          // 2. don't choose current item: dp[i - 1][j]
          dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
        }
      }
    }

//     print dp table to check
//    for (int i = 0; i < n; i++) {
//      for (int j = 0; j <= capacity; j++) {
//        System.out.print(dp[i][j] + "\t");
//      }
//      System.out.println("\n");
//    }
  }

}
