package shop.cn.edu.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;

import shop.cn.edu.com.shop.MainActivity;
import shop.cn.edu.com.shop.R;

public class StartPageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                Intent intent=new Intent(StartPageActivity.this,MainActivity.class);
                 startActivity(intent);
             }
         }, 3000);
    }

}
