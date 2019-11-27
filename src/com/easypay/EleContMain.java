package com.easypay;

import net.sf.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * 电子签约测试
 * @author njp
 *
 */
public class EleContMain {

	//标记生产还是测试环境
    public static boolean isTest = false;

    //根据接口文档生成对应的json请求字符串
    private static String biz_content = "";

    //接口文档中的方法名
    private static String service = "easypay.elecont.createCont";

    //协议开户商户
    private static String tempMerchantId = "900036800002758";
    //商户号
    private static String merchant_id = KeyUtils.TEST_DEFAULT_MERCHANT_ID;

    //接入机构号
    private static String partner = KeyUtils.TEST_DEFAULT_PARTNER;

    //请求地址
    private static String url = KeyUtils.DEFAULT_URL;

    //商户私钥
    private static String key = KeyUtils.TEST_MERCHANT_PRIVATE_KEY;

    //易生公钥
    private static String easypay_pub_key = KeyUtils.TEST_EASYPAY_PUBLIC_KEY;

    //加密密钥
    private static String DES_ENCODE_KEY = "j9q1x7DQaAGqEtyQNjqgzRKD";

    //协议预览
    public static void protocolPreview(){
        //测试动态参数用
        HashMap<String, Object> dynamicMap = new HashMap<String, Object>(30);
        dynamicMap.put("d1", "90");
        dynamicMap.put("d2", "1");
        dynamicMap.put("d3", "9");

        dynamicMap.put("b7", "测试商户");
        dynamicMap.put("b10", "上海市浦东新区");

        dynamicMap.put("b14", "张三");
        dynamicMap.put("b15", "18888888888");
        dynamicMap.put("b20", "√");
        dynamicMap.put("b21", "");
        dynamicMap.put("b22", "无");
        
        dynamicMap.put("b23", "0.55%");
        dynamicMap.put("b24", "20");
        dynamicMap.put("b25", "0.55%");
        dynamicMap.put("b26", "0.29%");
        dynamicMap.put("b27", "0.29%");
        dynamicMap.put("b28", "1000 元以下（含 1000 元）， 借记卡 0.29%， 贷记卡 0.29%；1000 元以上，借记卡 0.55%,20 元封顶，贷记卡 0.55%");
        dynamicMap.put("b29", "0.29%");
        dynamicMap.put("b30", "0.29%");

        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("model_name", "好开店商户（三方）受理支付业务协议");
//        sParaTemp.put("idno", "210624199702023099");
//        sParaTemp.put("idno_type", "1");
        sParaTemp.put("dynamic_map", dynamicMap);
        biz_content = sParaTemp.toString();

        service  = "easypay.elecont.protocolPreview";
    }

    //发送短信
    public static void sendSMS(){
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("random_code", "00");
        sParaTemp.put("model_name", "好开店商户（三方）受理支付业务协议");
//        sParaTemp.put("idno", "321281198301014053");
//        sParaTemp.put("idno_type", "1");
        biz_content = sParaTemp.toString();

        service  = "easypay.elecont.sendSMS";
    }

    //创建合同
    public static void createCont(){

        HashMap<String, Object> dynamicMap = new HashMap<String, Object>(30);
        dynamicMap.put("d1", "90");
        dynamicMap.put("d2", "1");
        dynamicMap.put("d3", "9");

        dynamicMap.put("b7", "测试商户");
        dynamicMap.put("b10", "上海市浦东新区");

        dynamicMap.put("b14", "张三");
        dynamicMap.put("b15", "18888888888");
        dynamicMap.put("b20", "√");
        dynamicMap.put("b21", "");
        dynamicMap.put("b22", "无");
        
        dynamicMap.put("b23", "0.55%");
        dynamicMap.put("b24", "20");
        dynamicMap.put("b25", "0.55%");
        dynamicMap.put("b26", "0.29%");
        dynamicMap.put("b27", "0.29%");
        dynamicMap.put("b28", "1000 元以下（含 1000 元）， 借记卡 0.29%， 贷记卡 0.29%；1000 元以上，借记卡 0.55%,20 元封顶，贷记卡 0.55%");
        dynamicMap.put("b29", "0.29%");
        dynamicMap.put("b30", "0.29%");
        
        JSONObject sParaTemp = new JSONObject();
        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("random_code", "00");
        sParaTemp.put("check_code", "575818");
        sParaTemp.put("location", "172.168.3.21");
        sParaTemp.put("out_trade_no", KeyUtils.getOutTradeNo());
        sParaTemp.put("contract_name", "好开店商户（三方）受理支付业务协议");
        sParaTemp.put("model_name", "好开店商户（三方）受理支付业务协议");
//        sParaTemp.put("idno", "");
//        sParaTemp.put("idno_type", "");
        sParaTemp.put("dynamic_map", dynamicMap);
        biz_content = sParaTemp.toString();

        service  = "easypay.elecont.createContract";
    }

    //合同查询
    public static void queryCont(String contractNo) {
 	   JSONObject sParaTemp = new JSONObject();
 	   sParaTemp.put("merchant_id",merchant_id);
   	   sParaTemp.put("contract_no", contractNo);

   	   biz_content = sParaTemp.toString();

   	 service = "easypay.elecont.queryCont";

    }

    //下载合同
   public static void downloadCont(String contractNo) {
	    JSONObject sParaTemp = new JSONObject();
     	sParaTemp.put("merchant_id",merchant_id);
   	    sParaTemp.put("contract_no", contractNo);

   	    biz_content = sParaTemp.toString();

     	service = "easypay.elecont.downloadCont";
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
                partner = KeyUtils.SC_DEFAULT_PARTNER;
                //请求地址
                url = KeyUtils.SC_URL;
                //商户私钥
                key = KeyUtils.SC_MERCHANT_PRIVATE_KEY;
                //易生公钥
                easypay_pub_key = KeyUtils.SC_EASYPAY_PUBLIC_KEY;
                //加密密钥
                DES_ENCODE_KEY = KeyUtils.SC_DES_ENCODE_KEY;
            }

            //协议预览
//            protocolPreview();
            //发送短信
//            sendSMS();
            //创建合同
            createCont();
            //合同查询
//            queryCont("QT20191122000014329");
//            下载合同
//            downloadCont("QT20191122000014329");


            //加密类型，默认RSA
            String sign_type = KeyUtils.TEST_DEFAULT_ENCODE_TYPE;
            //编码类型
            String charset = KeyUtils.TEST_DEFAULT_CHARSET;

            //根据请求参数生成的机密串
            String sign = KeyUtils.getSign(key, charset, biz_content);
            System.out.print("key=" + key + "\n");
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
            StringUtils.rsaVerifySign(resultStrBuilder, easypay_pub_key);
        }catch (Exception e){
            System.out.print(e.getMessage()+ "\n");
        }
    }

}
