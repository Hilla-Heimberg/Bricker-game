// USER_NAME = hilla_heimberg
// ID - 208916221

package algo_questions;

import java.util.Arrays;

/**
 * Class which contains solutions for algorithms questions.
 * @author Hilla Heimberg
 */
public class Solutions {
    // -------------------------------------- METHODS --------------------------------------

    /**
     * Constructor. An empty one.
     */
    public Solutions() { }

    /**
     * Method computing the maximal amount of tasks out of n tasks that can be completed with m time slots.
     * A task can only be completed in a time slot if the length of the time slot is grater than the no.
     * of hours needed to complete the task.
     * @param tasks array of integers of length n. tasks[i] is the time in hours required to complete task i.
     * @param timeSlots array of integers of length m. timeSlots[i] is the length in hours of the slot i.
     * @return maximal amount of tasks that can be completed
     */
    public static int alotStudyTime(int[] tasks, int[] timeSlots) {
        // first - we will sort the arrays
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);

        int maxNumOfTasks = 0;
        // second - we will try to enter tasks to the "timeSlots" array - (greedy design)
        int indInArray = 0;
        for (int task : tasks) {
            while (indInArray < timeSlots.length) {
                if (task <= timeSlots[indInArray]) {
                    maxNumOfTasks++;
                    indInArray++;
                    break;
                }

                // if we are in the final slot in the timeSlot array - we will finish the algorithm
                if (indInArray == timeSlots.length - 1) {
                    return maxNumOfTasks;
                }
                indInArray++;
            }
        }
        return maxNumOfTasks;
    }

    /**
     * Method computing the min amount of leaps a frog needs to jump across n waterlily leaves, from leaf 1
     * to leaf n. The leaves vary in size and how stable they are, so some leaves allow larger leaps than
     * others. leapNum[i] is an integer telling you how many leaves ahead you can jump from leaf i.
     * If leapNum[3]=4, the frog can jump from leaf 3, and land on any of the leaves 4, 5, 6 or 7.
     * @param leapNum array of ints. leapNum[i] is how many leaves ahead you can jump from leaf i.
     * @return minimal no. of leaps to last leaf.
     */
    public static int minLeap(int[] leapNum) {
        int numOfJumps = 0, curJump = 1, curFarthestLeaf = 1;

        int amountOfLeaves = leapNum.length;
        if (amountOfLeaves == 1) { // edge case- there is only one leaf in the array -> we can't jump
            return 0;
        }

        int i = 0;
        while (i < amountOfLeaves - 1) {
            if (i + leapNum[i] >= amountOfLeaves-1) { //  if this jump will take us to the end
                return numOfJumps + 1;
            }
            int maximumOfJumps = i + 1;
            for (int j = curFarthestLeaf; j < amountOfLeaves && j <= i + leapNum[i]; j++){
                int candidateForMax = j + leapNum[j];
                if (candidateForMax >= maximumOfJumps){ // if there is better option of jump
                    maximumOfJumps = candidateForMax; // - we will update the maximumOfJumps - (greedy design)
                    curJump = j;
                }
            }

            // we will update the loop to started from the farthest leaf we need to check- decreases run time
            curFarthestLeaf = i + leapNum[i];
            i = curJump;
            numOfJumps++;
        }
        return numOfJumps;
    }

    /**
     * Method computing the solution to the following problem: A boy is filling the water trough for his
     * father's cows in their village. The trough holds n liters of water. With every trip to the village
     * well, he can return using either the 2 bucket yoke, or simply with a single bucket. A bucket holds 1
     * liter. In how many different ways can he fill the water trough? n can be assumed to be greater or equal
     * to 0, less than or equal to 48.
     * @param n liter of water
     * @return valid output of algorithm.
     */
    public static int bucketWalk(int n){
        // one way to fill 0 liter in the trough- "in an empty way".
        // one way to fill 1 liter in the trough - with just 1 bucket.
        if (n <= 0 || n == 1) {
            return 1;
        }

        // for fill more than two liter in the trough - the numbers of ways to do that is in accordance to
        // the ways before - Fibonacci - (dynamic programming)
        int nMinusOne = 1, nMinusTwo = 1;
        int numOfWays = 0;
        for (int index = 2; index <= n; index++){
            numOfWays = nMinusOne + nMinusTwo;
            nMinusTwo = nMinusOne;
            nMinusOne = numOfWays;
        }
        return numOfWays;
    }

    /**
     * Method computing the solution to the following problem: Given an integer n, return the number of
     * structurally unique BST's (binary search trees) which has exactly n nodes of unique values from 1 to n.
     * You can assume n is at least 1 and at most 19. (Definition: two trees S and T are structurally distinct
     * if one can not be obtained from the other by renaming of the nodes.) (credit: LeetCode)
     * @param n n nodes
     * @return valid output of algorithm.
     */
    public static int numTrees(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        int[] numOfTrees = new int[n + 1];
        numOfTrees[0] = numOfTrees[1] = 1;

        // for more than 2 nodes and the sub left tree not empty - the calculation will be: multiply
        // between the options for the sub left tree and the options for the sub right tree
        for (int i = 2; i < n + 1; i++) {
            for (int j = 0; j < i; j++) {
                numOfTrees[i] += numOfTrees[j] * numOfTrees[i - j - 1]; // Catalan - (dynamic programming)
            }
        }

        return numOfTrees[n];
    }
}
