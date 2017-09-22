package cn.kcrxorg.recheckapp.netutil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.kcrxorg.recheckapp.bean.Msg;

/**
 * Created by chang on 2017/9/21.
 */

public class GetData {
    public String getMsg(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("请求url失败");
        }
        InputStream inStream = conn.getInputStream();
        byte[] bt = StreamTool.read(inStream);
        inStream.close();
        String rsstr=new String(bt,"UTF-8");
        return rsstr;
    }

}
