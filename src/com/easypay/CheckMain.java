package com.easypay;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 对账测试
 * @author njp
 *
 */
public class CheckMain {

    //  ***  标记生产还是测试环境  true:测试   false:生产
    public static boolean isTest = true;

    //  ***  加密类型，可选RSA加密 / SM国密加密  不同加密方式对应不同商户私钥及易生公钥
    private static String sign_type = KeyUtils.TEST_RSA_ENCODE_TYPE;//RSA--KeyUtils.TEST_RSA_ENCODE_TYPE  ;   SM--KeyUtils.TEST_SM_ENCODE_TYPE

    //根据接口文档生成对应的json请求字符串
    private static String biz_content = "";

    //接口文档中的方法名
    private static String service = "";

    //商户号
    private static String merchant_id = KeyUtils.TEST_RSA_MERCHANT_ID;

    //接入机构号
    private static String partner = KeyUtils.TEST_RSA_PARTNER;

    //请求地址
    private static String url = KeyUtils.DEFAULT_URL;

    //key密钥
    private static String key = KeyUtils.TEST_MERCHANT_RSA_PRIVATE_KEY;

    //易生公钥
    private static String easypay_pub_key = KeyUtils.TEST_EASYPAY_RSA_PUBLIC_KEY;

    //加密密钥
    private static String DES_ENCODE_KEY = KeyUtils.TEST_DES_ENCODE_KEY;

    //编码类型
    private static String charset = KeyUtils.TEST_DEFAULT_CHARSET;

    //11.1 对账文件下载
    public static void seller(){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("user_type", 1);
        sParaTemp.put("trade_type", 1);
        sParaTemp.put("start_date", "20190403");
        sParaTemp.put("end_date", "20190403");
        sParaTemp.put("status", "1");
        biz_content = sParaTemp.toString();

        service  = "easypay.pay.check";
    }


    private static String getEncode(String data){
        return StringUtils.bytesToHexStr(DesUtil.desEncode(data, DES_ENCODE_KEY));
    }


    public static void main(String[] args) {
        //易生请求示例子
        try {

            //系统入件之后生成的合作伙伴ID（一般会通过邮件发送）
            if (!isTest) {
                //商户号
                merchant_id = KeyUtils.SC_DEFAULT_MERCHANT_ID;
                //接入机构号
                partner = KeyUtils.SC_RSA_PARTNER;
                //请求地址
                url = KeyUtils.SC_URL;
                //key密钥
                key = KeyUtils.SC_MERCHANT_PRIVATE_KEY;
                //加密密钥
                DES_ENCODE_KEY = KeyUtils.SC_DES_ENCODE_KEY;
            }else if(sign_type.equalsIgnoreCase(KeyUtils.TEST_SM_ENCODE_TYPE)){ //测试环境下，根据常量sign_type判断是RSA加密还是国密加密
                //商户号
                merchant_id = KeyUtils.TEST_SM_MERCHANT_ID;
                //接入机构号
                partner = KeyUtils.TEST_SM_PARTNER;
                //商户私钥
                key = KeyUtils.TEST_MERCHANT_SM_PRIVATE_KEY;
                //易生公钥
                easypay_pub_key = KeyUtils.TEST_EASYPAY_SM_PUBLIC_KEY;
            }

            //11.1 对账文件下载
            seller();

            //根据请求参数生成的机密串
            String sign = KeyUtils.getSign(key, charset, biz_content,sign_type);
            System.out.print("计算签名数据为：" + sign + "\n");
            Map<String, String> reqMap = new HashMap<String, String>(6);
            reqMap.put("biz_content", biz_content);
            reqMap.put("service", service);
            reqMap.put("partner", partner);
            reqMap.put("sign_type", sign_type);
            reqMap.put("charset", charset);
            reqMap.put("sign", sign);

            StringBuilder resultStrBuilder = new StringBuilder();
            int ret = HttpConnectUtils.sendRequest(url, KeyUtils.TEST_DEFAULT_CHARSET, reqMap, 30000, 60000, "POST", resultStrBuilder, null);
            System.out.print(" \n请求地址为：" + url +
                    "\n 请求结果为：" + ret +
                    "\n 请求参数为：" + reqMap.toString() +
                    "\n 返回内容为：" + resultStrBuilder.toString() + "\n");

            //易生公钥验证返回签名
            try {
                StringUtils.rsaVerifySign(resultStrBuilder, easypay_pub_key,sign_type);
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }catch (Exception e){
            System.out.print(e.getMessage()+ "\n");
        }
    }

}
