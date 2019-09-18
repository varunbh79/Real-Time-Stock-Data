package com.boot.project.stocks.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.math.BigInteger;


@JsonIgnoreProperties(ignoreUnknown = true)
public class StockDetails {

    private String companyName;
    private Double open;
    private Double close;
    private Double latestPrice;
    private BigInteger latestVolume;
    private Integer extendedPrice;
    private BigDecimal extendedChange;
    private BigDecimal previousClose;
    private BigDecimal week52High;
    private BigDecimal week52Low;
    private BigDecimal iexBidPrice;
    private BigDecimal iexAskPrice;

    public String getCompanyName () {
        return companyName;
    }

    public void setCompanyName (String companyName) {
        this.companyName = companyName;
    }

    public Double getOpen () {
        return open;
    }

    public void setOpen (Double open) {
        this.open = open;
    }

    public Double getClose () {
        return close;
    }

    public void setClose (Double close) {
        this.close = close;
    }

    public Double getLatestPrice () {
        return latestPrice;
    }

    public void setLatestPrice (Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public BigInteger getLatestVolume () {
        return latestVolume;
    }

    public void setLatestVolume (BigInteger latestVolume) {
        this.latestVolume = latestVolume;
    }

    public Integer getExtendedPrice () {
        return extendedPrice;
    }

    public void setExtendedPrice (Integer extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public BigDecimal getExtendedChange () {
        return extendedChange;
    }

    public void setExtendedChange (BigDecimal extendedChange) {
        this.extendedChange = extendedChange;
    }

    public BigDecimal getPreviousClose () {
        return previousClose;
    }

    public void setPreviousClose (BigDecimal previousClose) {
        this.previousClose = previousClose;
    }

    public BigDecimal getWeek52High () {
        return week52High;
    }

    public void setWeek52High (BigDecimal week52High) {
        this.week52High = week52High;
    }

    public BigDecimal getWeek52Low () {
        return week52Low;
    }

    public void setWeek52Low (BigDecimal week52Low) {
        this.week52Low = week52Low;
    }

    public BigDecimal getIexBidPrice () {
        return iexBidPrice;
    }

    public void setIexBidPrice (BigDecimal iexBidPrice) {
        this.iexBidPrice = iexBidPrice;
    }

    public BigDecimal getIexAskPrice () {
        return iexAskPrice;
    }

    public void setIexAskPrice (BigDecimal iexAskPrice) {
        this.iexAskPrice = iexAskPrice;
    }

    @Override
    public String toString () {
        return "StockDetails{" +
                "companyName='" + companyName + '\'' +
                ", open=" + open +
                ", close=" + close +
                ", latestPrice=" + latestPrice +
                ", latestVolume=" + latestVolume +
                ", extendedPrice=" + extendedPrice +
                ", extendedChange=" + extendedChange +
                ", previousClose=" + previousClose +
                ", week52High=" + week52High +
                ", week52Low=" + week52Low +
                ", iexBidPrice=" + iexBidPrice +
                ", iexAskPrice=" + iexAskPrice +
                '}';
    }
}

