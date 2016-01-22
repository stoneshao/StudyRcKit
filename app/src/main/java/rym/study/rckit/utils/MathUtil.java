package rym.study.rckit.utils;

/**
 * Created by zhanglei on 1/22/16.
 */
public class MathUtil {

    public static int getHashInt(String value, int max) {
        byte[] data = value.getBytes();
        int hash = 0;
        for (byte b : data)
        {
            hash = hash * 131 +  b;
        }
        return (hash & 0xff) % max;
    }
}
