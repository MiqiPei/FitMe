package com.fitme.community;


import com.fitme.community.dao.UserMapper;
import com.fitme.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class )
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("MoMo");
        user.setPassword("000000");
        user.setSalt("sfhjksh");
        user.setEmail("abc@gmail.com");
        user.setHeaderUrl("http://www.fitme.com/101.png");
        user.setCreatetime(new Date());

        int rows= userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());

    }

    @Test
    public void testSelectUser(){
        User user = userMapper.selectByName("jason");
        System.out.println(user);
        user = userMapper.selectByEmail("jasonexample@google.ca");
        System.out.println(user);
        user = userMapper.selectByID(101);
        System.out.println(user);

    }

    @Test
    public void updateUser(){
        int rows= userMapper.updateStatus(150,1);
        System.out.println(rows);
        rows= userMapper.updateHeader(150, "http:www.fitme.com/101.png" );
        System.out.println(rows);
        rows= userMapper.updatePassword(150,"5656");
        System.out.println(rows);
    }
}
