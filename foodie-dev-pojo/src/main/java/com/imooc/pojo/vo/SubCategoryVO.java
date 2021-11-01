package com.imooc.pojo.vo;

public class SubCategoryVO {
    private Integer sunId;
    private String subName;
    private String subType;
    private Integer subFatherId;

    public Integer getSubId() {
        return sunId;
    }

    public void setSubId(Integer sunId) {
        this.sunId = sunId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Integer getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(Integer subFatherId) {
        this.subFatherId = subFatherId;
    }
}
