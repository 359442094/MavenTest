package sk.toInterface;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 接口工具类
 * */
public class interFaceUtil {

        /**
         * 调用对方接口方法
         * @param path 对方或第三方提供的路径
         * @param data 向对方或第三方发送的数据，大多数情况下给对方发送JSON数据让对方解析
         */
        public static void main(String[] args) throws IOException {
            String jsonStr="11111";
            String path="http://gc.ditu.aliyun.com/geocoding?a=祁东县"; //http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo"
            String reuslt = sendPost(jsonStr, path);
            System.out.println(reuslt);
        }

        /**
        * 备用测试地址：
        * 　　手机信息查询接口：http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=手机号 　　　　　　　　　　　
        * 　　阿里云根据地区名获取经纬度接口：http://gc.ditu.aliyun.com/geocoding?a=兰州市
        * */
        public static String sendPost(String jsonStr, String path)
                throws IOException {
            byte[] data = jsonStr.getBytes();
            URL url = new URL(path);
            HttpURLConnection conn =(java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);// 设置连接超时时间为5秒
            conn.setReadTimeout(20 * 1000);// 设置读取超时时间为20秒
            // 使用 URL 连接进行输出，则将 DoOutput标志设置为 true
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "text/json;charset=UTF-8");
            //conn.setRequestProperty("Content-Encoding","gzip");
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            OutputStream outStream = conn.getOutputStream();// 返回写入到此连接的输出流
            outStream.write(data);
            outStream.close();//关闭流
            String msg = "";// 保存调用http服务后的响应信息
            // 如果请求响应码是200，则表示成功
            if (conn.getResponseCode() == 200) {
                // HTTP服务端返回的编码是UTF-8,故必须设置为UTF-8,保持编码统一,否则会出现中文乱码
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        (InputStream) conn.getInputStream(), "UTF-8"));
                msg = in.readLine();
                in.close();
            }
            conn.disconnect();// 断开连接
            return msg;
        }
}

