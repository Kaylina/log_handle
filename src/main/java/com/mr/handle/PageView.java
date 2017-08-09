package com.mr.handle;

import java.util.Map;

/**
 * Created with Kaylina
 * Time: 2017/5/23 18:10
 * Description: 定义PageView结构体
 */
public class PageView {
    private String stat_date;
    private String stat_hour;
    private String logdate;
    private String site_id;
    private String cid;
    private String ip;
    private String mzid;
    private String mz_media;
    private String referrer;
    private String language;
    private String vp_size;
    private String resolution;
    private String behavior;
    private String doc_title;
    private String doc_location;
    private String doc_host;
    private String doc_page;
    private String mz_ca;
    private String mz_sp;
    private String mz_kw;
    private String gg_name;
    private String gg_source;
    private String gg_medium;
    private String gg_content;
    private String gg_keywords;
    private String domain;
    private String sm;
    private String cookie;
    private String ua;
    private String url;
    private Map<String, String> paramsMap;

    /**
     * Created with Kaylina
     * Time: 2017/5/25 17:44
     * Description: 解析日志，并把参数放到对象里
     * 一步一个非空判断
     */
    public static PageView analyzeLog(String log) throws Exception {

        Map<String, String> paramsMap = null;
        String[] spiltLogs = log.split(" - ");
        if (spiltLogs[3].contains("/track_ajax")) {
            paramsMap = Util.getUrlParamsMap("/track_ajax?" + spiltLogs[4].split("\"")[1]);
        } else if (spiltLogs[3].contains("/track_proxy")) {
            paramsMap = Util.getUrlParamsMap(spiltLogs[3].split(" ")[1]);
        } else {
            throw new Exception("url不合法" + spiltLogs[3]);
        }

        String ip = spiltLogs[0].replaceAll("\"", "");
        String t = spiltLogs[2].split("\\[")[1].split("\\]")[0];
        String d = Util.handleTimeStr(t);
        String[] dateTime = d.split(" ");
        String stat_date = dateTime[0];
        String stat_hour = dateTime[1].substring(0, 2);
        String referrer = spiltLogs[8].replaceAll("\"", "");
        String ua = spiltLogs[9].replaceAll("\"", "");
        String cookie = spiltLogs[11].replaceAll("\"", "");
        String sm = Util.getMzAdCookie(cookie, ";", "sm=");
        String mz_id = Util.getMzAdCookie(cookie, ";", "a=");
        String domain = Util.getMzAdCookie(sm, ",", "dm:");
        // mz广告优先级为 referrer > cookie--sm
        String mz_ca = Util.getMzAdCookie(referrer, "&", "mz_ca=");
        if (PubMethod.isEmpty(mz_ca)) {
            mz_ca = Util.getMzAdCookie(sm, ",", "ca:");
        }
        String mz_sp = Util.getMzAdCookie(referrer, "&", "mz_sp=");
        if (PubMethod.isEmpty(mz_sp)) {
            mz_sp = Util.getMzAdCookie(sm, ",", "sp:");
        }
        String mz_kw = Util.getMzAdCookie(referrer, "&", "mz_kw=");
        if (PubMethod.isEmpty(mz_kw)) {
            mz_kw = Util.getMzAdCookie(sm, ",", "kw:");
        }

        PageView pv = new PageView();
        pv.setStat_date(Util.strHandle(stat_date)); //日期
        pv.setStat_hour(Util.strHandle(stat_hour)); //小时
        pv.setLogdate(Util.strHandle(d));
        pv.setIp(Util.strHandle(ip)); //客户端ip
        pv.setMz_media("-"); //预留字段，媒体
        pv.setReferrer(Util.strHandle(referrer)); //来源网页
        pv.setMzid(Util.strHandle(mz_id)); //秒针ID
        pv.setMz_ca(Util.strHandle(mz_ca)); //秒针广告活动ID
        pv.setMz_sp(Util.strHandle(mz_sp)); //秒针广告位ID
        pv.setMz_kw(Util.strHandle(mz_kw)); //秒针广告关键词ID
        pv.setDomain(Util.strHandle(domain));
        pv.setSm(Util.strHandle(sm)); // cookie 字段
        pv.setCookie(Util.strHandle(cookie)); // 解析cookie信息
        pv.setUa(Util.strHandle(ua));

        pv.setParamsMap(paramsMap);
        pv.setSite_id(Util.strHandle(paramsMap.get("tid").split("-")[1])); //站点ID
        pv.setCid(Util.strHandle(paramsMap.get("cid"))); //客户端唯一ID
        pv.setLanguage(Util.strHandle(paramsMap.get("ul"))); //用户语言
        pv.setVp_size(Util.strHandle(paramsMap.get("vp"))); //可视窗口大小
        pv.setResolution(Util.strHandle(paramsMap.get("sr"))); //屏幕分辨率
        pv.setBehavior(Util.strHandle(paramsMap.get("ub"))); //用户行为(滚动次数 输入次数 窗口调整次数 点击次数 鼠标移动次数 滑动次数移动 旋转次数移动 缩放次数)
        pv.setDoc_title(Util.strHandle(paramsMap.get("dt"))); //页面标题
        pv.setDoc_location(Util.strHandle(paramsMap.get("dl"))); //页面位置
        pv.setDoc_host(Util.strHandle(paramsMap.get("dh"))); //页面主机名
        pv.setDoc_page(Util.strHandle(paramsMap.get("dp"))); //页面路径
        pv.setGg_name(Util.strHandle(paramsMap.get("utm_campaign"))); //谷歌广告名称
        pv.setGg_source(Util.strHandle(paramsMap.get("utm_source"))); //谷歌广告来源
        pv.setGg_medium(Util.strHandle(paramsMap.get("utm_medium"))); //谷歌广告媒介
        pv.setGg_content(Util.strHandle(paramsMap.get("utm_content"))); //谷歌广告内容
        pv.setGg_keywords(Util.strHandle(paramsMap.get("utm_term"))); //谷歌广告关键字
        pv.setUrl(Util.strHandle(Util.transMapToString(paramsMap))); // 客户端请求

        return pv;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result
                .append(stat_date).append("\t")
                .append(stat_hour).append("\t")
                .append(logdate).append("\t")
                .append(site_id).append("\t")
                .append(cid).append("\t")
                .append(ip).append("\t")
                .append(mzid).append("\t")
                .append(mz_media).append("\t")
                .append(referrer).append("\t")
                .append(language).append("\t")
                .append(vp_size).append("\t")
                .append(resolution).append("\t")
                .append(behavior).append("\t")
                .append(doc_title).append("\t")
                .append(doc_location).append("\t")
                .append(doc_host).append("\t")
                .append(doc_page).append("\t")
                .append(mz_ca).append("\t")
                .append(mz_sp).append("\t")
                .append(mz_kw).append("\t")
                .append(gg_name).append("\t")
                .append(gg_source).append("\t")
                .append(gg_medium).append("\t")
                .append(gg_content).append("\t")
                .append(gg_keywords).append("\t")
                .append(domain).append("\t")
                .append(sm).append("\t")
                .append(cookie).append("\t")
                .append(ua).append("\t")
                .append(url);

        return result.toString();
    }


    public String getStat_date() {
        return stat_date;
    }

    public void setStat_date(String stat_date) {
        this.stat_date = stat_date;
    }

    public String getStat_hour() {
        return stat_hour;
    }

    public void setStat_hour(String stat_hour) {
        this.stat_hour = stat_hour;
    }

    public String getLogdate() {
        return logdate;
    }

    public void setLogdate(String logdate) {
        this.logdate = logdate;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMzid() {
        return mzid;
    }

    public void setMzid(String mzid) {
        this.mzid = mzid;
    }

    public String getMz_media() {
        return mz_media;
    }

    public void setMz_media(String mz_media) {
        this.mz_media = mz_media;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVp_size() {
        return vp_size;
    }

    public void setVp_size(String vp_size) {
        this.vp_size = vp_size;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getDoc_title() {
        return doc_title;
    }

    public void setDoc_title(String doc_title) {
        this.doc_title = doc_title;
    }

    public String getDoc_location() {
        return doc_location;
    }

    public void setDoc_location(String doc_location) {
        this.doc_location = doc_location;
    }

    public String getDoc_host() {
        return doc_host;
    }

    public void setDoc_host(String doc_host) {
        this.doc_host = doc_host;
    }

    public String getDoc_page() {
        return doc_page;
    }

    public void setDoc_page(String doc_page) {
        this.doc_page = doc_page;
    }

    public String getMz_ca() {
        return mz_ca;
    }

    public void setMz_ca(String mz_ca) {
        this.mz_ca = mz_ca;
    }

    public String getMz_sp() {
        return mz_sp;
    }

    public void setMz_sp(String mz_sp) {
        this.mz_sp = mz_sp;
    }

    public String getMz_kw() {
        return mz_kw;
    }

    public void setMz_kw(String mz_kw) {
        this.mz_kw = mz_kw;
    }

    public String getGg_name() {
        return gg_name;
    }

    public void setGg_name(String gg_name) {
        this.gg_name = gg_name;
    }

    public String getGg_source() {
        return gg_source;
    }

    public void setGg_source(String gg_source) {
        this.gg_source = gg_source;
    }

    public String getGg_medium() {
        return gg_medium;
    }

    public void setGg_medium(String gg_medium) {
        this.gg_medium = gg_medium;
    }

    public String getGg_content() {
        return gg_content;
    }

    public void setGg_content(String gg_content) {
        this.gg_content = gg_content;
    }

    public String getGg_keywords() {
        return gg_keywords;
    }

    public void setGg_keywords(String gg_keywords) {
        this.gg_keywords = gg_keywords;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }


}
