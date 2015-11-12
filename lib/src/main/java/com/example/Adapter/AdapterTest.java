package com.example.Adapter;

/**
 * Created by Mr_Wrong on 15/11/11.
 */
public class AdapterTest {
    public static void main(String[] args) {

        Targetable targetable = new Adapter();
        targetable.method2();

        Source source = new Source();
        Targetable table = new Wrapper(source);
        table.method2();
    }
}
