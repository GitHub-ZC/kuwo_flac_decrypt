package kuwo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class flac
{
    private static String first = "ylzsxkwm";
    
    protected static String downloadUrl(String id) {
        try {
            URL url = new URL("http://nmobi.kuwo.cn/mobi.s?f=kuwo&q=" + encode(id));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            String message = "";
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                byte[] data = new byte[1024];
                StringBuffer sb1 = new StringBuffer();
                int length = 0;
                while ((length = inputStream.read(data)) != -1) {
                    String s = new String(data, 0,length);
                    sb1.append(s);
                }
                message=sb1.toString();
                inputStream.close();
            }
            conn.disconnect();
//            System.out.println(new String(message.getBytes(), "utf-8"));
            return message;
        }catch(Exception e) {
            return "{\"code\": \"request error\", \"message\": \"kuwo request error\"}";
        }
    }
    
    private static String encode(String id) {
        String s = "user=e3cc098fd4c59ce2&android_id=e3cc098fd4c59ce2&prod=kwplayer_ar_9.3.1.3&corp=kuwo&newver=2&vipver=9.3.1.3&source=kwplayer_ar_9.3.1.3_qq.apk&p2p=1&notrace=0&type=convert_url2&br=2000kflac&format=flac|mp3|aac&sig=0&rid="+ id +"&priority=bitrate&loginUid=435947810&network=WIFI&loginSid=1694167478&mode=download&uid=658048466";
        byte[] bArr = s.getBytes();
        byte[] a2 = d.a(bArr, bArr.length, first.getBytes(), first.getBytes().length);
        return new String(b.a(a2, a2.length));
    }
}
