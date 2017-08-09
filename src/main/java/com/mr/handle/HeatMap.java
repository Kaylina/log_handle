package com.mr.handle;

import java.util.Map;

/**
 * Created with Kaylina
 * Time: 2017/5/23 20:41
 * Description: 定义HeatMap结构体
 */
public class HeatMap {
    private String hm_v;
    private String hm_s;
    private String hm_p;
    private PageView pageView;

    public static HeatMap analyzeLog(String log) throws Exception {

        HeatMap heatMap = new HeatMap();
        PageView pageView = PageView.analyzeLog(log);
        heatMap.setPageView(pageView);
        Map<String, String> pm = pageView.getParamsMap();
        heatMap.setHm_p(Util.strHandle(pm.get("hm_p")));
        heatMap.setHm_s(Util.strHandle(pm.get("hm_s")));
        heatMap.setHm_v(Util.strHandle(pm.get("hm_v")));

        return heatMap;

    }

    @Override
    public String toString() {
        return
                pageView.toString() + '\t' +
                        hm_v + '\t' +
                        hm_s + '\t' +
                        hm_p;
    }

    public String getHm_v() {
        return hm_v;
    }

    public void setHm_v(String hm_v) {
        this.hm_v = hm_v;
    }

    public String getHm_s() {
        return hm_s;
    }

    public void setHm_s(String hm_s) {
        this.hm_s = hm_s;
    }

    public String getHm_p() {
        return hm_p;
    }

    public void setHm_p(String hm_p) {
        this.hm_p = hm_p;
    }

    public PageView getPageView() {
        return pageView;
    }

    public void setPageView(PageView pageView) {
        this.pageView = pageView;
    }

}
