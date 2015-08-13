package com.com.mr_wrong.HandleImageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Mr_Wrong on 15/8/13.
 */
public class ImageLoader {

    private LruCache<String, Bitmap> mLruCache;

    private ExecutorService mThreadPool;//线程池

    private int mThreadCount = 1;//默认线程池1

    private Type mType = Type.LIFO;


    public enum Type {
        FIFO, LIFO
    }

    private LinkedList<Runnable> mTasks;//任务队列

    private Thread mPoolThread;//轮询线程

    private Handler mPoolThreadHandler;

    private Handler mHandler;//主线程的handler 设置图片

    //信号量  防止mPoolThreadHandler没有初始化
    private volatile Semaphore mSemaphore = new Semaphore(1);

    //信号量 我也不懂
    private volatile Semaphore mPoolSemaphore;

    private static ImageLoader mInstance;

    //获取单例
    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(1, Type.LIFO);
                }
            }
        }
        return mInstance;
    }
    public static ImageLoader getInstance(int threadCount, Type type)
    {

        if (mInstance == null)
        {
            synchronized (ImageLoader.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ImageLoader(threadCount, type);
                }
            }
        }
        return mInstance;
    }
    //构造
    private ImageLoader(int threadcount, Type type) {
        init(threadcount, type);
    }

    private void init(int threadcount, Type type) {
        mPoolThread = new Thread() {
            @Override
            public void run() {
                try {
                    //请求信号量
                    mSemaphore.acquire();
                } catch (InterruptedException e) {
                }
                Looper.prepare();

                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());

                        try {
                            mPoolSemaphore.acquire();
                        } catch (InterruptedException e) {
                        }
                    }
                };
                mSemaphore.release();

                Looper.loop();
            }
        };
        mPoolThread.start();


        //获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();

        int cacheSize = maxMemory / 8;

        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };


        mThreadPool = Executors.newFixedThreadPool(threadcount);
        mPoolSemaphore = new Semaphore(threadcount);
        mTasks = new LinkedList<Runnable>();
        mType = type == null ? Type.LIFO : type;
    }

    /**
     * 取出一个任务
     *
     * @return
     */
    private synchronized Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTasks.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTasks.removeLast();
        }
        return null;
    }

    /**
     * 加载图片
     *
     * @param path
     * @param imageView
     */
    public void loadImage(final String path, final ImageView imageView) {

        imageView.setTag(path);

        if (mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    ImageView imageView = holder.imageView;
                    Bitmap bm = holder.bitmap;
                    String path = holder.path;

                    if (imageView.getTag().toString().equals(path)) {
                        imageView.setImageBitmap(bm);
                    }
                }
            };
        }

        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null) {
            ImgBeanHolder holder = new ImgBeanHolder();
            holder.bitmap = bm;
            holder.imageView = imageView;
            holder.path = path;
            Message message = Message.obtain();
            message.obj = holder;
            mHandler.sendMessage(message);
        } else {
            addTask(new Runnable() {

                @Override
                public void run() {
                    ImageSize imageSize = getImageViewWidth(imageView);

                    int reqwidth = imageSize.width;
                    int reqheight = imageSize.height;

                    Bitmap bm = decodeSampledBitmapFromResource(path,reqwidth,reqheight);

                    addBitmapToLruCache(path,bm);

                    ImgBeanHolder holder = new ImgBeanHolder();
                    holder.bitmap = bm;
                    holder.imageView = imageView;
                    holder.path = path;
                    Message message = Message.obtain();
                    message.obj = holder;
                    mHandler.sendMessage(message);
                }
            });
        }
    }

    /**
     * 添加一个任务
     *
     * @param runnable
     */
    private void addTask(Runnable runnable) {
        if (mPoolThreadHandler == null) {
            try {
                mSemaphore.acquire();
            } catch (InterruptedException e) {
            }
            mTasks.add(runnable);
            mPoolThreadHandler.sendEmptyMessage(0x110);
        }
    }

    /**
     * 从缓存中获取
     *
     * @param path
     * @return
     */
    private Bitmap getBitmapFromLruCache(String path) {
        return mLruCache.get(path);
    }

    ;

    /**
     * 向缓存中添加
     *
     * @param key
     * @param bm
     */
    private void addBitmapToLruCache(String key, Bitmap bm) {
        if (getBitmapFromLruCache(key) == null) {
            if (bm != null) {
                mLruCache.put(key, bm);
            }
        }
    }

    private class ImgBeanHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }

    private class ImageSize {
        int width;
        int height;
    }

    private ImageSize getImageViewWidth(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        final DisplayMetrics displayMetrics = imageView.getContext()
                .getResources().getDisplayMetrics();
        final ViewGroup.LayoutParams params = imageView.getLayoutParams();

        int width = params.width == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getWidth(); // Get actual image width
        if (width <= 0)
            width = params.width; // Get layout width parameter
        if (width <= 0)
            width = getImageViewFieldValue(imageView, "mMaxWidth"); // Check
        // maxWidth
        // parameter
        if (width <= 0)
            width = displayMetrics.widthPixels;
        int height = params.height == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getHeight(); // Get actual image height
        if (height <= 0)
            height = params.height; // Get layout height parameter
        if (height <= 0)
            height = getImageViewFieldValue(imageView, "mMaxHeight"); // Check
        // maxHeight
        // parameter
        if (height <= 0)
            height = displayMetrics.heightPixels;
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;

    }

    /**
     * 反射获得ImageView设置的最大宽度和高度
     *
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;

                Log.e("TAG", value + "");
            }
        } catch (Exception e) {
        }
        return value;
    }

    private int calculateInSampleSize(BitmapFactory.Options op,
                                      int reqwidth, int reqheight) {

        int width = op.outWidth;
        int height = op.outHeight;

        int insamlesize = 1;

        if (width > reqwidth && height > reqheight) {
            int widthRatio = Math.round((float) width / (float) reqwidth);
            int heightRatio = Math.round((float) reqheight / (float) reqheight);
            insamlesize = Math.max(widthRatio, heightRatio);
        }
        return insamlesize;
    }

    /**
     * 压缩图片
     *
     * @param path
     * @param reqwidth
     * @param reqheight
     * @return
     */
    private Bitmap decodeSampledBitmapFromResource(String path,
                                                   int reqwidth, int reqheight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqwidth, reqheight);

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

}
