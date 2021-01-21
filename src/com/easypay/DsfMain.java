package com.easypay;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 代付测试
 * @author njp
 *
 */
public class DsfMain {

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


    /**
     * //实时代付
    public static void dsfPay(){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("out_trade_no", KeyUtils.getOutTradeNo());
        sParaTemp.put("type", 1);//代付类型 固定传1
        sParaTemp.put("nbkno", "313100001274"); //联行号
//        sParaTemp.put("bank_code", "308");//民生银行(银行编号请见‘代付’页面底部)
//        sParaTemp.put("bank_name", "上海");//银行网点名称
//        sParaTemp.put("city", "上海");
        sParaTemp.put("acc", "6225881008024282");   //银行卡号
        sParaTemp.put("name", "test");    //账户姓名
        sParaTemp.put("acc_type", 2); //付款账户类型：1 结算账户 2 现金账户, 默认2现金账户
        sParaTemp.put("amount", "180");
        sParaTemp.put("bis_type", 1);//收款账户类型：0 对公 1 对私
        biz_content = sParaTemp.toString();

        service  = "trade.acc.dsfpay.pay";
    }
     */

  //联机代付
    public static void dsfNewPay(){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("out_trade_no", KeyUtils.getOutTradeNo().substring(0, 18));
//        sParaTemp.put("nbkno", ""); //联行号
        sParaTemp.put("acc", "6225881008024283");   //银行卡号
        sParaTemp.put("name", "test");    //账户姓名
        sParaTemp.put("idno", "123456789012345679");    //身份证号
        sParaTemp.put("acc_type", 2); //付款账户类型：1 结算账户 2 现金账户, 默认2现金账户
        sParaTemp.put("amount", "180");
        biz_content = sParaTemp.toString();

        service  = "trade.acc.dsfpay.newPay";
    }



    private static String getEncode(String data){
        return StringUtils.bytesToHexStr(DesUtil.desEncode(data, DES_ENCODE_KEY));
    }

    //代付查询
    public static void dsfQuery(String outTradeNo){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("out_trade_no", outTradeNo);

        biz_content = sParaTemp.toString();
        service  = "trade.acc.dsfpay.query";
    }

    //代付回单下载
    public static void getReceipt(String outTradeNo){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("out_trade_no", outTradeNo);

        biz_content = sParaTemp.toString();
        service  = "trade.acc.dsfpay.getReceipt";
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

            //6.13 联机代付
            dsfNewPay();

            //6.9 代付查询
//            dsfQuery("dsf1554781247666");

            //6.20 代付回单下载
//            getReceipt("20201020185222101");

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
