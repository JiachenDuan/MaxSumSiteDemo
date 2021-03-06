package com.example.demo.utils;

import com.example.demo.exception.InvalidInputException;
import datastructure.TreeNode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Utils {

    private int maxLen;
    private int maxSum;
    private final String INVALID_BINARY_TREE_ERROR_MESSAGE = "Invalid Serialized Binary Tree. Each node should be separated by , and " +
            "use # as null node. Example: 1,2,#,5,2,#,#,#,6,9,#,1,#,#,7,#,# ";

    /**
     * deserialize binary tree string and look up the max sum
     * Enabled cache, maxSum will return from cache when serializedBinaryTree has been passed in before.
     *
     * @param serializedBinaryTree
     * @return
     * @throws Exception
     */
    @Cacheable(value = "maxsum")
    public int getMaxSumFromSerializedBinaryTree(String serializedBinaryTree) throws Exception {
        if (serializedBinaryTree != null && serializedBinaryTree.length() != 0) {
            //Must contains , and # and only allow 0-9 , and # in the input
            if (!serializedBinaryTree.matches("^(?=.*\\,+)(?=.*\\#+)[0-9#,]*$"))
                throw new InvalidInputException(INVALID_BINARY_TREE_ERROR_MESSAGE);
            TreeNode root = deserialize(serializedBinaryTree);
            return getMaxSum(root);
        }
        throw new InvalidInputException("Serialized Binary Tree passed in is null");
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


    /**
     * Decoded the serialized binary tree data into a Binary Tree structured data
     *
     * @param data
     * @return
     */
    private TreeNode deserialize(String data) throws InvalidInputException {
        Queue<String> queue = new LinkedList<>();
        queue.addAll(Arrays.asList(data.split(",")));
        try {
            TreeNode root = buildTree(queue);
            return root;
        } catch (NullPointerException ex) {
            throw new InvalidInputException(INVALID_BINARY_TREE_ERROR_MESSAGE);
        }

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

}
