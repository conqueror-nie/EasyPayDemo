package com.easypay;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MerchantMain {

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

    //商户进件
    public static void addMerchant() {
        JSONObject reqMap = new JSONObject();
        reqMap.put("merchant_id", merchant_id);
        reqMap.put("name", "接口测试");
        reqMap.put("userType", 3);
        reqMap.put("businessProvince", 13);
        reqMap.put("businessCity", 1301);
        reqMap.put("contactTel", "13801010101");
        reqMap.put("address", "XX大街1号");
        reqMap.put("mobile", "13817200001");
        reqMap.put("email", "13817200001@qq.com");
        reqMap.put("mccType", 2);
        reqMap.put("licenseName", "接口测试");
        reqMap.put("licenseNum", "AAAABBBBCCCCDDDD");
        reqMap.put("licenceBeginDate", "2010-01-01");
        reqMap.put("licenceEndDate", "2050-01-01");
        reqMap.put("registeredCapital", "1580000");
        reqMap.put("currency", "人民币");
        reqMap.put("ip", "192.168.1.1");
        reqMap.put("mac", "aabbccdd1234");
        reqMap.put("icp", "ICP-0001");
        reqMap.put("website", "www.aaa.com");
        reqMap.put("appName", "接口测试APP");
        reqMap.put("agentName", "张三");
        reqMap.put("agentIdNoType", 1);
        reqMap.put("agentIdNo", "310110199001010001");
        reqMap.put("agentIdExpireDate", "2024-12-31");
        reqMap.put("realName", "李四");
        reqMap.put("idno", "310228199101010001");
        reqMap.put("idnoType", 1);
        reqMap.put("idExpireDate", "2020-05-23");
        reqMap.put("scope", "这里是运营范围");
        reqMap.put("accType", 1);
        reqMap.put("bankAccName", "李四");
        reqMap.put("bankAcc", "6217001210033852123");
        reqMap.put("bank", "中国建设银行");
        reqMap.put("province", 31);
        reqMap.put("city", 3101);
        reqMap.put("bankName", "中国建设银行上海航华支行");
        biz_content = reqMap.toString();
        service = "merchant.add.input";
    }

    //商户入网审批
    public static void confirmMerchant() {
        JSONObject reqMap = new JSONObject();
        reqMap.put("merchant_id", "900029000002745");
        reqMap.put("remark", "审批通过");
        biz_content = reqMap.toString();
        service = "merchant.add.confirm";
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

            //12.1商户基本信息入网
//            addMerchant();

            //12.2商户入网审批
            confirmMerchant();

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

        } catch (Exception e) {
            if (e != null) {
                System.out.print(e.getMessage() + "\n");
            } else {
                System.out.print("-----其他未知错误--------" + "\n");
            }
        }
    }
}
