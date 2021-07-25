package com.wroten.demo.leetcode;

public class TwoSumSolution {

    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 2, 3};
        int target = 4;
        int[] ret = twoSum(nums, target);
        if (ret[0] + ret[1] < 1) {
            System.out.println("No result!");
        } else {
            System.out.println("ret = " + "[" + ret[0] + " , " + ret[1] + "]");
        }
    }

    public static int[] twoSum(int[] nums, int target) {
        if (nums.length < 2) {
            System.out.println("ERROR" + "最少给定两个整数");
        }
        int i;
        int j;
        int len = nums.length;
        for (i = 0; i < len - 1; i++) {
            for (j = i + 1; j < len; j++) {
                if (nums[i] + nums[j] == target) {
                    System.out.println(String.valueOf(nums[i]) + " + " + String.valueOf(nums[j]) + " = " + target);
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
}
