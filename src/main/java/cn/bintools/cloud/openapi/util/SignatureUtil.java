package cn.bintools.cloud.openapi.util;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/***
 * 签证工具类
 * @author <a href="jian.huang@bintools.cn">Turbo</a>
 * @version 1.0.0 2021-06-2021/6/23-15:56
 */
public class SignatureUtil {

    public static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;
        char[] toDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int var5 = 0; i < l; ++i) {
            out[var5++] = toDigits[(240 & data[i]) >>> 4];
            out[var5++] = toDigits[15 & data[i]];
        }
        return out;
    }


    public static String getSignature(HashMap<String, Object> params, String secret) throws Exception {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, Object> sortedParams = new TreeMap<>(params);
        Set<Map.Entry<String, Object>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder baseString = new StringBuilder();
        for (Map.Entry<String, Object> param : entrys) {
            //sign参数 和 空值参数 不加入算法
            if (param.getValue() != null && !"".equals(param.getKey().trim()) && !"sign".equals(param.getKey().trim()) && !"key".equals(param.getKey().trim()) && !"".equals(param.getValue().toString().trim())) {
                baseString.append(param.getKey().trim()).append("=").append(param.getValue().toString().trim()).append("&");
            }
        }
        if (baseString.length() > 0) {
            baseString.deleteCharAt(baseString.length() - 1).append(secret);
        }
        // 使用MD5对待签名串求签
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(baseString.toString().getBytes("UTF-8"));
            return new String(encodeHex(bytes));
        } catch (GeneralSecurityException ex) {
            throw ex;
        }
    }

    public static void main(String[] args) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        result.put("appid", "abccc");
        result.put("currentTime", System.currentTimeMillis());
        result.put("source", "钉钉");
        String akkstdseeedsefg = getSignature(result, "akkstdseeedsefg");
        System.out.println(akkstdseeedsefg);
    }


}
