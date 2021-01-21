package com.easypay;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//二维码，公众号测试demo
//包含退款和订单查询
public class OrderMain {

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

    //7.6 二维码/APP支付-推送订单
    public static void qrcodePayPush(String payType){
        JSONObject sParaTemp = qrcodeAndJsPayPush(payType);
        sParaTemp.put("pay_acc_type", "00");

        biz_content = sParaTemp.toString();

        service  = "easypay.qrcode.pay.push";
    }
    //7.9 H5收银台推单
    public static JSONObject easyPayh5() {
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("out_trade_no", "easyPayh5_" + KeyUtils.getOutTradeNo());
        sParaTemp.put("bank_code", "EASYPAY");
        sParaTemp.put("account_type", "1");
        sParaTemp.put("subject", "Echannell");
        sParaTemp.put("body", "body");
        sParaTemp.put("amount", "1");
        sParaTemp.put("front_url", "https://www.baidu.com");
        sParaTemp.put("notify_url", "http://127.0.0.1:8080/index.php/Api/YsNotify/notify");
        sParaTemp.put("timeout_minutes", "10");
        sParaTemp.put("order_type", "151");

        biz_content = sParaTemp.toString();
        service = "easypay.merchant.easyPayh5";
        return sParaTemp;
    }

    //7.11 云闪付H5推单
    public static JSONObject untionH5Pay() {
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("out_trade_no", KeyUtils.getOutTradeNo());
        sParaTemp.put("bank_code", "EASYPAY");
        sParaTemp.put("account_type", "1");
        sParaTemp.put("subject", "Echannell");
        sParaTemp.put("body", "body");
        sParaTemp.put("amount", "1");
        sParaTemp.put("front_url", "https://www.baidu.com");
        sParaTemp.put("notify_url", "http://127.0.0.1:8080/index.php/Api/YsNotify/notify");
        sParaTemp.put("timeout_minutes", "10");
        sParaTemp.put("order_type", "151");

        biz_content = sParaTemp.toString();
        service = "easypay.merchant.unionH5Pay";
        return sParaTemp;
    }

    public static JSONObject qrcodeAndJsPayPush(String payType) {
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("seller_email", "18679106330@gmail.com");
        sParaTemp.put("amount", 1);
        sParaTemp.put("business_time", "2017-12-07 15:35:00");
        sParaTemp.put("notify_url", "https://www.baidu.com");
        sParaTemp.put("order_desc", "Echannell");
        sParaTemp.put("subject", "Echannell");
        sParaTemp.put("pay_type", payType);

        HashMap<String, Object> identityMap = new HashMap<String, Object>(30);
        identityMap.put("name", "测试");
        identityMap.put("mobile", "18010461111");
        identityMap.put("idno_type", "1");
        identityMap.put("id_no", "340827199311101111");
        identityMap.put("min_age", "18");

        sParaTemp.put("identity", identityMap);

        sParaTemp.put("out_trade_no", "demo" + KeyUtils.getOutTradeNo() + "_");
        return sParaTemp;
    }

    //7.8 公众号/网页内支付-推送订单
    public static void jsPayPush(String payType, String open_id){
        JSONObject sParaTemp = qrcodeAndJsPayPush(payType);
        sParaTemp.put("open_id", open_id );
        biz_content = sParaTemp.toString();

        service  = "easypay.js.pay.push";
    }


    //10.1 订单查询
    public static void orderQuery(String out_trade_no){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);

        sParaTemp.put("out_trade_no", out_trade_no);

        biz_content = sParaTemp.toString();
        service  = "easypay.merchant.query";
    }

    //10.2 退款
    public static void refund(String origin_trade_no){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("refund_amount", "1");
        sParaTemp.put("out_trade_no", "refund" + KeyUtils.getOutTradeNo());
        sParaTemp.put("origin_trade_no", origin_trade_no);
        sParaTemp.put("subject", "testRefund");

        biz_content = sParaTemp.toString();
        service  = "easypay.merchant.refund";
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

            //二维码订单推送
            OrderMain.qrcodePayPush("aliPay");//银联：unionNative, 微信：wxNative, 支付宝：aliPay

            //H5收银台推单
//            easyPayh5();

            //untionH5Pay
//            untionH5Pay();

            //公众号订单推送
//            OrderMain.jsPayPush("wxJsPay","oVRQJ05dzTQ7PO6qlST36ibnw8X8");//wxJsPay
//            OrderMain.jsPayPush("aliJsPay","20881007434917916336963360919773");// aliJsPay

            //订单查询
//            OrderMain.orderQuery("demo1553480416547");

            //订单退款
//            OrderMain.refund("Test202011243616601");

            //加密类型，默认RSA
            String sign_type = KeyUtils.TEST_RSA_ENCODE_TYPE;
            //编码类型
            String charset = KeyUtils.TEST_DEFAULT_CHARSET;

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
            if(e != null){
                System.out.print(e.getMessage()+ "\n");
            }else {
                System.out.print("-----其他未知错误--------"+ "\n");
            }
        }
    }
}
