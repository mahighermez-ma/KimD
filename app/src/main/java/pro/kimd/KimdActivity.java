package pro.kimd;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class KimdActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kimd);


            try {


              //  setContentView(R.layout.main);

                    // permission granted...
                    if (getIntent().getBooleanExtra("EXIT",false)){
                        KimdActivity.this.finish();
                        System.exit(0);
                    }
                    ImageView img=(ImageView)findViewById(R.id.imgkimd);
                    Animation a = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animzoomin);
                    a.reset();
                    img.clearAnimation();
                    img.startAnimation(a);

                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        OwnerDataBaseManager ownerDataBaseManager=new OwnerDataBaseManager(KimdActivity.this);
                        if (ownerDataBaseManager.getowner()==null){
                            if (Settings.canDrawOverlays(KimdActivity.this)) {
                            }else{
                                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                        Uri.parse("package:" + getPackageName()));
                                startActivityForResult(intent, 1);
                            }
                            startActivity(new Intent(KimdActivity.this,MainActivity.class));

                        }else {
                        startActivity(new Intent(KimdActivity.this,ProgramActivity.class));
                        }
                    }
                },3000);
            }catch (Exception e){
                //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

}
