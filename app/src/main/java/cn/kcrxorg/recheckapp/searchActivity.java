package cn.kcrxorg.recheckapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.handheld.UHF.UhfManager;

import java.util.List;

import cn.pda.serialport.Tools;

/**
 * Created by chang on 2017/9/20.
 */

public class searchActivity extends Activity {
    private KeyReceiver keyReceiver;
    private static Toast toast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        registerReceiver();
        init();
    }

    private void init() {
            }
    private void registerReceiver() {
        keyReceiver = new KeyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.rfid.FUN_KEY");
        registerReceiver(keyReceiver , filter);
    }
    private void unregisterReceiver(){
        unregisterReceiver(keyReceiver);
    }
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    private class KeyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int keyCode = intent.getIntExtra("keyCode", 0);
            if (keyCode == 0) {
                keyCode = intent.getIntExtra("keycode", 0);
            }
            boolean keyDown = intent.getBooleanExtra("keydown", false);
            if (keyDown) {
                if (toast == null) {
                    toast = Toast.makeText(searchActivity.this, "KeyReceiver:keyCode = " + keyCode, Toast.LENGTH_SHORT);
                } else {
                    toast.setText("KeyReceiver:keyCode = " + keyCode);
                }
                toast.show();

                }
            switch (keyCode) {
                case KeyEvent.KEYCODE_F1:
                    Log.d("search", "++++++++++++++++++++++++" + keyCode);
                    break;
                case KeyEvent.KEYCODE_F2:
                    Log.d("search", "++++++++++++++++++++++++" + keyCode);
                    break;
                case KeyEvent.KEYCODE_F3:
                    Log.d("search", "++++++++++++++++++++++++" + keyCode);
                    break;
                case KeyEvent.KEYCODE_F4:
                    Log.d("search", "++++++++++++++++++++++++" + keyCode);
                    UhfManager uhf=UhfManager.getInstance();;

                    List<byte[]> epcs = uhf.inventoryRealTime();
                    if(epcs.size()>0) {
                        Log.d("search", Tools.Bytes2HexString(epcs.get(0),epcs.get(0).length));
                    }else
                    {
                        Log.d("search", "没读到东西");
                    }

                    break;
                case KeyEvent.KEYCODE_F5:
                    Log.d("onkeyDown", "++++++++++++++++++++++++" + keyCode);
                    break;
            }
            }


        }
    }

