package com.com.mr_wrong.VolleyAndGson;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by Mr_Wrong on 2015/5/16.
 */
public class GsonRequest<T> extends Request<T> {
    private Gson mGson;
    private final Response.Listener<T> mListener;
    private Class<T> mClass;

    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener reeorlistener) {
        super(method, url, reeorlistener);
        mGson = new Gson();
        mClass = clazz;
        mListener = listener;
    }

    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorlistener) {
        this(Method.GET, url, clazz, listener, errorlistener);

    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(mGson.fromJson(jsonString, mClass), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected void deliverResponse(T t) {
        mListener.onResponse(t);
    }
}
