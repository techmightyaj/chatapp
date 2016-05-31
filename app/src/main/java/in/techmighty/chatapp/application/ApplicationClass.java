package in.techmighty.chatapp.application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;

import in.techmighty.chatapp.custom.LruBitmapCache;

/**
 * Created by ankit varia on 30/05/16.
 */
public class ApplicationClass extends Application {

    public static final String TAG = ApplicationClass.class.getSimpleName();
    public static int VOLLEY_TIMEOUT = 10 * 1000; // 10 Sec.
    public static int VOLLEY_NUMBER_OF_ATTEMPTS = 2;
    public static float VOLLEY_BACK_OF_MULTIPLIER = 2;
    private static ApplicationClass mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    public static synchronized ApplicationClass getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Stetho.initializeWithDefaults(this);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        mRequestQueue.getCache().clear();
        return mRequestQueue;
    }

    public void clearCache() {

        mRequestQueue.getCache().clear();

    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {

        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
