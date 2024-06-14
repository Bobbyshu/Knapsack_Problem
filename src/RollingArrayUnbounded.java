public class RollingArrayUnbounded {
  public static void main(String[] args) {
    int[] weight = {1, 3, 4};
    int[] value = {15, 20, 30};
    int bagSize = 4;
    rollingArray(weight, value, bagSize);
  }

  public static void rollingArray(int[] weight, int[] value, int capacity) {
    int[] dp = new int[capacity + 1];
    // traverse item
    for (int i = 0; i < weight.length; i++) {
      // traverse capacity in ascending order
      for (int j = weight[i]; j <= capacity; j++) {
        dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
      }
    }
  }
}
