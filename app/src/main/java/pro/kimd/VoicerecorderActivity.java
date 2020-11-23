package pro.kimd;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class VoicerecorderActivity extends AppCompatActivity {
    private static final int REQUEST_SCREENSHOT = 59706;
    private MediaProjectionManager mgr;
    int mScreenDensity;
    Intent data;
    int a = 0;
    int min;
    private int resultCodes=0;
    private MediaProjectionManager mProjectionManager;
    int DISPLAY_WIDTH = 720;
    int DISPLAY_HEIGHT = 1280;
    MediaProjection mMediaProjection;

    MediaRecorder mMediaRecorder;
    static final String EXTRA_RESULT_CODE="resultCode";
    static final String EXTRA_RESULT_INTENT="resultIntent";
    SparseIntArray ORIENTATIONS = new SparseIntArray();
    {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }
    VirtualDisplay mVirtualDisplay;
    MediaProjectionCallback mMediaProjectionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicerecorder);
        new Thread(new Runnable() {
            @Override
            public void run() {
                setrquestmeediaprojection();
            }
        }).start();

    }

    @RequiresApi(api = 29)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent dataSS) {

        if (requestCode == REQUEST_SCREENSHOT) {
            Toast.makeText(this, "garanted", Toast.LENGTH_SHORT).show();
//            resultCodes = resultCode;
//            data = dataSS;
//            setScreen();
            Intent intent=new Intent(getApplicationContext(),VoicerecorderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(EXTRA_RESULT_CODE, resultCode)
                    .putExtra(EXTRA_RESULT_INTENT, data)
                    .putExtra("density", mScreenDensity);
            ContextCompat.startForegroundService(getApplicationContext(),intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @RequiresApi(api = 29)
    public void setScreen(){

        mProjectionManager = (MediaProjectionManager) getSystemService
                (Context.MEDIA_PROJECTION_SERVICE);
        initRecorder();
        shareScreen();
    }
    public void btnstoprecord(View view){
        stopService(new Intent(VoicerecorderActivity.this,VoicerecorderActivity.class));
    }
    public void setrquestmeediaprojection() {
        mgr = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);

        startActivityForResult(mgr.createScreenCaptureIntent(),
                REQUEST_SCREENSHOT);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

    }
    private void initRecorder() {
        try {
            mMediaRecorder=new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"468545878548");
            if (!file.exists()){file.mkdirs();}
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
            Date now = new Date();
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/c.mp3");
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
                mMediaProjection = mProjectionManager.getMediaProjection(resultCodes,data);
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
            return mMediaProjection.createVirtualDisplay("voicerecorder",
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
}
