package test;

import android.util.Log;

public class Child<T extends Number> extends Parent<T> {

    @Override
    public void func(T t) {
        Log.d("renjunqingT", "泛型测试多态: child");
    }
}
