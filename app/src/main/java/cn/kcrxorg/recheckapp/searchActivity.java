package cn.kcrxorg.recheckapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;

import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.handheld.UHF.UhfManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kcrxorg.recheckapp.bean.OneTwoPackRs;
import cn.kcrxorg.recheckapp.netutil.GetData;
import cn.pda.serialport.Tools;

/**
 * Created by chang on 2017/9/20.
 */

public class searchActivity extends Activity {
    private KeyReceiver keyReceiver;
    private static Toast toast;
    String data;
   String caonima="草拟吗git";
    TextView textscan;
    TextView textbundlenum;

    private Animation animation = null;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 101://包捆消息
                    Data data= (Data) msg.getData().getSerializable("listitem");
                    List listitem = data.getList();
                    SimpleAdapter myAdapter = new SimpleAdapter(searchActivity.this, listitem, R.layout.onetwopach_item,
                            new String[]{"updatetime", "onepackcode", "twopackcode", "bankname", "operatusername", "checkusername", "cashtypename", "cashvouchername"},
                            new int[]{R.id.text_onetwo_updatetime, R.id.text_onepackcode, R.id.text_twopackcode, R.id.text_bankname,
                                    R.id.text_operatusername, R.id.text_checkusername, R.id.text_cashtypename, R.id.text_cashvouchername});
                    ListView listView = (ListView) findViewById(R.id.list_test);
                    listView.setAdapter(myAdapter);

                    textscan.setText("包号:"+((Map)listitem.get(0)).get("twopackcode"));
                    textbundlenum.setText(" 捆数:"+listitem.size());
                    break;
                case 200://未查询到数据
                    textscan.setText("未查询到此包号/捆号");
                    break;
            }
//            if (msg.what == Barcode1DManager.Barcode1D) {
//                String data = msg.getData().getString("data");
//                sortAndadd(listBarcode, data);
//                addListView();
//                eidtBarCount.setText(listBarcode.size() + "");
////				barcodeCount++;
//                Util.play(1, 0);
//            }
        }

        ;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        registerReceiver();
        Util.initSoundPool(this);

        textbundlenum= (TextView) findViewById(R.id.text_bundlenum);
        textscan=(TextView)findViewById(R.id.text_scan);
    }

    private void registerReceiver() {
        keyReceiver = new KeyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.rfid.FUN_KEY");
        registerReceiver(keyReceiver, filter);
    }

    private void unregisterReceiver() {
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
                    toast = Toast.makeText(searchActivity.this, "正在扫描...请对准二维码或封包卡", Toast.LENGTH_SHORT);
                } else {
                    toast.setText("正在扫描...请对准二维码或封包卡");
                }
                toast.show();
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
                        Log.d("search", "开始读取包号");
                        UhfManager uhf = UhfManager.getInstance();
                        uhf.setOutputPower(26);
                        List<byte[]> epcs = uhf.inventoryRealTime();
                        if (epcs.size() > 0)//读到包号开始查询
                        {
                            final String packcode = Tools.Bytes2HexString(epcs.get(0), epcs.get(0).length).substring(0, 8);
                            Log.d("search", packcode);
                            Util.play(1, 0);
                            new Thread() {
                                public void run() {
                                    try {
                                        GetData getData = new GetData();
                                        data = getData.getMsg("http://192.168.2.2:8080/ssm-pdaserver/getOneTwoPackRs?code=" + packcode);
                                        Log.d("search", data);
                                        if (data != null || !data.equals("")) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(data);
                                                int msgcode = jsonObject.getInt("code");
                                                Log.d("search", "msgcode:" + msgcode);
                                                if (msgcode == 100)//收到正常数据
                                                {
                                                    JSONObject jsonObject1 = jsonObject.getJSONObject("extend");
                                                    JSONArray jsonArray = jsonObject1.getJSONArray("oneTwoPackRslist");
                                                    Log.d("search", "jsonArray:" + jsonArray.length());
                                                    List<OneTwoPackRs> oneTwoPackRslist = new ArrayList<OneTwoPackRs>();
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                                                        OneTwoPackRs oneTwoPackRs = new OneTwoPackRs();
                                                        oneTwoPackRs.setUpdatetime(jsonObj.getString("updatetime"));
                                                        oneTwoPackRs.setOnepackcode(jsonObj.getString("onepackcode"));
                                                        oneTwoPackRs.setTwopackcode(jsonObj.getString("twopackcode"));
                                                        oneTwoPackRs.setBankname(jsonObj.getString("bankname"));
                                                        oneTwoPackRs.setOperatusername(jsonObj.getString("operatusername"));
                                                        oneTwoPackRs.setCheckusername(jsonObj.getString("checkusername"));
                                                        oneTwoPackRs.setCashtypename(jsonObj.getString("cashtypename"));
                                                        oneTwoPackRs.setCashvouchername(jsonObj.getString("cashvouchername"));
                                                        oneTwoPackRslist.add(oneTwoPackRs);
                                                    }
                                                    List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
                                                    for (int i = 0; i < oneTwoPackRslist.size(); i++) {
                                                        Map<String, Object> showitem = new HashMap<String, Object>();
                                                        showitem.put("updatetime", oneTwoPackRslist.get(i).getUpdatetime());
                                                        showitem.put("onepackcode", oneTwoPackRslist.get(i).getOnepackcode());
                                                        showitem.put("twopackcode", oneTwoPackRslist.get(i).getTwopackcode());
                                                        showitem.put("bankname", oneTwoPackRslist.get(i).getBankname());
                                                        showitem.put("operatusername", oneTwoPackRslist.get(i).getOperatusername());
                                                        showitem.put("checkusername", oneTwoPackRslist.get(i).getCheckusername());
                                                        showitem.put("cashtypename", oneTwoPackRslist.get(i).getCashtypename());
                                                        showitem.put("cashvouchername", oneTwoPackRslist.get(i).getCashvouchername());
                                                        listitem.add(showitem);
                                                    }
                                                    Data data=new Data();
                                                    data.setList(listitem);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putSerializable("listitem",data);
                                                    Message msg = new Message();
                                                    msg.what = 101;
                                                    msg.setData(bundle);
                                                    mHandler.sendMessage(msg);

                                                } else //返回数据异常,没有查询到数据
                                                {
                                                    Bundle bundle = new Bundle();
                                                    Message msg = new Message();
                                                    msg.what = 200;
                                                    mHandler.sendMessage(msg);
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();

                            break;
                        } else {
                            Log.d("search", "没读到东西");
                        }
                    case KeyEvent.KEYCODE_F5:
                        Log.d("onkeyDown", "++++++++++++++++++++++++" + keyCode);
                        break;
                }
            }

        }


    }
}

