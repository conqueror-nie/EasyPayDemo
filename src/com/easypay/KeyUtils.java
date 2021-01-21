package com.easypay;

import com.easypay.sm.SM2Utils;
import com.easypay.sm.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyUtils {

    /**
     * 参数说明：接口提供两套加签方式，分别为RSA加密和国密加密
     * 每个商户号只能选择一种方式获取私钥（私钥在门户获取，生产环境需要修改需联系易生运营人员，测试环境不允许修改）
     * 测试环境提供了RSA和国密的‘商户号’及‘密钥参数’，具体加签验签方法区别请看代码。
     */

//===============================================测试参数=====================================================================================================================================================================================
    /**
     * 测试参数 ###############
     */

    //=======================RSA===========================================
    //RSA 测试商户号 -- merchant_id
    public static final String TEST_RSA_MERCHANT_ID = "900029000000354";
    //RSA 测试合作伙伴ID -- partner
    public static final String TEST_RSA_PARTNER = "900029000000354";
    //RSA测试商户私钥
    public static final String TEST_MERCHANT_RSA_PRIVATE_KEY ="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIqUuxd92eEBXVneDWhfNP6XCkLcGBO1YAulexKX+OdlfZzB/4NNHkOAQQy84k3ZgIUPIk5hewLbA+XGrk9Wih5HG3ZQeFugeoTcx3vwo7AQv7KnmcKEWFNlOr/EhB3JndmcQnBRsIRRdCP+7nobfBqU0jS8dnpcQX1AtBRZRnkfAgMBAAECgYAe+u70ansZ1Q9EduKycY5MWAHAPqnXRhXppJ3l4zmOqV6ye6Aef1ADsRlZuqQw2S3lESQPN7WjRskRRiBTtjn8Atul9YeC7+QirP1K8seUP5gKB4bcjlzzl1m5dmxldkptJAmdzwYn8PRTW0+tFVyEaD/B8hKGxij4Gew0e8bwCQJBAOboG3ttBESsG2cAtmP1MfKRTjVdY7qRMXzBybcAeobBbmgCQgybVXXgjbGai+qwrQqcVRIp6p1yDWTZxVSuDWsCQQCZpBhcayOCMZR6F8dQJSuSSSIJw/GGN7IXfMYIqLxA2oGzlQ0B1DffOUe2wrid+WdpLuYCz2LYPQHDEgYM1dwdAkEAnfwhEYm9ad73wLnUEQAqdHTGtex316aP3XQZt4Q0UQ73o2IoHsgI6OYDDIlZQfIv8xqTeiIDzEXEtEPrp8yOkQJBAIWAzFZKFqHD2UO6M8vVcKX9fGFF7TH2ZX75Qc82Z9ZmyDs2sgW71QzX5hPN4cQLeqswQFeCw14orMZHfBBdKJUCQQDiWYk85okRugsWtxeJFhMEt2oUT+Kd8Yz5Aiz3J9XIS+zWtJrFlv+hXkVedPJ3xtBF32DZrCbxDn3UjXipRaCP";
    //RSA测试易生公钥
    public static final String TEST_EASYPAY_RSA_PUBLIC_KEY ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2WTfvas1JvvaRuJWIKmKlBLmkRvr2O7Fu3k/zvhJs+X1JQorPWq/yZduY6HKu0up7Qi3T6ULHWyKBS1nRqhhHpmLHnI3sIO8E/RzNXJiTd9/bpXMv+H8F8DW5ElLxCIVuwHBROkBLWS9fIpslkFPt+r13oKFnuWhXgRr+K/YkJQIDAQAB";

    //========================国密==========================================
    //国密 测试商户号 -- merchant_id
    public static final String TEST_SM_MERCHANT_ID = "900010000002555";
    //国密 测试合作伙伴ID -- partner
    public static final String TEST_SM_PARTNER = "900010000002555";

    //国密测试商户私钥
    public static final String  TEST_MERCHANT_SM_PRIVATE_KEY="Mewy51A6JSC1a7LNffbxlJoUqsMRFG9DZtAzMZc1Mw==";
    //国密测试易生公钥
//    public static final String TEST_EASYPAY_SM_PUBLIC_KEY="04385208260b67de2de5218a520d839b90e334aea4f2639a32045492af3bbb4dc2519392db7ea53c79171e8f39f9fdc2845f863b6e849162b15928861d19b9f783";
    public static final String TEST_EASYPAY_SM_PUBLIC_KEY="BDhSCCYLZ94t5SGKUg2Dm5DjNK6k8mOaMgRUkq87u03CUZOS236lPHkXHo85+f3ChF+GO26EkWKxWSiGHRm594M=";

    //===========================测试通用信息==========================================
    //测试访问地址
    //public static String DEFAULT_URL = "https://test_nucc.bhecard.com:9088/api_gateway.do";
    public static String DEFAULT_URL = "http://localhost:8081/api_gateway.do";
    //测试环境敏感信息des加密密钥
    public static String TEST_DES_ENCODE_KEY = "CueaiPrW9sIskbn9qkoPh9J3";



//===================================================生产参数=================================================================================================================================================================================

    /**
     * 生产参数 ###############
     */
    //=======================RSA===========================================
    //RSA 生产商户
    public static final String SC_DEFAULT_MERCHANT_ID = "900029000000354";
    public static final String SC_RSA_PARTNER = "900029000000354";

    //RSA生产商户私钥
    public static final String SC_MERCHANT_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALLfMUbf4u8uDSeG0WR//LSvxv7qglKsHwws3mpyqUWJau1ZXcfMeNQf+OhGTFrKsyP1WS3kXa1ZErjIhdeX5Jq2TphgXWZ+HdNVtd/rmHo84cjHJZOOBSvSmVYzqsJT253LoX1ip2sx/zbobU+Sm//4I5Zo4/yuufWBElCL2cuPAgMBAAECgYEApkJ3Fx27Xf48E+VodDXSulA4c3GeuSFrqnF6Ow9g71WPohZS6QfRt7oQLjZJeoq2gFHpFpMRz7LfiAo6/e4deXEWtMRg4UHcZbdBlTR4oM8au1TTEq446VhllSNZ8qeHxL4zO4peVDNupp8rElOMTXiQLKOph+fioLi++tvqZGECQQDrvtI3QD68BEQXlwMcUHkczppTs3Gxtd5uElQ7BQCTpM1fBhFIzv+TNxhNLGo5+2z1MKjWNE1KBo1ZXZnJdSfLAkEAwj1ykXJkYWbNrTRGzh0ZjSg784c20TeEl2HZ4BvSdCwSRxZUdZYx4x6MUWISlnokaifXSJhnsU9vXoZR4xEKzQJBAN5SJdNfLgqIB2Mr0g4owh7tpFLdPpJ2Tl8FwBOswv96AwfjI/fC5vmBktRs13z45KdSjVb9Ggp+pVyqzfZUGwMCQGdPWWVEq2Em1ZQe7t3nmlR6ptBTBXPnjG0bzU8mXRwO6LXIial09hmvgMA0YmCInF+dyyJAdT5YWoqy9FDKGq0CQQCBstxGn3LUWcGxEmTtmDt3pkHVy2IYQl/xFCgWYW1xIrC7dO8GfLZLzUbq/yBGO1KCRaQpFYKbJNTETB0TlKSY";
    public static final String SC_EASYPAY_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2WTfvas1JvvaRuJWIKmKlBLmkRvr2O7Fu3k/zvhJs+X1JQorPWq/yZduY6HKu0up7Qi3T6ULHWyKBS1nRqhhHpmLHnI3sIO8E/RzNXJiTd9/bpXMv+H8F8DW5ElLxCIVuwHBROkBLWS9fIpslkFPt+r13oKFnuWhXgRr+K/YkJQIDAQAB";

    //===========================生产通用信息==========================================
    //生产访问地址
    public static String SC_URL = "https://newbox.bhecard.com/api_gateway.do";
    //生产环境敏感信息des加密密钥
    public static String SC_DES_ENCODE_KEY = "s6yaiIycSFXufo4jEg3VmLs4";


//====================================================================================================================================================================================================================================

    //常量定义
    public static final String TEST_RSA_ENCODE_TYPE = "RSA";
    public static final String TEST_SM_ENCODE_TYPE = "SM";
    public static final String TEST_DEFAULT_CHARSET = "UTF-8";


    public static String getSign(String key, String charset, String bizContent) throws Exception {
        return AlipaySignature.rsaSign(bizContent, key, charset);
    }

    public static String getSign(String key, String charset, String bizContent,String keyType) throws Exception {
        if(TEST_RSA_ENCODE_TYPE.equals(keyType)) {
            return AlipaySignature.rsaSign(bizContent,  key, charset);
        }
        try {
            return Util.byteToHex( SM2Utils.sign("".getBytes(),Base64.decode(key),bizContent.getBytes(charset)));
        }catch(Exception e) {
            return null;
        }
    }


    public static String getOutTradeNo(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(d) +  System.currentTimeMillis();
    }
}
