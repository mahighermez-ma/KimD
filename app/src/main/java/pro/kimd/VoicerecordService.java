package pro.kimd;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class VoicerecordService extends Service {
    static final String EXTRA_RESULT_CODE="resultCode";
    static final String EXTRA_RESULT_INTENT="resultIntent";
    private int resultCode=0,mScreenDensity;
    private Intent resultData;
    private static final int REQUEST_SCREENSHOT = 59706;
    private MediaProjectionManager mgr;
    Intent data;
    int a = 0;
    int min;
    private int resultCodes=0;
    private MediaProjectionManager mProjectionManager;
    int DISPLAY_WIDTH = 720;
    int DISPLAY_HEIGHT = 1280;
    MediaProjection mMediaProjection;

    MediaRecorder mMediaRecorder;
    SparseIntArray ORIENTATIONS = new SparseIntArray();
    {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }
    VirtualDisplay mVirtualDisplay;
    MediaProjectionCallback mMediaProjectionCallback;
    public VoicerecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = 29)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "ForegroundServiceChannel")
                .build();
        startForeground(1, notification);
        if (intent!=null){
            resultCode=intent.getIntExtra(EXTRA_RESULT_CODE, 1337);
            resultData=intent.getParcelableExtra(EXTRA_RESULT_INTENT);
            mScreenDensity=intent.getIntExtra("density",1337);
            recored();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "ForegroundServiceChanneler",
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(serviceChannel);


        }
    }


    @Override
    public void onDestroy() {
        try {
            mMediaRecorder.stop();

        }catch (IllegalStateException s){
            Log.e("return232", s.toString());
        }
        mMediaRecorder.reset();
        stopScreenSharing();
        destroyMediaProjection();
        super.onDestroy();
    }

    private void initRecorder() {
        try {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"468545878548");
            if (!file.exists()){file.mkdirs();}
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
            Date now = new Date();
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/c.mp4");
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
            mMediaRecorder.setVideoFrameRate(30);
            mMediaRecorder.prepare();
        } catch (IOException e) {
            Log.e("324", "initRecorder: "+e.toString() );
            e.printStackTrace();
        }
    }
    private void shareScreen() {
        if (mMediaProjection==null){
            try {
                mMediaProjectionCallback = new MediaProjectionCallback();
                mMediaProjection = mProjectionManager.getMediaProjection(resultCode,resultData);
                mMediaProjection.registerCallback(mMediaProjectionCallback, null);
                mVirtualDisplay = createVirtualDisplay();
                mMediaRecorder.start();
            }catch (Exception e){
                Log.e("324", "shareScreen: "+e.toString() );
            }
        }
    }
    private VirtualDisplay createVirtualDisplay() {
        try {
            return mMediaProjection.createVirtualDisplay("Captureservice",
                    DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    mMediaRecorder.getSurface(), null /*Callbacks*/, null
                    /*Handler*/);
        }catch (Exception e){
            Log.e("324", "createVirtualDisplay: " + e.toString() );
            return null;
        }

    }
    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {

            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaProjection = null;
            stopScreenSharing();
        }

    }
    private void stopScreenSharing() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        destroyMediaProjection();
    }

    private void destroyMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection.unregisterCallback(mMediaProjectionCallback);
            mMediaProjectionCallback=null;
            mMediaProjection.stop();
            mMediaProjection = null;
            Runtime.getRuntime().gc();
        }
    }
    @RequiresApi(api = 29)
    public void recored(){
            mMediaRecorder = new MediaRecorder();
            mProjectionManager = (MediaProjectionManager) getSystemService
                    (Context.MEDIA_PROJECTION_SERVICE);
            initRecorder();
            shareScreen();

    }
}
