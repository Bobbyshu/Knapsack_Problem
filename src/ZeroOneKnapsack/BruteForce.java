package ZeroOneKnapsack;

import java.util.*;

public class BruteForce {
  // test case
  public static void main(String[] args) {
    int[] weight = {1, 3, 4};
    int[] value = {15, 20, 30};
    int capacity = 4;
    BruteForce bf = new BruteForce();

    List<List<Integer>> list = bf.bruteForce(weight, value, capacity);
    // choose all available options
    System.out.println(list);

    // filter choice
    int maxVal = 0, choiceIdx = 0;
    for (int i = 0; i < list.size(); ++i) {
      List<Integer> choice = list.get(i);
      int curVal = 0, curWeight = 0;
      for (int idx : choice) {
        curVal += value[idx];
        curWeight += weight[idx];
        // over capacity
        if (curWeight > capacity) {
          // reset cur val and break internal loop
          curVal = 0;
          break;
        }
      }
      if (curVal > maxVal) {
        maxVal = curVal;
        choiceIdx = i;
      }
    }

    System.out.println("Maximum value we can get is " + maxVal
        + " and the selection is " + list.get(choiceIdx));
  }

  List<List<Integer>> res;
  LinkedList<Integer> choice;

  /**
   * input three parameter to generate all available choices.
   *
   * @param weight   weight of item
   * @param value    value of item
   * @param capacity capacity of knapsack
   * @return list contains all available choices
   */
  public List<List<Integer>> bruteForce(int[] weight, int[] value, int capacity) {
    res = new ArrayList<>();
    choice = new LinkedList<>();
    int[] nums = new int[weight.length];
    // initialize array
    for (int i = 0; i < nums.length; ++i) {
      nums[i] = i;
    }
    backtrace(nums, 0);
    return res;
  }

  /**
   * backtrace to simulate the choose process.
   *
   * @param nums     input arr
   * @param startIdx start index for next recursion
   */
  public void backtrace(int[] nums, int startIdx) {
    res.add(new ArrayList<>(choice));
    // out of boundary
    if (startIdx >= nums.length) {
      return;
    }

    for (int i = startIdx; i < nums.length; ++i) {
      choice.add(nums[i]);
      backtrace(nums, i + 1);
      // undo the last choice
      choice.removeLast();
    }
  }
}
