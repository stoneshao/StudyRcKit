package rym.study.rckit.utils;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

public class HttpRequestUtil extends Thread {

    private static final String TAG = "HttpRequestUtil";
    public static final String APP_KEY = "n19jmcy59ocx9";
    public static final String APP_SECRET = "PblLNSx3hSkW";

    private static final String APPKEY = "RC-App-Key";
    private static final String NONCE = "RC-Nonce";
    private static final String TIMESTAMP = "RC-Timestamp";
    private static final String SIGNATURE = "RC-Signature";

    private Handler mHandler;
    private HttpURLConnection mConn;
    private String mBodyContent;

    public HttpRequestUtil(String method, String reqUrl, String bodyContent, Handler handler) throws IOException {
        Log.d(TAG, "HttpRequestUtil method = " + method + ", urlStr = " + reqUrl + ", content = " + bodyContent);
        mBodyContent = bodyContent;
        mHandler = handler;

        // Create HttpURLConnection.
        URL url = new URL(reqUrl);
        mConn = (HttpURLConnection) url.openConnection();
        mConn.setDoOutput(true);
        mConn.setUseCaches(false);
        mConn.setRequestMethod(method);
        mConn.setInstanceFollowRedirects(true);
        mConn.setConnectTimeout(5000);
        mConn.setReadTimeout(5000);

        // Write header.
        String nonce = String.valueOf(Math.random() * 1000000);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = toHex(toSHA1(APP_SECRET + nonce + timestamp));
        mConn.setRequestProperty(APPKEY, APP_KEY);
        mConn.setRequestProperty(NONCE, nonce);
        mConn.setRequestProperty(TIMESTAMP, timestamp);
        mConn.setRequestProperty(SIGNATURE, sign);
        mConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    }

    @Override
    public void run() {
        try {
            // Write content.
            DataOutputStream out = new DataOutputStream(mConn.getOutputStream());
            out.writeBytes(mBodyContent);
            out.flush();
            out.close();

            // Send http request, convert response to String
            InputStream in = mConn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            outStream.close();
            in.close();
            String json = new String(data);

            // Response callback.
            Message msg = Message.obtain();
            msg.obj = json;
            mHandler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mConn.disconnect();
        }
    }

    private byte[] toSHA1(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(value.getBytes());
            return md.digest();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String toHex(byte[] data) {
        final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for (int j = 0; i < l; ++i) {
            out[j++] = DIGITS_LOWER[(0xf0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0f & data[i]];
        }
        return String.valueOf(out);
    }
}
