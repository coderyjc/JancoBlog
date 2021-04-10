package com.Jancoyan.domain;

public class Type {
    private Integer typeId;

    private String typeName;

    private Integer preTypeId;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getPreTypeId() {
        return preTypeId;
    }

    public void setPreTypeId(Integer preTypeId) {
        this.preTypeId = preTypeId;
    }
}