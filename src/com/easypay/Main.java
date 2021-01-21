package com.easypay;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main {

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

    //6.3 现金账户冻结
    public static void preauth() {
        JSONObject reqMap = new JSONObject();
        reqMap.put("merchant_id", merchant_id);
        reqMap.put("amount", "1");
        reqMap.put("out_trade_no", KeyUtils.getOutTradeNo());
        biz_content = reqMap.toString();
        service = "trade.auth.preauth";
    }

    //6.7 账户余额查询
    public static void balnace() {
        JSONObject reqMap = new JSONObject();
        reqMap.put("merchant_id", merchant_id);
        biz_content = reqMap.toString();
        service = "trade.acc.balance";
    }


    //7.1 合单支付-平台商户推单推送订单
    public static void pushMergeOrder() {
        service = "easypay.merchant.merge.pay";
        JSONObject reqMap = new JSONObject();
        reqMap.put("subject", "测试主订单");
        reqMap.put("body", service);
        reqMap.put("out_merge_no", "M" + KeyUtils.getOutTradeNo());

        JSONArray jsonarray = new JSONArray();
        JSONObject orderDetail = new JSONObject();
        orderDetail.put("subject", "测试子订单1");
        orderDetail.put("body", "测试子订单1");
        orderDetail.put("merchant_id", merchant_id);
        orderDetail.put("out_trade_no", "SUB1" + KeyUtils.getOutTradeNo());
        orderDetail.put("total_amount", "1");
        orderDetail.put("seller_email", "sellerEmail");
        orderDetail.put("buyer_email", "sellerEmail");

        JSONObject orderDetai2 = new JSONObject();
        orderDetai2.put("subject", "测试子订单2");
        orderDetai2.put("body", "测试子订单2");
        orderDetai2.put("merchant_id", merchant_id);
        orderDetai2.put("out_trade_no", "SUB2" +  KeyUtils.getOutTradeNo());
        orderDetai2.put("total_amount", "1");
        orderDetai2.put("seller_email", "sellerEmail");
        orderDetai2.put("buyer_email", "sellerEmail");

        jsonarray.add(orderDetail);
        jsonarray.add(orderDetai2);

        reqMap.put("order_details", jsonarray.toString());
        biz_content = reqMap.toString();
        System.out.println(biz_content);
    }

    //7.2 直连网银推单
    public static void pushPortalOrder() {
        service = "easypay.merchant.netBankPay";
        JSONObject reqMap = new JSONObject();
        reqMap.put("subject", "直连网银推单");
        reqMap.put("body", "直连网银推单");
        reqMap.put("merchant_id", merchant_id);
        reqMap.put("out_trade_no",  KeyUtils.getOutTradeNo());
        reqMap.put("bank_code", "UNIONPAY_FRONT");
        reqMap.put("account_type", "1"); //1 借记 2 贷记 3 企业
        reqMap.put("amount", "1");
        reqMap.put("accNo", "6253000000001234");    //快捷卡号
        reqMap.put("front_url", "https://www.baidu.com");
        reqMap.put("notify_url", "https://www.baidu.com");



        biz_content = reqMap.toString();
        System.out.println(biz_content);
    }


    //7.7 标准收银台推单
    public static void pushCashierOrder() {
        service = "easypay.merchant.easyPay";
        JSONObject reqMap = new JSONObject();
        reqMap.put("subject", "标准收银台推单");
        reqMap.put("body", "标准收银台推单");
        reqMap.put("merchant_id", merchant_id);
        reqMap.put("out_trade_no",  KeyUtils.getOutTradeNo());
        reqMap.put("bank_code", "EASYPAY");
        reqMap.put("account_type", "1"); //1 借记 2 贷记 3 企业
        reqMap.put("amount", "1");
//        reqMap.put("accNo", "62260978003");    //快捷卡号
//        reqMap.put("accNo", "6253000000001234");    //快捷卡号
        reqMap.put("front_url", "https://www.baidu.com");
        reqMap.put("notify_url", "https://www.baidu.com");

        biz_content = reqMap.toString();
        System.out.println(biz_content);
    }


    public static void main(String[] args) {
        //易生请求示例子
        try {
            if (!isTest) {
                //商户号
                merchant_id = KeyUtils.SC_DEFAULT_MERCHANT_ID;
                //接入机构号
                partner = KeyUtils.SC_RSA_PARTNER;
                //请求地址
                url = KeyUtils.SC_URL;
                //key密钥
                key = KeyUtils.SC_MERCHANT_PRIVATE_KEY;
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


            //根据接口文档生成对应的json请求字符串
            //6.3 现金账户冻结
            //Main.preauth();

            //6.7 账户余额查询
            //Main.balnace();

            //7.1 合单支付-平台商户推单推送订单
//            Main.pushMergeOrder();

            //7.2 直连网银推单
            Main.pushPortalOrder();

            //7.7 标准收银台推单
//            Main.pushCashierOrder();

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

            if(service == "easypay.merchant.merge.pay" || service == "easypay.merchant.netBankPay" || service == "easypay.merchant.easyPay") { //合单支付生产form表单
                System.out.println("Form请求html: \n");
                StringUtils.createAutoFormHtml(url,reqMap,"UTF-8");
            }else{
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
            }

        } catch (Exception e) {
            if (e != null) {
                System.out.print(e.getMessage() + "\n");
            } else {
                System.out.print("-----其他未知错误--------" + "\n");
            }
        }
    }




}
