package com.easypay;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商户分账测试
 * @author dgj
 * @author dgj
 * @date  2019年10月28日
 */
public class SubAccountMain {

	//标记生产还是测试环境
    public static boolean isTest = true;

    //根据接口文档生成对应的json请求字符串
    private static String biz_content = "";

    //接口文档中的方法名
    private static String service = "easypay.platform.ledger";

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
    private static String DES_ENCODE_KEY = KeyUtils.TEST_DES_ENCODE_KEY;

    //子账户分账
    public static void separateAccount() throws ParseException {
        //子账户信息集合
        JSONArray jsonList = new JSONArray();
        JSONObject sParaTemp = new JSONObject();
        //子账户信息
        JSONObject subAccountA = new JSONObject();
        subAccountA.put("acc_id","100000001281");
        subAccountA.put("separate_amount",1);
        subAccountA.put("remark","入账测试");

        JSONObject subAccountB = new JSONObject();
        subAccountB.put("acc_id","100000003103");
        subAccountB.put("separate_amount",2);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfOrigin = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfNo = new SimpleDateFormat("yyyyMMddHHmmss");

        jsonList.add(subAccountA);
        jsonList.add(subAccountB);

        sParaTemp.put("merchant_id", merchant_id);
        sParaTemp.put("partner_id", partner);
        sParaTemp.put("trade_time", sdf.format(d).toString());
        sParaTemp.put("notify_url", "http://127.0.0.1/test");
        //生成订单号
        long orderNo=Long.valueOf(sdfNo.format(d)+Math.round(Math.random()*10));
        sParaTemp.put("out_trade_no", orderNo);
        sParaTemp.put("origin_trade_no", "201910141826349427");

        sParaTemp.put("total_amount", 3);
        sParaTemp.put("sub_acc_infos", jsonList.toString());

        biz_content = sParaTemp.toString();

        service  = "easypay.platform.ledger";
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

            //分账
            separateAccount();

            //加密类型，默认RSA
            String sign_type = KeyUtils.TEST_DEFAULT_ENCODE_TYPE;
            //编码类型
            String charset = KeyUtils.TEST_DEFAULT_CHARSET;

            //根据请求参数生成的机密串
            String sign = KeyUtils.getSign(key, charset, biz_content);
            System.out.print("计算签名数据为：" + sign + "\n");
            Map<String, String> reqMap = new HashMap<String, String>(6);
            reqMap.put("biz_content", biz_content);
            reqMap.put("service", service);
            reqMap.put("partner", partner);
            reqMap.put("sign_type", sign_type);
            reqMap.put("charset", charset);
            reqMap.put("sign", sign);
            System.out.println("biz_content: " + biz_content);
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
