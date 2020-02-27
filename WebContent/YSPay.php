<?php
/**
    易生支付，封装类
 */
    class YSPay {
        private $baseHost="https://test_nucc.bhecard.com:9088/api_gateway.do";
        public $merchant_id="900029000000354";
        private $DES_ENCODE_KEY="CueaiPrW9sIskbn9qkoPh9J3";
        private $signKey="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIqUuxd92eEBXVneDWhfNP6XCkLcGBO1YAulexKX+OdlfZzB/4NNHkOAQQy84k3ZgIUPIk5hewLbA+XGrk9Wih5HG3ZQeFugeoTcx3vwo7AQv7KnmcKEWFNlOr/EhB3JndmcQnBRsIRRdCP+7nobfBqU0jS8dnpcQX1AtBRZRnkfAgMBAAECgYAe+u70ansZ1Q9EduKycY5MWAHAPqnXRhXppJ3l4zmOqV6ye6Aef1ADsRlZuqQw2S3lESQPN7WjRskRRiBTtjn8Atul9YeC7+QirP1K8seUP5gKB4bcjlzzl1m5dmxldkptJAmdzwYn8PRTW0+tFVyEaD/B8hKGxij4Gew0e8bwCQJBAOboG3ttBESsG2cAtmP1MfKRTjVdY7qRMXzBybcAeobBbmgCQgybVXXgjbGai+qwrQqcVRIp6p1yDWTZxVSuDWsCQQCZpBhcayOCMZR6F8dQJSuSSSIJw/GGN7IXfMYIqLxA2oGzlQ0B1DffOUe2wrid+WdpLuYCz2LYPQHDEgYM1dwdAkEAnfwhEYm9ad73wLnUEQAqdHTGtex316aP3XQZt4Q0UQ73o2IoHsgI6OYDDIlZQfIv8xqTeiIDzEXEtEPrp8yOkQJBAIWAzFZKFqHD2UO6M8vVcKX9fGFF7TH2ZX75Qc82Z9ZmyDs2sgW71QzX5hPN4cQLeqswQFeCw14orMZHfBBdKJUCQQDiWYk85okRugsWtxeJFhMEt2oUT+Kd8Yz5Aiz3J9XIS+zWtJrFlv+hXkVedPJ3xtBF32DZrCbxDn3UjXipRaCP";
        private $easy_public_key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2WTfvas1JvvaRuJWIKmKlBLmkRvr2O7Fu3k/zvhJs+X1JQorPWq/yZduY6HKu0up7Qi3T6ULHWyKBS1nRqhhHpmLHnI3sIO8E/RzNXJiTd9/bpXMv+H8F8DW5ElLxCIVuwHBROkBLWS9fIpslkFPt+r13oKFnuWhXgRr+K/YkJQIDAQAB";
        private $seller_email="13601470343@qq.com";
        private $info;
        public  $sign,$biz_content;
        public function __construct(){
            $this->info = null;
            $this->sign = null;
            $this->biz_content = null;
        }
        public function SetInfo($key,$val){
            $this->info[$key]=$val;
        }
        public function GetAll(){
            return $this->info;
        }
		//发送生成签名
        private function sign_json (){
            $this->biz_content = json_encode($this->info,256);
            $this->sign = $this->sign_encrypt($this->convertPrivateKey(),$this->biz_content);
			echo $sign;
        }
        public function post_return($service){
            $this->sign_json();
            $post_data =  "sign=".urlencode($this->sign)."&charset=UTF-8&biz_content=".urlencode($this->biz_content)."&partner=".$this->merchant_id."&sign_type=RSA&service=".$service;
            $service_return_name = str_replace(".","_",$service).'_response';
            $return_data = $this->request($this->baseHost,true,"post",$post_data);
            $return_array = json_decode($return_data,1);
            $trade_response = json_encode($return_array[$service_return_name],256);
			//返回验签
            $ras_jiemi = $this->sign_decrypt($this->convertPublicKey(),$trade_response,$return_array['sign']);
			if($return_array[$service_return_name]['code'] == '00'){
				//判断请求后的签名用easy_public_key验签，正确true，错误false
                if($ras_jiemi){
					//返回请求的内容
					return $return_array;
				}
			}
            
        }
        //使用url封装请求方法
        //封装可以请求http和https
        //可以发送get和post的请求方式
        function request($url,$https=true,$method='get',$data=null){
            //1.初识化curl
            $ch = curl_init($url);
            //2.根据实际请求需求进行参数封装
            //返回数据不直接输出
            curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
            //如果是https请求
            if($https === true){
                curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,false);
                curl_setopt($ch,CURLOPT_SSL_VERIFYHOST,false);
            }
            //如果是post请求
            if($method === 'post'){
                //开启发送post请求选项
                curl_setopt($ch,CURLOPT_POST,true);
                //发送post的数据
                curl_setopt($ch,CURLOPT_POSTFIELDS,$data);
            }
            //3.发送请求
            $result = curl_exec($ch);
            //4.返回返回值，关闭连接
            curl_close($ch);
            return $result;
        }
        private  function sign_encrypt($private_key,$data){
            $pi_key =  openssl_pkey_get_private($private_key);
            openssl_sign($data,$signature,$pi_key,OPENSSL_ALGO_SHA1);//生成签名
            $signature = base64_encode($signature);
            openssl_free_key($pi_key);
			echo $signature \r;
            return $signature;
        }
        private function sign_decrypt($public_key ,$sign_str,$encrypted){
            $pu_key = openssl_pkey_get_public($public_key);
            $verify = openssl_verify($sign_str, base64_decode($encrypted), $pu_key, OPENSSL_ALGO_SHA1);
            openssl_free_key($pu_key);
            return $verify==1;//false or true
        }
        /*
			转换公钥钥匙
		*/
        public function convertPublicKey() {
            $publicKey = $this->easy_public_key;
            //判断是否传入公钥内容
            $public_key_string = !empty($publicKey) ? $publicKey : '';
            //64位换行公钥钥内容
            $public_key_string = chunk_split($public_key_string, 64, "\r\n");
            //公钥头部
            $public_key_header = "-----BEGIN PUBLIC KEY-----\r\n";
            //公钥尾部
            $public_key_footer = "-----END PUBLIC KEY-----";
            //完整公钥拼接
            $public_key_string = $public_key_header.$public_key_string.$public_key_footer;
            return $public_key_string;
        }
        /*
			转换私钥钥匙
		*/
        public function convertPrivateKey() {
            $privateKey = $this->signKey;
            //判断是否传入私钥内容
            $public_key_string = !empty($privateKey) ? $privateKey : '';
            //64位换行公钥钥内容
            $public_key_string = chunk_split($public_key_string, 64, "\r\n");
            //私钥头部
            $public_key_header = "-----BEGIN RSA PRIVATE KEY-----\r\n";
            //私钥尾部
            $public_key_footer = "-----END RSA PRIVATE KEY-----";
            //完整私钥拼接
            $public_key_string = $public_key_header.$public_key_string.$public_key_footer;
			print_r($public_key_string)
            // return $public_key_string;
        }
    }

?>
<?php 
	//调用
		$ys = new YSPay();
	    $ys->SetInfo("out_trade_no",time().rand(1000000,9999999));
	    $ys->SetInfo("merchant_id",$ys->merchant_id);
	    $ys->SetInfo("acc","6214835650183187");
	    $ys->SetInfo("name","张山");
	    $ys->SetInfo("amount","100");
	    $ys->SetInfo("acc_type",2);
	    $ys->SetInfo("notify_url","http://www.baidu.com");
        $service="trade.acc.dsfpay.newPay";
	    $array = $ys->post_return($service);//返回true,false
        print_r($array);
?>