package com.Jancoyan.service;

import com.Jancoyan.dao.TypeMapper;
import com.Jancoyan.domain.Type;
import com.Jancoyan.domain.TypeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jancoyan
 */
@Service
public class TypeService {

    @Autowired
    TypeMapper typeMapper;

    /**
     *  获取所有的父类型和子类型
     *  封装在List中
     * @return
     */
    public List<Type> getAll(){
        TypeExample typeExample = new TypeExample();
        TypeExample.Criteria criteria = typeExample.createCriteria();
        criteria.andPreTypeIdIsNull();
        List<Type> typeList = typeMapper.selectByExample(typeExample);
        for (Type type : typeList){
            TypeExample typeExample1 = new TypeExample();
            TypeExample.Criteria criteria1 = typeExample1.createCriteria();
            criteria1.andPreTypeIdEqualTo(type.getTypeId());
            List<Type> subTypes = typeMapper.selectByExample(typeExample1);
            type.setSubTypes(subTypes);
        }
        return typeList;
    }

}
