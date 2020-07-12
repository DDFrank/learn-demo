package com.frank.service;

import com.frank.FeignApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 016039
 * @Package com.frank.service
 * @Description: ${todo}
 * @date 2018/10/31下午9:35
 */
@SpringBootTest(classes = FeignApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApiTest {
    @Autowired
    private ApiService apiService;

    @Test
    public void test() {
        try{
            System.out.println(apiService.index());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
