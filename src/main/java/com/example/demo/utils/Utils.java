package com.example.demo.utils;

import datastructure.TreeNode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Utils {

  private int maxLen;
  private int maxSum;

  /**
   * deserialize binary tree string and look up the max sum
   *
   * @param serializedBinaryTree
   * @return
   * @throws Exception
   */
  public int getMaxSumFromSerializedBinaryTree(String serializedBinaryTree) throws Exception {
    if (serializedBinaryTree != null && serializedBinaryTree.length() != 0) {
      return getMaxSum(deserialize(serializedBinaryTree));
    }
    throw new Exception("Serialized Binary Tree passed in is null");
  }

  /**
   * Look up the max sum of a Binary Tree Node
   *
   * @param root
   * @return
   */
  private int getMaxSum(TreeNode root) {
    if (root == null) return 0;
    maxSum = Integer.MIN_VALUE;
    maxLen = 0;
    maxSumHelper(root, 0, 0);
    return maxSum;
  }

  private void maxSumHelper(TreeNode root, int sum, int len) {

    if (root == null) {
      if (maxLen < len) {
        maxLen = len;
        maxSum = sum;
      } else if (maxLen == len && maxSum < sum) maxSum = sum;
      return;
    }

    maxSumHelper(root.left, sum + root.val, len + 1);
    maxSumHelper(root.right, sum + root.val, len + 1);
  }

  private String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    buildString(root, sb);
    return sb.toString();
  }

  private void buildString(TreeNode root, StringBuilder sb) {

    if (root == null) {
      sb.append("#").append(" ");
      return;
    }
    sb.append(root.val).append(" ");
    buildString(root.left, sb);
    buildString(root.right, sb);
  }

  /**
   * Decoded the serialized binary tree data into a Binary Tree structured data
   *
   * @param data
   * @return
   */
  private TreeNode deserialize(String data) {
    Queue<String> queue = new LinkedList<>();
    queue.addAll(Arrays.asList(data.split(" ")));
    return buildTree(queue);
  }

  /**
   * Build binary tree
   *
   * @param queue
   * @return
   */
  private TreeNode buildTree(Queue<String> queue) {
    String val = queue.poll();
    if (val.equals("#")) return null;
    TreeNode root = new TreeNode(Integer.valueOf(val));
    root.left = buildTree(queue);
    root.right = buildTree(queue);
    return root;
  }

  public static void main(String[] args) {
    String str = "1,2,3,4,5,null,6,7,null,null,null,null,8";
    /**
     * 1 / \ 2 3 \ / \ 5 6 7 / 1
     *
     * <p>serialized: 1 2 # 5 1 # # # 3 6 # # 7 # #
     */
    Utils u = new Utils();
    TreeNode node = new TreeNode(1);
    node.left = new TreeNode(2);
    node.left.right = new TreeNode(5);
    node.left.right.left = new TreeNode(1);
    node.right = new TreeNode(3);
    node.right.left = new TreeNode(6);
    node.right.right = new TreeNode(7);
    String secrilized = u.serialize(node);
    System.out.println(secrilized);
  }
}
