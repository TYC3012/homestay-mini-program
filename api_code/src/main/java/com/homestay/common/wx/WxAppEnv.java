package com.homestay.common.wx;

import com.alibaba.fastjson2.JSON;

import com.homestay.common.exception.ServiceException;
import com.homestay.common.utils.http.HttpUtils;
import org.apache.commons.codec.binary.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 微信配置文件工具集成类
 * @Author shylock.online
 * @Date 15:58 2023/6/13 0013
 **/
public class WxAppEnv {
    public static final String APPID = "wx7ba1e4a2a17ee45d";
    public static final String APPSECRET = "cb2b88a6b2d06a3a8f000070435c1c95";

    public static final String GETOPENID_URL = "https://api.weixin.qq.com/sns/jscode2session";

    //获取手机号  https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/getPhoneNumber.html
    public static final String GET_PHONENUMBER = "https://api.weixin.qq.com/wxa/business/getuserphonenumber" +
            "?access_token=";

    //获取token
    public static final String GET_ACCESSTOKEN = "https://api.weixin.qq" +
            ".com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;


    /**
     * AES加密/解密工具，含微信平台(小程序/开放平台)加密数据解密
     *
     * @author shylock.online
     * @Remarks 微信平台(小程序 / 开放平台)加密数据解密依赖org.bouncycastle:bcprov-jdk16及commons-codec:commons-codec
     * @Example <pre>
     * //微信平台(小程序/开放平台)加密数据解密
     * String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
     * String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
     * String iv = "r7BXXKkLb8qrSNn05n0qiA==";
     * System.out.println("微信数据解密："+AESUtils.wxDecrypt(encryptedData, sessionKey, iv));
     * //普通 加密/解密
     * String key = getKey();
     * System.out.println("密钥:"+key);
     * String encData = encrypt("Hello Word", key);
     * System.out.println("加密密文:"+encData);
     * System.out.println("解密原文："+decrypt(encData, key));
     * </pre>
     * maven坐标
     * <p>
     * <dependency>
     * <groupId>org.bouncycastle</groupId>
     * <artifactId>bcprov-jdk16</artifactId>
     * <version>1.46</version>
     * </dependency>
     * <dependency>
     * <groupId>commons-codec</groupId>
     * <artifactId>commons-codec</artifactId>
     * <version>1.15</version>
     * </dependency>
     * </p>
     */
    public static class AESUtils {

        //CipherMode：算法/模式/填充
        private static final String CipherMode = "AES/ECB/PKCS5Padding";
        //CipherModeForWX：算法/模式/填充 微信平台要求算法为：AES-128-CBC，数据采用PKCS#7填充
        private static final String CipherModeForWX = "AES/CBC/PKCS7Padding";
        //密钥长度 可以是128、256等
        private static final int keysize = 128;

        //是否导入provider
        public static boolean initialized = true;

        private static final Charset CHARSET = Charset.forName("utf-8");
        //填充的位数
        private static final int BLOCK_SIZE = 32;

        /**
         * 导入provider
         */
        public static boolean initialize() {
            if (!initialized) {
                return false;
            }
            Security.addProvider(new BouncyCastleProvider());
            initialized = true;
            return initialized;
        }

        /**
         * 生成一个AES密钥对象
         *
         * @return
         */
        public static SecretKeySpec generateKey() {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(keysize, new SecureRandom());
                SecretKey secretKey = kgen.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
                return key;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 生成一个AES密钥字符串
         *
         * @return
         */
        public static String generateKeyString() {
            return byte2hex(generateKey().getEncoded());
        }

        /**
         * 加密字节数据
         *
         * @param content
         * @param key
         * @return
         */
        public static byte[] encrypt(byte[] content, byte[] key) {
            try {
                Cipher cipher = Cipher.getInstance(CipherMode);
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
                byte[] result = cipher.doFinal(content);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 通过byte[]类型的密钥加密String
         *
         * @param content
         * @param key
         * @return 16进制密文字符串
         */
        public static String encrypt(String content, byte[] key) {
            try {
                Cipher cipher = Cipher.getInstance(CipherMode);
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
                byte[] data = cipher.doFinal(content.getBytes("UTF-8"));
                String result = byte2hex(data);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 通过String类型的密钥加密String
         *
         * @param content
         * @param key
         * @return 16进制密文字符串
         */
        public static String encrypt(String content, String key) {
            byte[] data = null;
            try {
                data = content.getBytes("UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            data = encrypt(data, new SecretKeySpec(hex2byte(key), "AES").getEncoded());
            String result = byte2hex(data);
            return result;
        }

        /**
         * 通过byte[]类型的密钥解密byte[]
         *
         * @param content
         * @param key
         * @return
         */
        public static byte[] decrypt(byte[] content, byte[] key) {
            try {
                Cipher cipher = Cipher.getInstance(CipherMode);
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
                byte[] result = cipher.doFinal(content);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // 生成iv
        public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(iv));
            return params;
        }

        /**
         * 通过String类型的密钥 解密String类型的密文
         *
         * @param content
         * @param key
         * @return
         */
        public static String decrypt(String content, String key) {
            byte[] data = null;
            try {
                data = hex2byte(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            data = decrypt(data, hex2byte(key));
            if (data == null)
                return null;
            String result = null;
            try {
                result = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         * 通过byte[]类型的密钥解密byte[]，主要用于微信平台(小程序/开放平台)数据解密
         *
         * @param content
         * @param key
         * @return
         */
        public static byte[] wxDecrypt(byte[] content, byte[] key, byte[] iv) {
            initialize();
            try {
                Cipher cipher = Cipher.getInstance(CipherModeForWX);
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), generateIV(iv));// 初始化
                byte[] result = cipher.doFinal(content);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 微信平台(小程序/开放平台)数据解密
         *
         * @param content
         * @param sessionKey
         * @param iv
         * @return
         */
        public static String wxDecrypt(String content, String sessionKey, String iv) {
            String result = "";
            try {
                byte[] resultByte = wxDecrypt(Base64.decodeBase64(content), Base64.decodeBase64(sessionKey),
                        Base64.decodeBase64(iv));
                if (null != resultByte && resultByte.length > 0) {
                    result = new String(PKCS7Decode(resultByte));
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return result;
        }

        /**
         * 通过byte[]类型的密钥 解密String类型的密文
         *
         * @param content
         * @param key
         * @return
         */
        public static String decrypt(String content, byte[] key) {
            try {
                Cipher cipher = Cipher.getInstance(CipherMode);
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
                byte[] data = cipher.doFinal(hex2byte(content));
                return new String(data, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 字节数组转成16进制字符串
         *
         * @param b
         * @return
         */
        public static String byte2hex(byte[] b) { // 一个字节的数，
            StringBuffer sb = new StringBuffer(b.length * 2);
            String tmp;
            for (int n = 0; n < b.length; n++) {
                // 整数转成十六进制表示
                tmp = (Integer.toHexString(b[n] & 0XFF));
                if (tmp.length() == 1) {
                    sb.append("0");
                }
                sb.append(tmp);
            }
            return sb.toString().toUpperCase(); // 转成大写
        }

        /**
         * byte数组转化为16进制字符串
         *
         * @param bytes
         * @return
         */
        private static String byteToHexString(byte[] bytes) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String strHex = Integer.toHexString(bytes[i]);
                if (strHex.length() > 3) {
                    sb.append(strHex.substring(6));
                } else {
                    if (strHex.length() < 2) {
                        sb.append("0" + strHex);
                    } else {
                        sb.append(strHex);
                    }
                }
            }
            return sb.toString();
        }

        /**
         * 将hex字符串转换成字节数组
         *
         * @param inputString
         * @return
         */
        private static byte[] hex2byte(String inputString) {
            if (inputString == null || inputString.length() < 2) {
                return new byte[0];
            }
            inputString = inputString.toLowerCase();
            int l = inputString.length() / 2;
            byte[] result = new byte[l];
            for (int i = 0; i < l; ++i) {
                String tmp = inputString.substring(2 * i, 2 * i + 2);
                result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
            }
            return result;
        }


        /****
         * 获取对称加密随机生成的秘钥串
         *
         * @return
         */
        public static String getKey() {
            try {
                KeyGenerator kg = KeyGenerator.getInstance("AES");
                kg.init(keysize);
                SecretKey sk = kg.generateKey();
                byte[] b = sk.getEncoded();
                String s = byteToHexString(b);
                return s;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 获得对明文进行补位填充的字节.
         *
         * @param count 需要进行填充补位操作的明文字节个数
         * @return 补齐用的字节数组
         */
        public static byte[] PKCS7Encode(int count) {
            // 计算需要填充的位数
            int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
            if (amountToPad == 0) {
                amountToPad = BLOCK_SIZE;
            }
            // 获得补位所用的字符
            char padChr = PKCS7Chr(amountToPad);
            String tmp = new String();
            for (int index = 0; index < amountToPad; index++) {
                tmp += padChr;
            }
            return tmp.getBytes(CHARSET);
        }

        /**
         * 删除解密后明文的补位字符
         *
         * @param decrypted 解密后的明文
         * @return 删除补位字符后的明文
         */
        public static byte[] PKCS7Decode(byte[] decrypted) {
            int pad = decrypted[decrypted.length - 1];
            if (pad < 1 || pad > 32) {
                pad = 0;
            }
            return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
        }

        /**
         * 将数字转化成ASCII码对应的字符，用于对明文进行补码
         *
         * @param a 需要转化的数字
         * @return 转化得到的字符
         */
        public static char PKCS7Chr(int a) {
            byte target = (byte) (a & 0xFF);
            return (char) target;
        }


       /* public static void main(String[] args) {
            //微信平台(小程序/开放平台)加密数据解密
            String encryptedData =
            "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB
            +3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6
            /1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W
            //FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E
            /r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns
            /8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20
            f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
            String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
            String iv = "r7BXXKkLb8qrSNn05n0qiA==";
            System.out.println("微信数据解密："+AESUtils.wxDecrypt(encryptedData, sessionKey, iv));
            //普通 加密/解密
            String key = getKey();
            System.out.println("密钥:"+key);
            String encData = encrypt("Hello Word", key);
            System.out.println("加密密文:"+encData);
            System.out.println("解密原文："+decrypt(encData, key));
        }*/

    }

    /**
     * 获取微信token
     *
     * @return
     */
    public static WxAccessTokenDTo getAccessToken() {
        String accessTokenJSON = HttpUtils.sendGet(WxAppEnv.GET_ACCESSTOKEN);
        WxAccessTokenDTo accessTokenDTo = JSON.parseObject(accessTokenJSON, WxAccessTokenDTo.class);
        if (accessTokenDTo == null || com.homestay.common.utils.StringUtils.isBlank(accessTokenDTo.getAccess_token())) {
            throw new ServiceException("请求token失败");
        }
        return accessTokenDTo;
    }

    /**
     * 获取微信手机号
     *
     * @param accessToken
     * @param code
     * @return
     */
    public static String getWxPhone(String accessToken, String code) {
        String phoneNumberUrl = WxAppEnv.GET_PHONENUMBER + accessToken;
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("code", code);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(queryMap, headers);
        ResponseEntity<HashMap> mapResponseEntity = restTemplate.postForEntity(phoneNumberUrl, httpEntity,
                HashMap.class);
        HashMap<String, Object> response = mapResponseEntity.getBody();
        if (response != null && response.get("errcode").toString().equals("0")) {
            Object phone_info = response.get("phone_info");
            HashMap phone_infoMap = JSON.parseObject(JSON.toJSONString(phone_info), HashMap.class);
            if (phone_infoMap.get("phoneNumber") != null) {
                return phone_infoMap.get("phoneNumber").toString();
            }
        }
        return null;
    }

}


