package com.jh.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * interurban_coach_od
 * @author 
 */
public class InterurbanCoachOd implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 出发地
     */
    private String oCity;

    /**
     * 目的地
     */
    private String dCity;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 发班日期
     */
    private Integer dateTime;

    /**
     * 出发地经度
     */
    private BigDecimal oCityLon;

    /**
     * 出发地纬度
     */
    private BigDecimal oCityLat;

    /**
     * 目的地经度
     */
    private BigDecimal dCityLon;

    /**
     * 目的地纬度
     */
    private BigDecimal dCityLat;

    /**
     * 时间
     */
    private Date synchronizeTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getoCity() {
        return oCity;
    }

    public void setoCity(String oCity) {
        this.oCity = oCity;
    }

    public String getdCity() {
        return dCity;
    }

    public void setdCity(String dCity) {
        this.dCity = dCity;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getDateTime() {
        return dateTime;
    }

    public void setDateTime(Integer dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getoCityLon() {
        return oCityLon;
    }

    public void setoCityLon(BigDecimal oCityLon) {
        this.oCityLon = oCityLon;
    }

    public BigDecimal getoCityLat() {
        return oCityLat;
    }

    public void setoCityLat(BigDecimal oCityLat) {
        this.oCityLat = oCityLat;
    }

    public BigDecimal getdCityLon() {
        return dCityLon;
    }

    public void setdCityLon(BigDecimal dCityLon) {
        this.dCityLon = dCityLon;
    }

    public BigDecimal getdCityLat() {
        return dCityLat;
    }

    public void setdCityLat(BigDecimal dCityLat) {
        this.dCityLat = dCityLat;
    }

    public Date getSynchronizeTime() {
        return synchronizeTime;
    }

    public void setSynchronizeTime(Date synchronizeTime) {
        this.synchronizeTime = synchronizeTime;
    }
}