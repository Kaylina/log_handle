import com.mr.handle.PubMethod;
import com.mr.handle.Util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with Kaylina
 * Time: 2017/6/6 13:56
 * Description: Map方法一次性过滤全部日志
 */
/*
拆分的12个字段:
        arr[0] 含义：客户端ip地址 示例：112.114.181.50
        arr[1] 含义：客户端用户名,用户Auth Basic Module验证 示例："-"
        arr[2] 含义：$time_local 通用日志格式下的本地时间。 示例：[14/May/2015:16:41:10 +0800]
        arr[3] 含义：客户端请求           示例： GET /track_proxy?tid=dc-860&cid=149110324618359163&dr=http%3A%2F%2Fwww.sina.com.cn%2F&sr=1366*768&vp=931*453&de=gb2312&sd=24-bit&ul=zh-cn&je=1&fl=25.0%20r0&t=timing&ni=1&dl=http%3A%2F%2Fwww.yoka.com%2Ffashion%2F%3Fpopularizeid%3D184&dt=%E6%97%B6%E8%A3%85%E9%A2%91%E9%81%93_YOKA%E6%97%B6%E5%B0%9A%E7%BD%91&ub=0-0-0-0-0-0-0-0&plt=1060&pdt=3&rrt=136&dit=868&clt=868&z=1736593629 HTTP/1.1
        arr[4] 含义：客户端请求的报文体 示例："-" POST 请求会把参数填入进去
        arr[5] 含义：记录请求状态 示例："200"
        arr[6] 含义： 发送给客户端的字节数，不包括响应头的大小； 该变量与Apache模块mod_log_config里的“%B”参数兼容。 示例："43"
        arr[7] 含义：请求头中的Request Headers的Accept-Language: en-US  示例："zh-CN"
        arr[8] 含义：记录从哪个页面链接访问过来的 示例："http://www.hnair.com/
        arr[9] 含义：记录客户端浏览器相关信息 示例  "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; LCJB)"
        arr[10]含义：客户端ip地址 示例："-"
        arr[11]含义：用户cookie 示例："syn=1_27f15b81_57713c94_57713c94_1; a=UB3aZ0GffT92; tsc=3_55f198c4_577f6f73_df_115"
*/

public class MapHandle {
    // 对日志进行针对性的分析
    public static Map<String, Object> analyzeLog(String log) throws Exception {
        // 把原始日志切分
        String[] spiltLogs = log.split(" - ");
        // 第一个参数 ip地址
        String ip = spiltLogs[0].split("\"")[1];
        // 处理第三个参数 日期
        String t = spiltLogs[2].split("\\[")[1].split("\\]")[0];
        String d = Util.handleTimeStr(t);
        String[] dateTime = d.split(" ");
        String stat_date = dateTime[0];
        String stat_hour = dateTime[1].substring(0, 2);

        // 处理第四个参数  客户端的请求
        Map<String, String> paramsMap = null;
        if (spiltLogs[3].contains("/track_ajax")) {
            paramsMap = Util.getUrlParamsMap("/track_ajax?" + spiltLogs[4].split("\"")[1]);

        } else if (spiltLogs[3].contains("/track_proxy")) {
            paramsMap = Util.getUrlParamsMap(spiltLogs[3].split(" ")[1]);
        }

        System.out.println("url长参数---" + paramsMap);

        String referrer = spiltLogs[8].replace("\"", "");
        // 处理第十号参数 记录客户端浏览器相关信息 ua
        String ua = spiltLogs[9].split("\"")[1];

        // 处理第十二号参数 cookie
        String cookie = spiltLogs[11].replace("\"", "");
        String sm = Util.getMzAdCookie(cookie, ";", "sm=");
        String mz_id = Util.getMzAdCookie(cookie, ";", "a=");
        String domain = Util.getMzAdCookie(sm, ",", "dm:");

        //mz广告相关参数以referrer为准，然后是cookie的sm中
        String mz_ca = Util.getMzAdCookie(referrer, "&", "mz_ca=");
        if (PubMethod.isEmpty(mz_ca))
            mz_ca = Util.getMzAdCookie(sm, ",", "ca:");
        String mz_sp = Util.getMzAdCookie(referrer, "&", "mz_sp=");
        if (PubMethod.isEmpty(mz_sp))
            mz_sp = Util.getMzAdCookie(sm, ",", "sp:");
        String mz_kw = Util.getMzAdCookie(referrer, "&", "mz_kw=");
        if (PubMethod.isEmpty(mz_kw))
            mz_kw = Util.getMzAdCookie(sm, ",", "kw:");

        Map<String, Object> etlMap = new LinkedHashMap<String, Object>();
        etlMap.put("stat_date", stat_date);  // 日期
        etlMap.put("stat_hour", stat_hour); //时间
        etlMap.put("logdate", d);

        // 解析客户端请求
        etlMap.put("site_id", Util.strHandle(paramsMap.get("tid").split("-")[1])); //跟踪ID、站点ID
        etlMap.put("cid", Util.strHandle(paramsMap.get("cid"))); //客户端唯一ID
        etlMap.put("ip", Util.strHandle(ip));  // 客户端ip
        etlMap.put("mz_id", Util.strHandle(mz_id)); //秒针ID
        etlMap.put("mz_media", "-"); //预留字段，媒体
        etlMap.put("referrer", Util.strHandle(referrer)); //来源网页
        etlMap.put("language", Util.strHandle(paramsMap.get("ul"))); //用户语言
        etlMap.put("vp_size", Util.strHandle(paramsMap.get("vp"))); //可视窗口大小
        etlMap.put("resolution", Util.strHandle(paramsMap.get("sr"))); //屏幕分辨率

        etlMap.put("behavior", Util.strHandle(paramsMap.get("ub"))); //用户行为(滚动次数 输入次数 窗口调整次数 点击次数 鼠标移动次数 滑动次数移动 旋转次数移动 缩放次数)
        etlMap.put("doc_title", Util.strHandle(paramsMap.get("dt"))); //页面标题
        etlMap.put("doc_location", Util.strHandle(paramsMap.get("dl"))); //页面位置
        etlMap.put("doc_host", Util.strHandle(paramsMap.get("dh"))); //页面主机名
        etlMap.put("doc_page", Util.strHandle(paramsMap.get("dp"))); //页面路径

        etlMap.put("mz_ca", Util.strHandle(mz_ca));
        etlMap.put("mz_sp", Util.strHandle(mz_sp));
        etlMap.put("mz_kw", Util.strHandle(mz_kw));
        etlMap.put("gg_name", Util.strHandle(paramsMap.get("utm_campaign"))); //谷歌广告名称
        etlMap.put("gg_source", Util.strHandle(paramsMap.get("utm_source"))); //谷歌广告来源
        etlMap.put("gg_medium", Util.strHandle(paramsMap.get("utm_medium"))); //谷歌广告媒介
        etlMap.put("gg_content", Util.strHandle(paramsMap.get("utm_content"))); //谷歌广告内容
        etlMap.put("gg_keywords", Util.strHandle(paramsMap.get("utm_term"))); //谷歌广告关键字

        // t=event 增加4个参数
        if ((spiltLogs[3].contains("t=event")) || (spiltLogs[4].contains("t=event"))) {
            if (!paramsMap.containsKey(";caid=")) {
                etlMap.put("ec", Util.strHandle(paramsMap.get("ec"))); //事件分类
                etlMap.put("ea", Util.strHandle(paramsMap.get("ea"))); //事件点击
                etlMap.put("el", Util.strHandle(paramsMap.get("el"))); //事件标签
                etlMap.put("ev", Util.strHandle(paramsMap.get("ev"))); //事件价值

            } else if (paramsMap.containsKey(";caid=")) {
                etlMap.put("caid", Util.strHandle(paramsMap.get("caid"))); //高级事件id
                for (int i = 1; i < 21; i++) {
                    etlMap.put("cal" + i, Util.strHandle(paramsMap.get("cal" + i)));//高级事件标签组
                }
                for (int i = 1; i < 21; i++) {
                    etlMap.put("cav" + i, Util.strHandle(paramsMap.get("cal" + i)));//高级事件标签组
                }
            }
        }
        // t=heatmap 增加4个参数
        if ((spiltLogs[3].contains("t=heatmap")) || (spiltLogs[4].contains("t=heatmap"))) {
            etlMap.put("hm_v", Util.strHandle(paramsMap.get("hm_v"))); //页面宽度*页面高度
            etlMap.put("hm_s", Util.strHandle(paramsMap.get("hm_s"))); //切片名称
            etlMap.put("hm_p", Util.strHandle(paramsMap.get("hm_p"))); //坐标

        }
        etlMap.put("domain", Util.strHandle(domain));
        etlMap.put("sm", Util.strHandle(sm)); // cookie 字段
        etlMap.put("cookie", Util.strHandle(cookie)); // 解析cookie信息
        etlMap.put("ua", Util.strHandle(ua));  // ua
        etlMap.put("url", Util.strHandle(Util.transMapToString(paramsMap))); // 客户端请求

        return etlMap;
    }

}
