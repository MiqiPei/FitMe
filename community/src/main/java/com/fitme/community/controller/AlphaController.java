package com.fitme.community.controller;

import com.fitme.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }


    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        //get request
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name  = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" +value);
        }
        System.out.println(request.getParameter("code"));

        //return response data
        response.setContentType("text/html;charset=utf-8");
        try{
            PrintWriter writer = response.getWriter();
            writer.write("<h1>Fit me</h1>");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //Get Request
    // /students?current = 1 and limit =20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current",required  = false, defaultValue = "1") int current,
            @RequestParam(name = "limit",required  = false, defaultValue = "1") int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    // Post request
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }
    // response HTML data
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "Maggie");
        mav.addObject("age", "30");
        mav.setViewName("/demo/view");
        return mav;
    }
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name","school");
        model.addAttribute("age","1900");
        return "/demo/view";
    }

    //response Jason data
    @RequestMapping(path="/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp(){
        Map<String, Object> emp= new HashMap<>();
        emp.put("name","Maggie");
        emp.put("age", "18");
        emp.put("salary",8000);
        return emp;
    }


}