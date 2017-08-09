package com.mr.handle;

import java.util.Map;

/**
 * Created with Kaylina
 * Time: 2017/5/23 20:28
 * Description: 定义customer_event结构体
 */
public class CusEvent {
    private String caid;
    private String cal1;
    private String cal2;
    private String cal3;
    private String cal4;
    private String cal5;
    private String cal6;
    private String cal7;
    private String cal8;
    private String cal9;
    private String cal10;
    private String cal11;
    private String cal12;
    private String cal13;
    private String cal14;
    private String cal15;
    private String cal16;
    private String cal17;
    private String cal18;
    private String cal19;
    private String cal20;
    private String cav1;
    private String cav2;
    private String cav3;
    private String cav4;
    private String cav5;
    private String cav6;
    private String cav7;
    private String cav8;
    private String cav9;
    private String cav10;
    private String cav11;
    private String cav12;
    private String cav13;
    private String cav14;
    private String cav15;
    private String cav16;
    private String cav17;
    private String cav18;
    private String cav19;
    private String cav20;
    private PageView pageView;


    public static CusEvent analyzeLog(String log) throws Exception {

        PageView pageView = PageView.analyzeLog(log);
        CusEvent cusEvent = new CusEvent();
        cusEvent.setPageView(pageView);
        Map<String, String> pm = pageView.getParamsMap();
        cusEvent.setCaid(Util.strHandle(pm.get("caid")));
        cusEvent.setCal1(Util.strHandle(pm.get("cal1")));
        cusEvent.setCal2(Util.strHandle(pm.get("cal2")));
        cusEvent.setCal3(Util.strHandle(pm.get("cal3")));
        cusEvent.setCal4(Util.strHandle(pm.get("cal4")));
        cusEvent.setCal5(Util.strHandle(pm.get("cal5")));
        cusEvent.setCal6(Util.strHandle(pm.get("cal6")));
        cusEvent.setCal7(Util.strHandle(pm.get("cal7")));
        cusEvent.setCal8(Util.strHandle(pm.get("cal8")));
        cusEvent.setCal9(Util.strHandle(pm.get("cal9")));
        cusEvent.setCal10(Util.strHandle(pm.get("cal10")));
        cusEvent.setCal11(Util.strHandle(pm.get("cal11")));
        cusEvent.setCal12(Util.strHandle(pm.get("cal12")));
        cusEvent.setCal13(Util.strHandle(pm.get("cal13")));
        cusEvent.setCal14(Util.strHandle(pm.get("cal14")));
        cusEvent.setCal15(Util.strHandle(pm.get("cal15")));
        cusEvent.setCal16(Util.strHandle(pm.get("cal16")));
        cusEvent.setCal17(Util.strHandle(pm.get("cal17")));
        cusEvent.setCal18(Util.strHandle(pm.get("cal18")));
        cusEvent.setCal19(Util.strHandle(pm.get("cal19")));
        cusEvent.setCal20(Util.strHandle(pm.get("cal20")));
        cusEvent.setCav1(Util.strHandle(pm.get("cav1")));
        cusEvent.setCav2(Util.strHandle(pm.get("cav2")));
        cusEvent.setCav3(Util.strHandle(pm.get("cav3")));
        cusEvent.setCav4(Util.strHandle(pm.get("cav4")));
        cusEvent.setCav5(Util.strHandle(pm.get("cav5")));
        cusEvent.setCav6(Util.strHandle(pm.get("cav6")));
        cusEvent.setCav7(Util.strHandle(pm.get("cav7")));
        cusEvent.setCav8(Util.strHandle(pm.get("cav8")));
        cusEvent.setCav9(Util.strHandle(pm.get("cav9")));
        cusEvent.setCav10(Util.strHandle(pm.get("cav10")));
        cusEvent.setCav11(Util.strHandle(pm.get("cav11")));
        cusEvent.setCav12(Util.strHandle(pm.get("cav12")));
        cusEvent.setCav13(Util.strHandle(pm.get("cav13")));
        cusEvent.setCav14(Util.strHandle(pm.get("cav14")));
        cusEvent.setCav15(Util.strHandle(pm.get("cav15")));
        cusEvent.setCav16(Util.strHandle(pm.get("cav16")));
        cusEvent.setCav17(Util.strHandle(pm.get("cav17")));
        cusEvent.setCav18(Util.strHandle(pm.get("cav18")));
        cusEvent.setCav19(Util.strHandle(pm.get("cav19")));
        cusEvent.setCav20(Util.strHandle(pm.get("cav20")));

        return cusEvent;
    }

    @Override
    public String toString() {
        return
                pageView.toString() + '\t' +
                        caid + '\t' +
                        cal1 + '\t' +
                        cal2 + '\t' +
                        cal3 + '\t' +
                        cal4 + '\t' +
                        cal5 + '\t' +
                        cal6 + '\t' +
                        cal7 + '\t' +
                        cal8 + '\t' +
                        cal9 + '\t' +
                        cal10 + '\t' +
                        cal11 + '\t' +
                        cal12 + '\t' +
                        cal13 + '\t' +
                        cal14 + '\t' +
                        cal15 + '\t' +
                        cal16 + '\t' +
                        cal17 + '\t' +
                        cal18 + '\t' +
                        cal19 + '\t' +
                        cal20 + '\t' +
                        cav1 + '\t' +
                        cav2 + '\t' +
                        cav3 + '\t' +
                        cav4 + '\t' +
                        cav5 + '\t' +
                        cav6 + '\t' +
                        cav7 + '\t' +
                        cav8 + '\t' +
                        cav9 + '\t' +
                        cav10 + '\t' +
                        cav11 + '\t' +
                        cav12 + '\t' +
                        cav13 + '\t' +
                        cav14 + '\t' +
                        cav15 + '\t' +
                        cav16 + '\t' +
                        cav17 + '\t' +
                        cav18 + '\t' +
                        cav19 + '\t' +
                        cav20;
    }

    public String getCaid() {
        return caid;
    }

    public void setCaid(String caid) {
        this.caid = caid;
    }

    public String getCal1() {
        return cal1;
    }

    public void setCal1(String cal1) {
        this.cal1 = cal1;
    }

    public String getCal2() {
        return cal2;
    }

    public void setCal2(String cal2) {
        this.cal2 = cal2;
    }

    public String getCal3() {
        return cal3;
    }

    public void setCal3(String cal3) {
        this.cal3 = cal3;
    }

    public String getCal4() {
        return cal4;
    }

    public void setCal4(String cal4) {
        this.cal4 = cal4;
    }

    public String getCal5() {
        return cal5;
    }

    public void setCal5(String cal5) {
        this.cal5 = cal5;
    }

    public String getCal6() {
        return cal6;
    }

    public void setCal6(String cal6) {
        this.cal6 = cal6;
    }

    public String getCal7() {
        return cal7;
    }

    public void setCal7(String cal7) {
        this.cal7 = cal7;
    }

    public String getCal8() {
        return cal8;
    }

    public void setCal8(String cal8) {
        this.cal8 = cal8;
    }

    public String getCal9() {
        return cal9;
    }

    public void setCal9(String cal9) {
        this.cal9 = cal9;
    }

    public String getCal10() {
        return cal10;
    }

    public void setCal10(String cal10) {
        this.cal10 = cal10;
    }

    public String getCal11() {
        return cal11;
    }

    public void setCal11(String cal11) {
        this.cal11 = cal11;
    }

    public String getCal12() {
        return cal12;
    }

    public void setCal12(String cal12) {
        this.cal12 = cal12;
    }

    public String getCal13() {
        return cal13;
    }

    public void setCal13(String cal13) {
        this.cal13 = cal13;
    }

    public String getCal14() {
        return cal14;
    }

    public void setCal14(String cal14) {
        this.cal14 = cal14;
    }

    public String getCal15() {
        return cal15;
    }

    public void setCal15(String cal15) {
        this.cal15 = cal15;
    }

    public String getCal16() {
        return cal16;
    }

    public void setCal16(String cal16) {
        this.cal16 = cal16;
    }

    public String getCal17() {
        return cal17;
    }

    public void setCal17(String cal17) {
        this.cal17 = cal17;
    }

    public String getCal18() {
        return cal18;
    }

    public void setCal18(String cal18) {
        this.cal18 = cal18;
    }

    public String getCal19() {
        return cal19;
    }

    public void setCal19(String cal19) {
        this.cal19 = cal19;
    }

    public String getCal20() {
        return cal20;
    }

    public void setCal20(String cal20) {
        this.cal20 = cal20;
    }

    public String getCav1() {
        return cav1;
    }

    public void setCav1(String cav1) {
        this.cav1 = cav1;
    }

    public String getCav2() {
        return cav2;
    }

    public void setCav2(String cav2) {
        this.cav2 = cav2;
    }

    public String getCav3() {
        return cav3;
    }

    public void setCav3(String cav3) {
        this.cav3 = cav3;
    }

    public String getCav4() {
        return cav4;
    }

    public void setCav4(String cav4) {
        this.cav4 = cav4;
    }

    public String getCav5() {
        return cav5;
    }

    public void setCav5(String cav5) {
        this.cav5 = cav5;
    }

    public String getCav6() {
        return cav6;
    }

    public void setCav6(String cav6) {
        this.cav6 = cav6;
    }

    public String getCav7() {
        return cav7;
    }

    public void setCav7(String cav7) {
        this.cav7 = cav7;
    }

    public String getCav8() {
        return cav8;
    }

    public void setCav8(String cav8) {
        this.cav8 = cav8;
    }

    public String getCav9() {
        return cav9;
    }

    public void setCav9(String cav9) {
        this.cav9 = cav9;
    }

    public String getCav10() {
        return cav10;
    }

    public void setCav10(String cav10) {
        this.cav10 = cav10;
    }

    public String getCav11() {
        return cav11;
    }

    public void setCav11(String cav11) {
        this.cav11 = cav11;
    }

    public String getCav12() {
        return cav12;
    }

    public void setCav12(String cav12) {
        this.cav12 = cav12;
    }

    public String getCav13() {
        return cav13;
    }

    public void setCav13(String cav13) {
        this.cav13 = cav13;
    }

    public String getCav14() {
        return cav14;
    }

    public void setCav14(String cav14) {
        this.cav14 = cav14;
    }

    public String getCav15() {
        return cav15;
    }

    public void setCav15(String cav15) {
        this.cav15 = cav15;
    }

    public String getCav16() {
        return cav16;
    }

    public void setCav16(String cav16) {
        this.cav16 = cav16;
    }

    public String getCav17() {
        return cav17;
    }

    public void setCav17(String cav17) {
        this.cav17 = cav17;
    }

    public String getCav18() {
        return cav18;
    }

    public void setCav18(String cav18) {
        this.cav18 = cav18;
    }

    public String getCav19() {
        return cav19;
    }

    public void setCav19(String cav19) {
        this.cav19 = cav19;
    }

    public String getCav20() {
        return cav20;
    }

    public void setCav20(String cav20) {
        this.cav20 = cav20;
    }

    public PageView getPageView() {
        return pageView;
    }

    public void setPageView(PageView pageView) {
        this.pageView = pageView;
    }
}
