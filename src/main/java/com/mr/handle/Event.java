package com.mr.handle;

import java.util.Map;

/**
 * Created with Kaylina
 * Time: 2017/5/23 20:26
 * Description: 定义Event结构体
 */
public class Event {
    private String ec;
    private String ea;
    private String el;
    private String ev;
    private PageView pageView;

    public static Event analyzeLog(String log) throws Exception {

        Event event = new Event();
        PageView pageView = PageView.analyzeLog(log);
        event.setPageView(pageView);
        Map<String, String> pm = pageView.getParamsMap();

        event.setEc(Util.strHandle(pm.get("ec")));
        event.setEa(Util.strHandle(pm.get("ea")));
        event.setEl(Util.strHandle(pm.get("el")));
        event.setEv(Util.strHandle(pm.get("ev")));

        return event;

    }

    @Override
    public String toString() {
        return
                pageView.toString() + '\t' +
                        ec + '\t' +
                        ea + '\t' +
                        el + '\t' +
                        ev;
    }

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getEa() {
        return ea;
    }

    public void setEa(String ea) {
        this.ea = ea;
    }

    public String getEl() {
        return el;
    }

    public void setEl(String el) {
        this.el = el;
    }

    public String getEv() {
        return ev;
    }

    public void setEv(String ev) {
        this.ev = ev;
    }

    public PageView getPageView() {
        return pageView;
    }

    public void setPageView(PageView pageView) {
        this.pageView = pageView;
    }

}

