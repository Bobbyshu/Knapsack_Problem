package ZeroOneKnapsack;

public class RollingArray {
  public static void main(String[] args) {
    int[] weight = {1, 3, 4};
    int[] value = {15, 20, 30};
    int bagSize = 4;
    rollingArray(weight, value, bagSize);
  }

  /**
   * Rolling array method.
   *
   * @param weight   weight of item
   * @param value    value of item
   * @param capacity capacity of item
   */
  public static void rollingArray(int[] weight, int[] value, int capacity) {
    int n = weight.length;
    // dp[j] states that the most value we can get for knapsack with j capacity
    int[] dp = new int[capacity + 1];
    // we traverse item first then traverse capacity
    for (int i = 0; i < n; i++) {
      for (int j = capacity; j >= weight[i]; j--) {
        dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
      }
    }
//     print the result
//    for (int j = 0; j <= capacity; j++) {
//      System.out.print(dp[j] + " ");
//    }
  }
}
