package com.test;

import com.imooc.Application;
import com.imooc.service.StuService;
import com.imooc.service.TestTransService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransTest {
    @Autowired
    private StuService stuService;

    public static void main(String[] args) {
        ArrayList<Object> objects = new ArrayList<>();

    }

    @Autowired
    private TestTransService testTransService;

    @Test
    public void myTest() {
        testTransService.testPropagationTrans();
    }
}
