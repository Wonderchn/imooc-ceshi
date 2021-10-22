package com.imooc.service.impl;

import com.imooc.service.StuService;
import com.imooc.service.TestTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class TestTransServiceImpl implements TestTransService {

    @Autowired
    private StuService stuService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testPropagationTrans() {
       stuService.saveParent();

       try {
           //save point
           stuService.saveChildren();
       }catch(Exception e){
           e.printStackTrace();
       }
       //delete
        //update
        stuService.saveParent();



    }
}
