package pro.kimd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import pro.kimd.R;

import androidx.appcompat.app.AppCompatActivity;

public class plussActivity extends AppCompatActivity {
    EditText edtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pluss);
        edtname=(EditText)findViewById(R.id.edtname);
    }
    public void btnnumberpluss(View view){

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(plussActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT",true);
        startActivity(intent);
    }
}
