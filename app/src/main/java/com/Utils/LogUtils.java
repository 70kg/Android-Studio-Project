package com.Utils;

import android.util.Log;

/**
 * Created by Mr_Wrong on 15/6/11.
 */
public class LogUtils {
    public final static String TAG = "70kg";
    public final static String MATCH = "%s->%s->%d";
    public final static String CONNECTOR = ":<--->:";

    public final static boolean SWITCH = true;

    public static String buildHeader() {
        StackTraceElement stack = Thread.currentThread().getStackTrace()[4];

        return String.format(MATCH, stack.getClassName(), stack.getMethodName(),
                stack.getLineNumber()) + CONNECTOR;
    }

    public static String bb() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        int index = 4;
        String className = stackTrace[index].getFileName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();

        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ");

        return stringBuilder.toString();
    }

    public static void v(Object msg) {
        if (SWITCH) {
            Log.v(TAG, buildHeader() + msg.toString());
        }
    }

    public static void d(Object msg) {
        if (SWITCH) {
            Log.d(TAG, buildHeader() + msg.toString());
        }
    }

    public static void i(Object msg) {
        if (SWITCH) {
            Log.i(TAG, buildHeader() + msg.toString());
        }
    }

    public static void w(Object msg) {
        if (SWITCH) {
            Log.w(TAG, buildHeader() + msg.toString());
        }
    }

    public static void e(Object msg) {
        if (SWITCH) {
            Log.e(TAG, bb() + msg.toString());
        }
    }
}
