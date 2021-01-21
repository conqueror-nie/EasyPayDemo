package com.easypay;

import com.easypay.sm.SM2Utils;
import com.easypay.sm.Util;
import org.bouncycastle.util.encoders.Base64;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StringUtils {
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;
    private static final String[] PADDING = new String['\uffff'];

    static {
        PADDING[32] = "                                                                ";
    }

    public StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNumericSpace(String str) {
        if (str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != ' ') {
                    return false;
                }
            }

            return true;
        }
    }
    public static boolean isNotBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }
    public static String substringBefore(String str, String separator) {
        if (!isEmpty(str) && separator != null) {
            if (separator.length() == 0) {
                return "";
            } else {
                int pos = str.indexOf(separator);
                return pos == -1 ? str : str.substring(0, pos);
            }
        } else {
            return str;
        }
    }
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }


    public static String trim(String str) {
        return str == null ? null : str.trim();
    }


    public static String uncapitalize(String str) {
        int strLen;
        return str != null && (strLen = str.length()) != 0 ? (new StringBuffer(strLen)).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString() : str;
    }

    public static String upperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }



    public static String bytesToHexStr(byte[] b) {
        return bytesToHexStr(b, 0, b.length);
    }

    public static String bytesToHexStr(byte[] b, int start, int len) {
        StringBuffer var3 = new StringBuffer();

        for(int var4 = start; var4 < start + len; ++var4) {
            var3.append(String.format("%02x", b[var4]));
        }

        return var3.toString();
    }

    public static byte[] hexStrToBytes(String str) {
        if (str.length() % 2 != 0) {
            str = "0" + str;
        }

        byte[] var1 = new byte[str.length() / 2];

        for(int var2 = 0; var2 < str.length(); var2 += 2) {
            var1[var2 / 2] = (byte)((Byte.parseByte(str.substring(var2, var2 + 1), 16) << 4) + Byte.parseByte(str.substring(var2 + 1, var2 + 2), 16));
        }

        return var1;
    }

    public static void hexStrToBytes(String str, byte[] b, int from) {
        if (str.length() % 2 != 0) {
            str = "0" + str;
        }

        hexStrToBytes(str, b, from, str.length() / 2);
    }

    public static void hexStrToBytes(String str, byte[] b, int from, int length) {
        if (str.length() % 2 != 0) {
            str = "0" + str;
        }

        for(int var4 = 0; var4 < Math.min(str.length(), length << 1) && from + var4 / 2 < b.length; var4 += 2) {
            b[from + var4 / 2] = (byte)((Byte.parseByte(str.substring(var4, var4 + 1), 16) << 4) + Byte.parseByte(str.substring(var4 + 1, var4 + 2), 16));
        }

    }

    public static byte[] hexStrToInverseBytes(String str) {
        if (str.length() % 2 != 0) {
            str = "0" + str;
        }

        byte[] var1 = new byte[str.length() / 2];

        for(int var2 = 0; var2 < str.length(); var2 += 2) {
            var1[var1.length - 1 - var2 / 2] = (byte)((Byte.parseByte(str.substring(var2, var2 + 1), 16) << 4) + Byte.parseByte(str.substring(var2 + 1, var2 + 2), 16));
        }

        return var1;
    }

    public static String inverseBytesToHexStr(byte[] b, int start, int len) {
        StringBuffer var3 = new StringBuffer();

        for(int var4 = start + len - 1; var4 >= start; --var4) {
            var3.append(String.format("%02x", b[var4]));
        }

        return var3.toString();
    }

    public static String lengthFix(String text, int length, char ch, boolean end) {
        if (text == null) {
            text = "";
        }

        int var4 = text.getBytes().length;
        if (length == var4) {
            return text;
        } else if (length <= var4) {
            return end ? new String(text.getBytes(), 0, length) : new String(text.getBytes(), var4 - length, length);
        } else {
            char[] var5 = new char[length - var4];

            for(int var6 = 0; var6 < var5.length; ++var6) {
                var5[var6] = ch;
            }

            StringBuffer var7 = new StringBuffer(text);
            if (end) {
                var7 = var7.append(var5);
            } else {
                var7 = var7.insert(0, var5);
            }

            return var7.toString();
        }
    }

    public static String lengthFix(String text, int length, String ch, boolean end) {
        return lengthFix(text, length, ch.charAt(0), end);
    }


    /**
     * 功能：前台交易构造HTTP POST自动提交表单<br>
     * @param reqUrl 表单提交地址<br>
     * @param hiddens 以MAP形式存储的表单键值<br>
     * @param encoding 上送请求报文域encoding字段的值<br>
     * @return 构造好的HTTP POST交易表单<br>
     */
    public static String createAutoFormHtml(String reqUrl, Map<String, String> hiddens, String encoding) {
        StringBuffer sf = new StringBuffer();
        sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset="+encoding+"\"/></head><body>");
        sf.append("<form id = \"pay_form\" action=\"" + reqUrl
                + "\" method=\"post\">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Map.Entry<String, String>> set = hiddens.entrySet();
            Iterator<Map.Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> ey = it.next();
                String key = ey.getKey();
//                String value = URLEncoder.encode(ey.getValue(), encoding) ;
                String value = ey.getValue();

                sf.append("<input type='hidden' name='" + key + "' id='"
                        + key + "' value='" + value + "'/>");

            }
        }
        sf.append("</form>");
        sf.append("</body>");
        sf.append("<script type=\"text/javascript\">");
        sf.append("document.all.pay_form.submit();");
        sf.append("</script>");
        sf.append("</html>");
        System.out.println("html: " + sf.toString());
        return sf.toString();
    }

    public static void rsaVerifySign(StringBuilder resultStrBuilder, String easypay_pub_key) throws Exception {
        //同步返回签名，需要对字符串进行截取后，再验证签名
        String msg =resultStrBuilder.toString();
        String returnString = org.apache.commons.lang.StringUtils.substringBetween(msg,"response\":", ",\"sign\"");
        String returnSign = org.apache.commons.lang.StringUtils.substringBetween(msg,",\"sign\":\"","\"}");
        boolean isTrue=AlipaySignature.rsaCheckContent(returnString, returnSign, easypay_pub_key, "UTF-8");
        System.out.println("验证返回签名是否正确：" + isTrue);
    }

    public static boolean rsaVerifySign(StringBuilder resultStrBuilder, String easypay_pub_key , String sign_type)  throws Exception {
        //同步返回签名，需要对字符串进行截取后，再验证签名
        String msg =resultStrBuilder.toString();
        String returnString = org.apache.commons.lang.StringUtils.substringBetween(msg,"response\":", ",\"sign\"");
        String returnSign = org.apache.commons.lang.StringUtils.substringBetween(msg,",\"sign\":\"","\"}");
        boolean isTrue=false;
        if(KeyUtils.TEST_SM_ENCODE_TYPE.equals(sign_type)) {
            isTrue= SM2Utils.verifySign("".getBytes(), Base64.decode(easypay_pub_key.getBytes()),returnString.getBytes(),Util.hexToByte(returnSign));
            System.out.println("验证返回SM签名是否正确：" + isTrue);
        }else {
            isTrue=AlipaySignature.rsaCheckContent(returnString, returnSign, easypay_pub_key, "UTF-8");
            System.out.println("验证返回RSA签名是否正确：" + isTrue);
        }

        return isTrue;
    }



    //回调通知报文，验证签名
    public static void main(String[] args) throws Exception {
        String msg = "charset=utf-8&biz_content={\"ref_no\":\"11620190722110938208789\",\"notify_type\":\"WAIT_TRIGGER\",\"notify_id\":\"2019072205448161\",\"notify_time\":\"2019-07-22 12:37:24\",\"trade_no\":\"2019072205448161\",\"pay_no\":\"2019072205448161\",\"out_trade_no\":\"2019072200057\",\"payment_type\":\"1\",\"subject\":\"测试商品AAAAA\",\"body\":null,\"total_fee\":\"0.01\",\"amount\":\"0.01\",\"trade_status\":\"TRADE_FINISHED\",\"seller_email\":null,\"seller_id\":\"100000000081484\",\"buyer_id\":\"o8QAHwXd0BLZdYZzDQY0lDoHKnNQ\",\"buyer_email\":\"\",\"gmt_create\":\"2019-07-22 11:09:37\",\"gmt_payment\":\"2019-07-22 11:09:48\",\"is_success\":\"T\",\"is_total_fee_adjust\":\"0\",\"discount\":\"0\",\"gmt_logistics_modify\":\"2019-07-22 11:09:48\",\"price\":\"0.01\",\"quantity\":\"1\",\"seller_actions\":\"SEND_GOODS\"}&partner=100000000081484&sign=ZYgyNrwhKCEbHk9nJ0+XyswMjyBrDjrd9O2/8rV3/w1NTyoxua7UbYSapPB+gGtsK1nxzbBpU2yrKzRaEfPVsgMTP9vDs5rSgb3wALtrPJBqwSsobhQ/FidRFzc5WFt7vJBfdXaAAjuyly22z7QaQ/LEoEYHFa+sh5364/be+tA=&sign_type=RSA";

        String returnString = org.apache.commons.lang.StringUtils.substringBetween(msg,"biz_content=", "&partner=");
        String returnSign = org.apache.commons.lang.StringUtils.substringBetween(msg,"sign=","&sign_type");
        boolean isTrue=AlipaySignature.rsaCheckContent(returnString, returnSign, KeyUtils.TEST_EASYPAY_RSA_PUBLIC_KEY, "UTF-8");
        System.out.println("验证返回签名是否正确：" + isTrue);
    }
}
