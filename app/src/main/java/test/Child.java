package test;

import android.util.Log;

import java.io.Serializable;

public class Child<T extends Number> extends Parent<T> implements Serializable {

    @Override
    public void func(T t) {
        Log.d("renjunqingT", "泛型测试多态: child");
    }
}
