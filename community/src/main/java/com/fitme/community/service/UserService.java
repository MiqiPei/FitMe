package com.fitme.community.service;

import com.fitme.community.dao.LoginTicketMapper;
import com.fitme.community.dao.UserMapper;
import com.fitme.community.entity.LoginTicket;
import com.fitme.community.entity.User;
import com.fitme.community.util.CommunityConstant;
import com.fitme.community.util.CommunityUtil;
import com.fitme.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public User findUserById(int id){
        return userMapper.selectByID(id);
    }

    public Map<String,Object> register(User user){
        Map<String, Object> map= new HashMap<>();
        //empty
        if(user == null){
            throw new IllegalArgumentException("invalid Argument");
        }
        if(StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "Account cannot be blank!");
            return map;
        }
        if(StringUtils.isBlank(user.getUsername())) {
            map.put("passwordMsg", "Password cannot be blank!");
            return map;
        }
        if(StringUtils.isBlank(user.getUsername())) {
            map.put("emailMsg", "Email cannot be blank!");
            return map;
        }

        //verify account
        User u = userMapper.selectByName(user.getUsername());
        if(u != null){
            map.put("usernameMsg", "Account exists");
            return map;
        }

        //verify email
        u = userMapper.selectByEmail(user.getEmail());
        if(u != null){
            map.put("emailMsg", "Email has been registered");
            return map;
        }

        //new account register
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl("https://ibb.co/Nj7Mp74");
        user.setCreatetime(new Date());
        userMapper.insertUser(user);

        //email activation
        org.thymeleaf.context.Context context = new Context();
        context.setVariable("email", user.getEmail());
        String url = domain + contextPath + "/activation/" + user.getId() +"/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "Activate your account", content);
        return map;

    }

    public int activation(int userID, String code){
        User user = userMapper.selectByID(userID);
        if(user.getStatus() == 1){
            return ACTIVATION_REPEAT;
        }else if (user.getActivationCode().equals(code)){
            userMapper.updateStatus(userID,1);
            return ACTIVATION_SUCCESS;
        }else{
            return ACTIVATION_FAILURE;
        }

    }

    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        // empty
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "Account cannot be empty!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "Password cannot be empty");
            return map;
        }

        // verify account
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "Account does not exist!");
            return map;
        }

        // verify status
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "Account has not been activated!");
            return map;
        }

        // verify password
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "Password incorrect!");
            return map;
        }

        // Generate LoginTicket
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);

        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    public void logout(String ticket) {
        loginTicketMapper.updateStatus(ticket, 1);
    }

    public LoginTicket findLoginTicket(String ticket) {
        return loginTicketMapper.selectByTicket(ticket);
    }

    public int updateHeader(int userId, String headerUrl) {
        return userMapper.updateHeader(userId, headerUrl);
    }
    public User findUserByName(String username) {
        return userMapper.selectByName(username);
    }


}


