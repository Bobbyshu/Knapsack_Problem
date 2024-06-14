
# Knapsack Problem
Name: Yunmu Shu  
Topic: KnapSack  

## Introduction
The **KnapSack Problem** is the following problem in combinatorial optimization:

Given a set of items, each with a weight and a value, determine which items to include in the collection so that the total weight is less than or equal to a given limit and the total value is as large as possible.
It derives its name from the problem faced by someone who is constrained by a fixed-size knapsack and must fill it with the most valuable items. The problem often arises in resource allocation where the decision-makers have to choose from a set of non-divisible projects or tasks under a fixed budget or time constraint, respectively.

The knapsack problem has been studied for more than a century, with early works dating as far back as 1897. The name "knapsack problem" dates back to the early works of the mathematician Tobias Dantzig (1884–1956), and refers to the commonplace problem of packing the most valuable or useful items without overloading the luggage.

In this paper, I will try to analyze the mechanism of knapsack and its time&space complexity. This paper will only talk about **0-1 knapsack problem** and **unbounded knapsack problem**, the advanced algorithm will be analyzed in the future.


## Analysis of Algorithm
## (1) For 0-1 KnapSack Problem
0-1 KnapSack Problem may be implemented in a variety of methods in code, each of which is showed below in Big O number in this analysis(n for items number, m for capacity of bag).

| Version |  Big O | Space Used |
| :-- | :-- |  :-- |
| Brute Force | $O(n * 2^n)$  | $O(n)$|
| Dynamic Programming | $O(n * m)$  | $O(n * m)$ |
| Rolling Array | $O(n * m)$ | $O(m)$ |

### Brute Force version:
For each item in knapsack, we have two choices, select or not. So, for n item we have 2^n choices, and we need to put the choice into array which cost O(n), the total time complexity is O(n * 2^n). 
For space complexity, because each time we need a number to store the result of choosing n items from all items, we need to traverse for 1 to n. So, the space complexity is O(n).   
Here's the pseudo code:
```text 
Brute(n, items[], values[]):
  res[]
  for (int i = 1; i < n; ++i) {
    res[i] = enumerate(choose i item in max Value) 
  }

  return max number in res array
```
For this solution, it's in exponential time complexity which is time-consuming. So, we need to optimize it by using dynamic programming.

### Dynamic Programming version:
### 1. Definition states
F[i,v] states that for i items, the most value we can achieve by putting them into a knapsack whose capacity is v. The transfer formula show below:
$$ F[i, v] = max[F[i-1, v], F[i-1, v - Ci] + Wi]$$
In this formula, because we only need to decide choose or not for each item. So, when we think about the sub-problem, it becomes the question about current item and the former (i - 1) items. In this situation, if we don't choose current item, the most value we can get is F[i - 1, v]. If we choose current item, Ci for the current item required capacity, the remain capacity will become (v - Ci). Wi for the value that will be added into total revenue, and we can get (F[i - 1, v - Ci] + Wi). So, we only need to compare these two option and get the most value in each step.  
Here's the pseudo-code:
```text 
DP():
  F[]
  for i = 1 to N
    for v = Ci to V
      F[i, v] = max{F[i − 1, v], F[i − 1, v − Ci] + Wi}
```
We use 2D array to store the value in this method. By using dynamic programming, we can optimize the time complexity to O(n*m) (n for items number and m for capacity of knapsack).

### 2. Initialize dp table

Take this problem as example:  
**The weight of knapsack is 4**

|   | weight |  value  |
|  ----  | --  | -- |
| item0  | 1 | 15 |
| item1  | 3 | 20 |
| item2  | 4 | 30 |

**Initialize Column**  
If capacity of knapsack is zero, whatever item we choose, the most value we can get is zero. So, the dp[i][0] should all be zero.

|  DP table   | 0 |  1  |  2 | 3 | 4|
|  ----  | ----  | ---- | ---- |  ---- | ---- |
| item0  | 0 |  |   |  | |
| item1  | 0 |  |   |  | |
| item2  | 0 |  |   |  | |

**Initialize Row**  
Now we need to think the first line of dp table, dp[0][j] states that when i equals to 0, the most value we can get. In this example, the item 0 can be put inside knapsack when the weight equals or more than 1. Normally, we will sort the item array and let them show in increasing weight. If current j smaller than weight of item0, dp[i][j] should be zero because we can't put item 0 inside knapsack.

|  DP table   | 0 |  1  |  2 | 3 | 4|
|  ----  | ----  | ---- | ---- |  ---- | ---- |
| item0  | 0 | 15 | 15  | 15 | 15 |
| item1  | 0 |  |   |  | |
| item2  | 0 |  |   |  | |

For remain value, we can initialize any value because we will cover them during the trasfer of states.


### 3. Traverse Sequence
The sequence of traversing is not so strict because it's same result for us to traverse either from i or from j. For the formula, we can find that dp[i][j] comes from dp[i-1][j] and dp[i - 1][j - weight[i]]. Both of two data are locate in left upper of dp[i][j].So, we can make sure they will be calculated before we traverse to dp[i][j].


### Rolling Array:
### 1. Definition states
Based on dynamic programming, we can optimize the space complexity by compressing states. Checking the formula, we can find that if we copy the data in dp[i - 1] level to dp[i] level, the formula can be:
$$ dp[i][j] = max(dp[i][j], dp[i][j - weight[i]] + value[i])$$

Moreover, we can use a linear array to store the number which is dp[j]. At that time, dp[j] states that the most value that knapsack can get with j capacity.
$$ dp[j] = max(dp[j], dp[j - weight[i]] + value[i])$$

By compressing state, we can optimize the space complexity to O(m) and it won't be related to the quantity of items.
### 2. Traverse Sequence
After we compress state, it's important for us to make sure we traverse in a right way.
```text 
Rolling array(weight[], value[], capacity):
  F[]
  for i = 1 to N
    for j = Capacity to weight[i]
      F[j] = max{F[j], F[j - weights[i]] + values[i]}
```
Different from 2D array, we should traverse capacity in descending order. Because we need to make each item can only been put into knapsack once, if we traverse in ascending order, item 0 may be put into knapsack more than once.
For example, for item0, weight[0] = 1 and value[0] = 20. If we traverse in ascending order:
```text 
dp[1] = dp[1-weight[0]] + value[0] = 15
dp[2] = dp[2-weight[0]] + value[0] = 30 
```
However, at that time dp[2] is 30 which means item0 has been put into Knapsack twice. So, we can't traverse in ascending order.
In descending order:
```text 
dp[2] = dp[2-weight[0]] + value[0] = 15 
dp[1] = dp[1-weight[0]] + value[0] = 15  
```
In this order, we won't overlap the state with former calculation and take each item once. For 2D array, the reason why we don't need to use descending order traverse is that each dp[i][j] is calculated by their upper level, **so the same level dp[i][j] won't overlap them.**
For nest sequence, we need to make sure capacity is internal. Otherwise, if we put traverse capacity in external and in descending order. Each dp[j] will only put 1 item, which means knapsack only has been put 1 item.

## (2) For Unbounded Knapsack Problem
Unbounded knapsack problem has same intention as 0-1 knapsack problem. There are N items and a knapsack with a maximum weight capacity of W. The weight and value of the i-th item are represented as weight[i] and value[i], respectively. Each item has an unlimited supply (i.e., can be picked multiple times) which is the difference between 0-1 and bounded knapsack problem. But they both want to find a way to maximize the total value of the items in the knapsack.
Because we have talked about the traverse sequence before, **so we only need to change traverse sequence to ascending order for capacity.** The reason why we need to change is that each item can be added into knapsack multiple times.
```text 
For Unbounded Knapsack
Rolling array(weight[], value[], capacity):
  F[]
  for i = 1 to N # traverse item
    for j = weight[i] to capacity # traverse capacity
      F[j] = max{F[j], F[j - weights[i]] + values[i]}
```
However, in Unbounded Knapsack Problem, we don't need to consider nest sequence. That's because dp[j] is calculated by index j, we only need to make sure all the index before j have been calculated.
Take some example in 0-1 knapsack:
**If we traverse item externally and traverse capacity internally:**
|  DP [j]  | |    |   |  | |
|  ----  | ----  | ---- | ---- |  ---- | ---- |
| item0  | 0 | 15 | 30  | 45 | 60 |
| item1  | 0 | 15 | 30  | 45 | |
| item2  | 0 |  |   |  | |

**If we traverse capacity externally and traverse item internally:**

|  DP [j]  | |    |   |  | |
|  ----  | ----  | ---- | ---- |  ---- | ---- |
| item0  | 0 | 15 | 30  | 45 |  |
| item1  | 0 | 15 | 30  | 45 | |
| item2  | 0 | 15 | 30  |  | |

We can find that it's same result in different nest sequence.

## Empirical Analysis
Empirical research is research using empirical evidence. It is also a way of gaining 
knowledge by means of direct and indirect observation or experience. 
Empiricism values some research more than other kinds.

The test code can be found in this file:
* [Test] -- implementation of test 3 methods in 0-1 knapsack problem

For all empirical results, I write a code to test different input size. For brute force, it's hard to
input over 20 numbers because it's in O(n * 2^n) time complexity. So, I test brute force from 10 to
20 and test Dynamic programming & Rolling Array from 100 to 1000.

![BruteForce]  

In brute force way, we can find that it increases sharply when input size comes to 18, because it's
in exponential time complexity. Besides, during implementation, we need to store each selection into
ArrayList. After searching, we still need to traverse all the choices to determine which one is best,
it's really time-consuming.

![DP]  

In dynamic programming way, we can find that it save a lot of time comparing to bruteforce. Because
it uses 2D dp table to store value which decrease the duplicate calculation and let the time complexity
approach to linear.

![Rolling]  

In rolling array, it even becomes faster at first because input size isn't big enough. But the whole
curve is almost linear gradually. 

![10-20]  

Test input size from 10 to 20, we can hardly see time of other ways besides brute force.

![100-1000]  

Test input size from 100 to 1000, we can easily find the difference in rolling array and dp.
For rolling array, it's even better than dynamic programming. Because it doesn't need to generate
2D dp table to store and initialize it which may increase the speed of virtual machine. Using rolling array
can also decrease the use of memory.



## Application
The Knapsack algorithm is used to solve the Knapsack problem, a classic optimization problem that involves selecting a set of items with specific weights and values to maximize the value of items that can be put into a knapsack of a certain weight limit. The algorithm is commonly used in various fields such as resource allocation, portfolio optimization, and cutting stock problem. Here are some specific examples and their corresponding applications:

**Resource Allocation:** The Knapsack algorithm can be used in the allocation of resources such as time, money, and materials in various industries. For example, in the construction industry, the algorithm can be used to allocate materials to different projects to maximize the overall profit. In healthcare, the algorithm can be used to optimize the allocation of medical resources such as staff and equipment to minimize wait times and increase patient satisfaction. (Source: Optimization Methods in Resource Allocation, Springer Link)

**Portfolio Optimization:** The Knapsack algorithm can also be used in finance and investment to optimize the allocation of assets in a portfolio. For example, the algorithm can be used to select a combination of assets such as stocks, bonds, and commodities with specific weights and returns to maximize the overall return on investment while staying within certain constraints such as risk tolerance. (Source: Portfolio Optimization with the Knapsack Problem, Journal of Investment Strategies)

**Cutting Stock Problem:** The Knapsack algorithm is commonly used in manufacturing, specifically in the cutting stock problem, which involves cutting raw materials such as wood or metal into smaller pieces to minimize waste and maximize profit. The algorithm can be used to determine the optimal cutting patterns that result in the most efficient use of raw materials.

In all of these fields, the Knapsack algorithm is useful because it provides an efficient solution to a complex optimization problem. By finding the optimal solution to the problem, the algorithm can help businesses and organizations save time, money, and resources while maximizing profits and efficiency.

## Implementation
The code writing can be found in the following files:

* [BF_code] -- implementation of brute force in 0-1 knapsack problem
* [DP_code] -- implementation of dynamic programming in 0-1 knapsack problem
* [RA_code] -- implementation of rolling array in 0-1 knapsack problem
* [RA_Unbound] -- implementation of rolling array in unbounded knapsack problem

### Using language and libraries:
I use java to implement this algorithm because java is my most proficient programming language. For knapsack problem, brute force method use List and ArrayList while dynamic programming and rolling array only use array. So, I only use java.util library because I need to store result.
### Biggest challenge:
The biggest challenge is to understand the dynamic programming approach which involves breaking down the problem into smaller sub-problems and solving them recursively. This can be a difficult concept to grasp, especially I'm not so familiar with dynamic programming at first. I only learn a little from Fibonacci sequence before. It's important for me to have a solid understanding of the underlying mathematics involved in the Knapsack Problem and its variations, as well as familiarity with programming languages and data structures. For traverse sequence and the nest sequence of loop is also hard to determine. I should draw the dp table by myself in paper every time when I feel confused about the formula.
### Key points of the algorithm
### 0-1 Knapsack Problem
F[i,v] states that for i items, the most value we can achieve by putting them into a knapsack whose capacity is v. The transfer formula show below:
$$ F[i, v] = max[F[i-1, v], F[i-1, v - Ci] + Wi]$$
In this formula, because we only need to decide choose or not for each item. So, when we think about the sub-problem, it becomes the question about current item and the former (i - 1) items. In this situation, if we don't choose current item, the most value we can get is F[i - 1, v]. If we choose current item, Ci for the current item required capacity, the remain capacity will become (v - Ci). Wi for the value that will be added into total revenue and we can get (F[i - 1, v - Ci] + Wi). So, we only need to compare these two option and get the most value in each step.
```text
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
              // so the value is dp[i - 1][j]  
              dp[i][j] = dp[i - 1][j];  
            } else {  
              // when current capacity more than weight of current item  
              // we have 2 option 
              // 1. choose current item: dp[i - 1][j - weight[i]] + value[i] 
              // 2. don't choose current item: dp[i - 1][j]  
              dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);  
            }  
        }  
    }
}
 ```
Based on dynamic programming, we can optimize the space complexity by compressing states. Checking the formula, we can find that if we copy the data in dp[i - 1] level to dp[i] level, the formula can be:  
$$ dp[i][j] = max(dp[i][j], dp[i][j - weight[i]] + value[i])$$

Moreover, we can use a linear array to store the number which is dp[j]. At that time, dp[j] states that the most value that knapsack can get with j capacity.  
$$ dp[j] = max(dp[j], dp[j - weight[i]] + value[i])$$
```text
public void rollingArray(int[] weight, int[] value, int capacity) {  
	int n = weight.length;  
    // dp[j] states that the most value we can get for knapsack with j capacity  
    int[] dp = new int[capacity + 1];  
    // we traverse item first then traverse capacity  
    for (int i = 0; i < n; i++) {  
	    for (int j = capacity; j >= weight[i]; j--) {  
	        dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);  
		}
    }
}
```
### Unbounded Knapsack Problem
For unbounded knapsack problem, we use rolling array directly.
```text
public void rollingArray(int[] weight, int[] value, int capacity) {   
	int[] dp = new int[capacity + 1];  
	// traverse item  
	for (int i = 0; i < weight.length; i++) {  
	    // traverse capacity in ascending order  
	    for (int j = weight[i]; j <= capacity; j++) {  
	        dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);  
	    }  
	}  
}
```
I think dynamic programming problem should define states firstly, then initialize dp table and make sure the traverse order to avoid overlapping the past data. Finally, we can use formula to generate each number in dp table. **I have talked about those conceptions in second part-Analysis of Algorithm.**

## Summary
In conclusion, rolling array are the fastest method in three ways. I think that's because it doesn't need to initialize as many as dynamic programming. Not only can it save the space, but also it decreases the time in running. Although dynamic programming with 2D array and rolling array have same time complexity, they have much difference in realizing. Dynamic programming use 2D array to store the value that has been computed, while rolling array just use a linear array to register whole process which has the rolling influence during whole process.

During the research of knapsack problem, I really learn a lot. I heard about this problem in few months ago. However, I didn't know the mechanism of this algorithm. So, even after finishing the easiest 0-1 knapsack problem, I still use a lot of time to learn about implement in algorithm. I try to change the sequence of nest loop and traverse. Instead of just using this method, I try to understand each item in dp table and know how can I get it. The most meaningful thing I receive from this time homework is that I know a way to improve current programming structure. From the original brute force with exponential time complexity and linear space complexity, I optimize the program to almost linear time complexity and less space complexity. I think that's the magic of algorithm and I get the sense of achievement after optimizing the knapsack program.


## References

1. Wikipedia, "https://en.wikipedia.org/wiki/Knapsack_problem"

2. Google Developers, "https://developers.google.com/optimization/pack/knapsack#:~:text=In%20the%20knapsack%20problem%2C%20you,can%27t%20pack%20them%20all."

3. GeeksforGeeks, "https://www.geeksforgeeks.org/space-optimized-dp-solution-0-1-knapsack-problem/"
4. Application1, "https://journalofcloudcomputing.springeropen.com/articles/10.1186/s13677-017-0075-2"
5. Application2, "https://journals.plos.org/plosone/article?id=10.1371/journal.pone.0213652"
6. Application3, "https://pubsonline.informs.org/doi/10.1287/opre.9.6.849"
7. Levitin, Anany. The Design and Analysis of Algorithms. New Jersey: Pearson Education Inc., 2003.
8. Martello, Silvano; Toth, Paolo (1990). Knapsack problems: Algorithms and computer interpretations. Wiley-Interscience


<!-- auto references -->
[BruteForce]: BruteForce.png
[DP]: DP.png
[Rolling]: RollingArray.png
[10-20]: From10-20.png
[100-1000]: From100-1000.png
[BF_code]: src/ZeroOneKnapsack/BruteForce.java
[DP_code]: src/ZeroOneKnapsack/DP.java
[RA_code]: src/ZeroOneKnapsack/RollingArray.java
[RA_Unbound]: src/RollingArrayUnbounded.java
[Test]: src/Test01KnapSack/generalTest.java
