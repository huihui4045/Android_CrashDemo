package com.huihui.crash;

/**
 * Created by gavin
 * Time 2017/7/19  15:57
 * Email:molu_clown@163.com
 */

public class CrashMessageBean {

    private String version;

    private String versionCode;

    private String androidVersion;

    private String vendor;

    private String model;

    private String cupAbi;

    private String errorMes;

    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CrashMessageBean() {
    }

    public CrashMessageBean(String version, String versionCode, String androidVersion, String vendor,
                            String model, String cupAbi, String errorMes) {
        this.version = version;
        this.versionCode = versionCode;
        this.androidVersion = androidVersion;
        this.vendor = vendor;
        this.model = model;
        this.cupAbi = cupAbi;
        this.errorMes = errorMes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCupAbi() {
        return cupAbi;
    }

    public void setCupAbi(String cupAbi) {
        this.cupAbi = cupAbi;
    }

    public String getErrorMes() {
        return errorMes;
    }

    public void setErrorMes(String errorMes) {
        this.errorMes = errorMes;
    }
}
