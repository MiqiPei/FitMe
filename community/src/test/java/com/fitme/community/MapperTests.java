package com.fitme.community;


import com.fitme.community.dao.DiscussPostMapper;
import com.fitme.community.dao.LoginTicketMapper;
import com.fitme.community.dao.MessageMapper;
import com.fitme.community.dao.UserMapper;
import com.fitme.community.entity.DiscussPost;
import com.fitme.community.entity.LoginTicket;
import com.fitme.community.entity.Message;
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

    @Autowired
    private MessageMapper messageMapper;

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

    @Test
    public void testSelectLetters() {
        List<Message> list = messageMapper.selectConversations(111, 0, 20);
        for (Message message : list) {
            System.out.println(message);
        }

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        list = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message : list) {
            System.out.println(message);
        }

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        count = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count);

    }
}
