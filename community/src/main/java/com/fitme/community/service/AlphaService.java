package com.fitme.community.service;

import com.fitme.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    public AlphaService(){
        System.out.println("Instantiate AlphaService");
    }

    @PostConstruct
    public void init(){
        System.out.println("Initialize AlphaService");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Destroy AlphaService");
    }
    public String find(){
        return alphaDao.select();
    }
}
