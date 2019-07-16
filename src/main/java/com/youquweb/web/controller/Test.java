package com.youquweb.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        int times = 100000;
        long profit = 0;
        Test test = new Test();
        //情况1
        for(int i=0;i<times;i++){
            List<Integer> list = test.init();
            //user pick
            Random random = new Random();
            int userPick = random.nextInt(4);
            int userPickValue = list.get(userPick);
            if(userPickValue == 1){
                profit += 1000000;
            }
            //
            list.remove(userPick);
            //此时数组剩3个
        }
        System.out.println(profit);
        System.out.println(times);
        System.out.println(profit/times);
        profit = 0;
        //情况2
        for(int i=0;i<times;i++){
            List<Integer> list = test.init();
            //user pick
            Random random = new Random();
            int firstPick = random.nextInt(4);
            int firstPickValue = list.get(firstPick);
            list.remove(firstPick);
            //不重新选
            if(firstPickValue == 1){
                profit += 1000000;
            }
            //系统从剩下三个数中选出一个是0的
            int systemPick = random.nextInt(3);
            int systemPickValue = list.get(systemPick);
            //重新选,此时数组剩2个


            //

        }
        System.out.println(profit);
        System.out.println(times);
        System.out.println(profit/times);
    }

    /**
     * 随机生成长度为4的列表，并且只有一个是1，其他位都是0
     * @return
     */
    public List<Integer> init(){
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        int trueIndex = random.nextInt(4);
        for(int i=0;i<4;i++){
            if(i == trueIndex){
                list.add(1);
            }else{
                list.add(0);
            }
        }

        return list;
    }

    public int firstMissingPositive(int[] nums) {
        if(nums == null || nums.length == 0){
            return 1;
        }
        int len = nums.length;
        int[] check = new int[len];
        for(int i=0;i<nums.length;i++){
            if(nums[i] <= len && nums[i] >= 1){
                check[nums[i]-1] = 1;
            }
        }
        for(int i=0;i<check.length;i++){
            if(check[i] == 0){
                return i+1;
            }
        }
        return len+1;
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list1 = new ArrayList<List<Integer>>();
        list1.add(new ArrayList<Integer>());
        for(int num:nums){
            int size=list1.size(); //必须定义在内循环前
            for(int j=0;j<size;j++){
                List<Integer> temp = new ArrayList<>(list1.get(j));
                temp.add(num);
                list1.add(temp);
            }
        }
        return list1;
    }

//    public List<List<Integer>> subsets(int[] nums) {
//        //利用二进制
//        List<List<Integer>> result = new ArrayList<>();
//        if(nums == null || nums.length == 0){
//            return result;
//        }
//        int resultNum = (int)Math.pow(2,nums.length);
//        for(int i=0;i<resultNum;i++){
//            String oldBinary = Integer.toBinaryString(i);
//            String binary = fix(oldBinary, nums.length);
//            List<Integer> temp = new ArrayList<>();
//            for(int j=0;j<binary.length();j++){
//                if(binary.charAt(j) == '1'){
//                    temp.add(nums[j]);
//                }
//            }
//            result.add(temp);
//        }
//        return result;
//    }
//
//    private String fix(String s, int resultNum) {
//        if(s.length() != resultNum){
//            int num = resultNum-s.length();
//            for(int i=0;i<num;i++){
//                s = "0"+s;
//            }
//        }
//        return s;
//    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums == null || nums.length == 0){
            return result;
        }
        List<List<Integer>> temp = new ArrayList<>();
        permuteCore(nums,temp,0);
        result.addAll(temp);
        return result;
    }

    public void permuteCore(int[] nums,List<List<Integer>> temp,int index){
        if(nums == null || nums.length == 0){
            return;
        }
        if(index == nums.length - 1){
            List<Integer> t = new ArrayList<>();
            for(int i=0;i<nums.length;i++){
                t.add(nums[i]);
            }
            temp.add(t);
        }else{
            for(int i=index;i<nums.length;i++){
                swap(nums,i,index);
                permuteCore(nums,temp,index+1);
                swap(nums,index,i);
            }
        }
    }

    public void swap(int[] nums,int i,int j){
        if(i != j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    /**
     * 示例 1:

     输入: "abcabcbb"
     输出: 3
     解释: 无重复字符的最长子串是 "abc"，其长度为 3。
     示例 2:

     输入: "bbbbb"
     输出: 1
     解释: 无重复字符的最长子串是 "b"，其长度为 1。
     示例 3:

     输入: "pwwkew"
     输出: 3
     解释: 无重复字符的最长子串是 "wke"，其长度为 3。
     请注意，答案必须是一个子串，"pwke" 是一个子序列 而不是子串。
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if(s == null||s.length()==0){
            return -1;
        }
        String longestString = "";
        String curLongString = "";
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            //和之前的重复了
            if(curLongString.contains(String.valueOf(c))){
                int repeatIndex = curLongString.indexOf(c);
                String prefix = builder.substring(repeatIndex+1,builder.length());
                builder = new StringBuilder();
                builder.append(prefix).append(c);
                curLongString = builder.toString();
            }else{
                builder.append(c);
                curLongString = builder.toString();
                longestString = curLongString.length()>longestString.length()?curLongString:longestString;
            }
        }
        System.out.println(longestString);
        return longestString.length();
    }

    public int missingNumber(int[] nums) {
        if(nums == null||nums.length ==0){
            return 0;
        }
        boolean[] numbers = new boolean[nums.length+1];
        for(int i=0;i<nums.length;i++){
            numbers[nums[i]] = true;
        }
        for(int i=0;i<numbers.length;i++){
            if(!numbers[i]){
                return i;
            }
        }
        return 0;
    }

    public int rob1(int[] nums){
        int[] profits = new int[nums.length];
        profits[0] = nums[0];
        profits[1] = nums[0]>nums[1]?nums[0]:nums[1];
        for(int i=2;i<nums.length;i++){
            profits[i] = Math.max(profits[i-2]+nums[i],profits[i-1]);
        }
        return profits[nums.length-1];
    }

    public int rob(int[] nums) {
        //打劫到第i-1家时的最大收益与打劫到第i-2家的最大收益加上打劫第i家获得的金钱(nums[i])
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length == 1){
            return nums[0];
        }
        int maxProfit = 0;
        int lastProfit = 0;
        for(int i=0;i<nums.length;i++){
            int temp = maxProfit;
            maxProfit = Math.max(maxProfit,lastProfit+nums[i]);
            lastProfit = temp;
        }
        return maxProfit;
    }
}
