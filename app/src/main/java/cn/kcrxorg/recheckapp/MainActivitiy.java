package cn.kcrxorg.recheckapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by chang on 2017/9/18.
 */

public class MainActivitiy extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }

    private void init() {
        Button selectButton= (Button) findViewById(R.id.btn_search);
        selectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
           switch (v.getId())
           {
               case R.id.btn_search:
                   Intent searchintent=new Intent(this,searchActivity.class);
                 //  Intent searchintent=new Intent(this,Barcode1DActivity.class);
                   startActivity( searchintent);
                   break;
           }
    }
    long exitSytemTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //两次退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitSytemTime > 2000) {
                Toast.makeText(getApplicationContext(), R.string.exitSystem,
                        Toast.LENGTH_SHORT).show();
                exitSytemTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
