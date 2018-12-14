package sk.util.toInterface;

import net.sf.json.JSONObject;
import sk.util.jsonformat.JSONFormatUtil;

import java.io.IOException;

public class InterfaceText {
    /**
     * 备用测试地址：
     * 　　手机信息查询接口：http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=手机号
     * 　　阿里云根据地区名获取经纬度接口：http://gc.ditu.aliyun.com/geocoding?a=兰州市
     */
    public static void main(String[] args) throws IOException {
        String jsonStr = "11111";
        String path = "http://ip.taobao.com/service/getIpInfo.php?ip=63.223.108.42"; //http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo"
        String reuslt = InterFaceUtil.sendPost(path,"","POST");
        String formatJson = JSONFormatUtil.formatJson(reuslt);
        System.out.println("formatJson:"+formatJson);
        JSONObject jsonObject = JSONObject.fromObject(formatJson);
        System.out.println("jsonObject:"+jsonObject);
    }
}
