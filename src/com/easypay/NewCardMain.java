package com.easypay;

import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//新无卡测试demo
public class NewCardMain {

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


    //9.3 直接(认证)支付-获取短信验证码
    public static void directPayValidateAccount(){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);

//        sParaTemp.put("seller_email", "18679106330@gmail.com");
        sParaTemp.put("amount", "1");
        sParaTemp.put("business_time", "2020-02-27 10:35:00");
        sParaTemp.put("notify_url", "https://www.baidu.com");
        sParaTemp.put("subject", "Echannell");
        sParaTemp.put("out_trade_no", KeyUtils.getOutTradeNo());

        sParaTemp.put("bank_code", "CEB");

        sParaTemp.put("name", getEncode("测试"));
        sParaTemp.put("id_no", getEncode("340827199911115654"));//身份证
        sParaTemp.put("acc", getEncode("6217992900041925555")); //银行卡号

        sParaTemp.put("mobile", getEncode("18010461111"));

        biz_content = sParaTemp.toString();

        service  = "easypay.pay.directPay.validateAccount";
    }

    //9.4 直接(认证)支付-支付
    public static void directPay(String orderId,String sms_code){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);

        sParaTemp.put("out_trade_no",orderId);

        sParaTemp.put("bank_code", "CEB");

        sParaTemp.put("name", getEncode("测试"));
        sParaTemp.put("id_no", getEncode("340827199311101111"));//身份证
        sParaTemp.put("acc", getEncode("6217992900041921111")); //银行卡号

        sParaTemp.put("mobile", getEncode("18010461111"));

        sParaTemp.put("sms_code",sms_code);

        biz_content = sParaTemp.toString();

        service  = "easypay.pay.directPay";
    }

    //7.3 新无卡-协议支付-账户认证
    public static void validateAccount(){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("name", getEncode("测试"));    //账户姓名
        sParaTemp.put("id_no", getEncode("340827199311101111")); //身份证号
        sParaTemp.put("bank_code", "HXB");//银行编号(请见协议支付在线文档)
        sParaTemp.put("acc", getEncode("6230200055011111"));   //银行卡号
        sParaTemp.put("mobile", getEncode("18010461111")); //手机号
//        sParaTemp.put("cvv", getEncode("111")); //cvv
//        sParaTemp.put("validity_date", getEncode("2011")); //有效期
        sParaTemp.put("out_trade_no", KeyUtils.getOutTradeNo());
        sParaTemp.put("type", 0);//0 协议支付 1 商户测token快捷
        biz_content = sParaTemp.toString();

        service  = "easypay.pay.agreement.validate";
    }


    private static String getEncode(String data){
        return StringUtils.bytesToHexStr(DesUtil.desEncode(data, DES_ENCODE_KEY));
    }

    //7.4 新无卡-协议支付-账户签约
    public static void agreementPayBind(String orig_out_trade_no, String sms_code){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("orig_out_trade_no", orig_out_trade_no);
        sParaTemp.put("sms_code", sms_code);
        sParaTemp.put("type", 0);//0 协议支付 1 商户测token快捷

        biz_content = sParaTemp.toString();
        service  = "easypay.pay.agreement.bind";
    }

    //7.5 新无卡-协议支付-支付
    public static void agreementPay(String sign_no){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("seller_email", "18679106330@gmail.com");
        sParaTemp.put("amount", "1");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        sParaTemp.put("business_time", sdf.format(d) ); //"2019-12-07 15:35:00"
        sParaTemp.put("notify_url", "https://www.baidu.com");
        sParaTemp.put("order_desc", "Echannell");
        sParaTemp.put("out_trade_no", KeyUtils.getOutTradeNo());
        sParaTemp.put("sign_no", sign_no);
        sParaTemp.put("subject", "subject");
        sParaTemp.put("type", 0);//0 协议支付 1 商户测token快捷
        sParaTemp.put("order_type", "507");
        sParaTemp.put("org_name", "协议支付测试");
        sParaTemp.put("org_licenseNum", "34082712345678");
        sParaTemp.put("org_uniqueId", "00000000000000");

        biz_content = sParaTemp.toString();
        service  = "easypay.pay.agreement";
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
                //商户私钥
                key = KeyUtils.SC_MERCHANT_PRIVATE_KEY;
                //易生公钥
                easypay_pub_key = KeyUtils.SC_EASYPAY_PUBLIC_KEY;
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

            //新无卡-直接支付-账户认证
//            NewCardMain.directPayValidateAccount();
//            新无卡-直接支付
//            NewCardMain.directPay("202006091591676974917", "683222");


            //新无卡-协议支付-账户认证
            NewCardMain.validateAccount();

            //新无卡-协议支付-账户签约
//            NewCardMain.agreementPayBind("202006181592471454860", "433239");

//            新无卡-协议支付-支付--ES2020040800459954  --ES2020040900460491
//            NewCardMain.agreementPay("ES2020061802503233");//ES2020031008149818--生产环境 ES2020022900195008--测试环境

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
