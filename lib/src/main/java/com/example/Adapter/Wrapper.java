package com.example.Adapter;

/**
 * Created by Mr_Wrong on 15/11/11.
 * 对象的适配器模式
 */
public class Wrapper implements Targetable {
    private Source mSource;

    public Wrapper(Source source) {
        this.mSource = source;
    }
    @Override
    public void method2() {
        mSource.method1();
    }
}
