package com.example.testcustomview.mianshi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Baidu {

    // 百度贴吧一面：二叉树层序遍历、合并两个有序链表
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root); // 根节点入队

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // 当前层的节点数
            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll(); // 出队
                currentLevel.add(node.val);   // 访问节点值

                // 子节点入队（先左后右）
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(currentLevel); // 添加当前层结果
        }
        return result;
    }

    // 百度贴吧二面：HashMap<String, Object>转换成json串，Object可能是基本类型、ArrayList、HashMap
    public static String convertToJson(HashMap<String, Object> map) {
        JSONObject jsonObject = convertMapToJsonObject(map);
        return jsonObject.toString();
    }

    private static JSONObject convertMapToJsonObject(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            try {
                if (value instanceof Map) {
                    // 处理嵌套的 Map
                    jsonObject.put(key, convertMapToJsonObject((Map<String, Object>) value));
                } else if (value instanceof List) {
                    // 处理 List
                    jsonObject.put(key, convertListToJsonArray((List<Object>) value));
                } else {
                    // 处理基本类型和 String
                    jsonObject.put(key, value);
                }
            } catch (Exception e) {

            }
        }
        return jsonObject;
    }

    private static JSONArray convertListToJsonArray(List<Object> list) {
        JSONArray jsonArray = new JSONArray();
        for (Object item : list) {
            if (item instanceof Map) {
                // 处理 List 中的 Map
                jsonArray.put(convertMapToJsonObject((Map<String, Object>) item));
            } else if (item instanceof List) {
                // 处理 List 中的 List
                jsonArray.put(convertListToJsonArray((List<Object>) item));
            } else {
                // 处理基本类型和 String
                jsonArray.put(item);
            }
        }
        return jsonArray;
    }
}
