package bean;

import android.util.Log;

public class Parent<T> {
    public void func(T t){
        Log.d("renjunqingT", "泛型测试多态: parent");
    }
}

