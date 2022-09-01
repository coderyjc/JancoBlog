package com.Jancoyan.test;

import com.Jancoyan.dao.ArticleMapper;
import com.Jancoyan.dao.TypeMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Jancoyan
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    TypeMapper typeMapper;

    @Autowired
    ArticleMapper articleMapper;


    /**
     * 获取所有完整的类型
     * 2021.4.10
     */
//    @Test
//    public void getAllType(){
//        TypeExample typeExample = new TypeExample();
//        TypeExample.Criteria criteria = typeExample.createCriteria();
//        criteria.andPreTypeIdIsNull();
//        List<Type> typeList = typeMapper.selectByExample(typeExample);
//        for (Type type : typeList){
//            System.out.println(type);
//            TypeExample typeExample1 = new TypeExample();
//            TypeExample.Criteria criteria1 = typeExample1.createCriteria();
//            criteria1.andPreTypeIdEqualTo(type.getTypeId());
//            List<Type> subTypes = typeMapper.selectByExample(typeExample1);
//            type.setSubTypes(subTypes);
//        }
//    }

}
