package com.huihui.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Ucache;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by gavin
 * Time 2017/7/19  15:19
 * Email:molu_clown@163.com
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static String encodeData(String data) throws UnsupportedEncodingException {
        //进行BASE64编码,URL_SAFE/NO_WRAP/NO_PADDING
        return new String(Base64.encode(data.getBytes("utf-8"), Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING), "utf-8");
    }

    private static CrashHandler mCrashHandler = new CrashHandler();

    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    private Context context;

    private String TAG = this.getClass().getSimpleName();


    /****
     * 当程序中唯有捕获的异常，系统将会自动调用  uncaughtException
     * @param t  出现未捕获异常的线程
     * @param e  未捕获的异常
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {


        long millis = System.currentTimeMillis();

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(millis));

        PackageManager packageManager = context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);


            CrashMessageBean messageBean = new CrashMessageBean();

            messageBean.setTime(time);

            messageBean.setVersion(packageInfo.versionName);

            messageBean.setAndroidVersion(Build.VERSION.RELEASE + "" + Build.VERSION.SDK_INT);

            messageBean.setVendor(Build.MANUFACTURER);

            messageBean.setModel(Build.MODEL);

            messageBean.setCupAbi(Build.CPU_ABI);

           // messageBean.setErrorMes(e.getMessage()+e.toString());


            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);

            e.printStackTrace(printWriter);

            printWriter.close();
            messageBean.setErrorMes("新的错误："+result.toString());



            Ucache ucache = (Ucache) MobAPI.getAPI(Ucache.NAME);

            String json = JSON.toJSONString(messageBean);

            Log.e(TAG, "json:" + json);


            ucache.queryUcachePut("crash", encodeData(String.valueOf(millis)), encodeData(json), new APICallback() {
                @Override
                public void onSuccess(API api, int i, Map<String, Object> map) {

                    Log.e(TAG, "onSuccess:");
                }

                @Override
                public void onError(API api, int i, Throwable throwable) {
                    Log.e(TAG, "onError:");
                }
            });


        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        e.printStackTrace();

        if (mUncaughtExceptionHandler!=null){

            mUncaughtExceptionHandler.uncaughtException(t,e);
        }else {

            Process.killProcess(Process.myPid());
        }


    }

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {

        return mCrashHandler;
    }


    public void init(Context context) {

        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(this);

        this.context = context.getApplicationContext();
    }
}
