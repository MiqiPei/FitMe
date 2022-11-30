package com.fitme.community;


import com.fitme.community.dao.DiscussPostMapper;
import com.fitme.community.dao.LoginTicketMapper;
import com.fitme.community.dao.UserMapper;
import com.fitme.community.entity.DiscussPost;
import com.fitme.community.entity.LoginTicket;
import com.fitme.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class )
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

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

    @Test
    public void testSelectPosts(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0,0,10);
        for(DiscussPost post : list){
            System.out.println(post);
        }

        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }

    @Test
    public void testInsertLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(105);
        loginTicket.setTicket("sss");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));

        loginTicketMapper.insertLoginTicket(loginTicket);
    }
    @Test
    public void testSelectLoginTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("sss");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("sss", 1);
        loginTicket = loginTicketMapper.selectByTicket("sss");
        System.out.println(loginTicket);
    }
}
