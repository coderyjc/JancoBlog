package com.Jancoyan.domain;

import java.util.List;

/**
 * @author Jancoyan
 */
public class Type {
    private Integer typeId;

    private String typeName;

    private Integer preTypeId;

    private List<Type> subTypes;

    @Override
    public String toString() {
        return "Type{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", preTypeId=" + preTypeId +
                ", subTypes=" + subTypes +
                '}';
    }

    public List<Type> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<Type> subTypes) {
        this.subTypes = subTypes;
    }

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