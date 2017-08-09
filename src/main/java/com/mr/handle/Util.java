package com.mr.handle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created with Kaylina
 * Time: 2017/5/23 18:47
 * Description: 工具类
 */
public class Util {

    public static final String URL_PREFIX = "http://www.test.com";
    public static final Log log = LogFactory.getLog(Util.class);
    public static final SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
    public static final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Created with Kaylina
     * Time: 2017-05-14 20:54
     * Description: 解析原始日志的第三号参数 客户端请求信息
     */
    public static Map<String, String> getUrlParamsMap(String path) throws Exception {

        Map<String, String> paramsMap = new LinkedHashMap<>();
        URL orgUrl = new URL(URL_PREFIX + path);
        String orgParams = orgUrl.getQuery();
        String[] orgParamsArr = orgParams.split("&");
        for (int i = 0; i < orgParamsArr.length; i++) {
            String paramPair = orgParamsArr[i];
            int splitLocation = paramPair.indexOf("=");
            if (splitLocation <= 0) {
                continue;
            }

            String leftField = paramPair.substring(0, splitLocation);
            String rightField = paramPair.substring(splitLocation + 1);
            try {
                paramsMap.put(leftField, URLDecoder.decode(rightField, "UTF-8").replaceAll("\r|\n|\t", " ").trim());
            } catch (Exception e) {
                log.error(e.getMessage());
                paramsMap.put(leftField, rightField);
            }

            // 从dl中截取出google广告相关字段
            if ("dl".equals(leftField)) {
                try {
                    String dlUrlString = URLDecoder.decode(rightField, "UTF-8").replaceAll("\r|\n|\t", " ").trim();
                    URL dlUrl = new URL(dlUrlString);
                    String dlParam = dlUrl.getQuery();
                    if (StringUtils.isBlank(dlParam)) {
                        continue;
                    }
                    String[] dlParamArr = dlParam.split("&");
                    if (PubMethod.isEmpty(dlParamArr)) {
                        continue;
                    }
                    for (int j = 0; j < dlParamArr.length; j++) {
                        String dlParamPair = dlParamArr[j];
                        int splitLoc = dlParamPair.indexOf("=");
                        if (splitLoc <= 0) {
                            continue;
                        }
                        String dlKey = dlParamPair.substring(0, splitLoc);
                        String dlValue = dlParamPair.substring(splitLoc + 1);

                        if ("utm_campaign".equals(dlKey) || "utm_source".equals(dlKey) ||
                                "utm_medium".equals(dlKey) || "utm_content".equals(dlKey) ||
                                "utm_term".equals(dlKey)) {

                            paramsMap.put(dlKey, URLDecoder.decode(dlValue, "UTF-8").replaceAll("\r|\n|\t", " ").trim());

                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }

        return paramsMap;
    }

    /**
     * Created with Kaylina
     * Time: 2017-05-14 20:53
     * Description: 获取cookie中需要的key
     */

    public static String getMzAdCookie(String cookie, String splitter, String keyStr) {

        if (StringUtils.isBlank(cookie) || StringUtils.isBlank(splitter) || StringUtils.isBlank(keyStr)) {
            return null;
        }
        String[] cookieArray = cookie.split(splitter);
        for (String cookieStr : cookieArray) {
            if (cookieStr.contains(keyStr)) {
                return cookieStr.substring(cookieStr.indexOf(keyStr) + keyStr.length());
            }
        }
        return null;
    }

    /**
     * Created with Kaylina
     * Time: 2017/5/15 11:29
     * Description: 处理时间格式
     */
    public static String handleTimeStr(String timeStr) {
        try {
            return f.format(format.parse(timeStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Created with Kaylina
     * Time: 2017/5/15 13:12
     * Description: 为空转换为 -
     * 不为空 不区分大小写 转换为正常参数
     */
    public static String strHandle(String str) {
        if (StringUtils.isEmpty(str)) {
            str = "-";
        } else {
            str = str.replaceAll("\r|\n|\t", "");
        }
        return str;
    }

    /**
     * Created with Kaylina
     * Time: 2017/5/15 15:46
     * Description: map转string
     */
    public static String transMapToString(Map map) {
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("=").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? ";" : "");
        }
        return sb.toString();
    }

}

