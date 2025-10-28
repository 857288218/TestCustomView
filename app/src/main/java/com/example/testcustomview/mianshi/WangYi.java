package com.example.testcustomview.mianshi;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class WangYi {
    // 有效的括号: 遇到左括号入栈，遇到右括号栈顶出栈，比较括号是否匹配
    public boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }

        HashMap<Character, Character> hashMap = new HashMap<>();
        hashMap.put(')', '(');
        hashMap.put('[', ']');
        hashMap.put('{', '}');

        Deque<Character> stack = new LinkedList<>();
        for (int i=0; i<s.length(); i++) {
            Character character = s.charAt(i);
            if (hashMap.containsKey(character)) {
                Character popLeft = stack.pop();
                if (popLeft != hashMap.get(character)) {
                    return false;
                }
            } else {
                stack.push(character);
            }
        }
        return stack.isEmpty();
    }
}
