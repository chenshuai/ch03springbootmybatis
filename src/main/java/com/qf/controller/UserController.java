package com.qf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityManager securityManager;

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("deletePage")
    public String deletePage(){
        int i = 3 / 0;
        return "delete";
    }

    @RequestMapping("delete")
    public String delete(int uid){
        int i = userService.deleteUser(uid);
        if(i > 0){
            return "success";
        }
        return "delete";
    }

    @RequestMapping("index")
    public String index(@RequestParam(defaultValue = "1") int pageNum, Model model){
        //startPage使用当前线程或者重新开启线程，用于处理接下来查询的数据集合，根据设定的页码和每页显示的数量，直接将查询的集合设定为Page类型
        //集合中的数据也就仅剩下所设定的数量的数据。
        PageHelper.startPage(pageNum,5);
        List<User> userList = userService.getUserList();
        PageInfo<User> pageInfo = new PageInfo(userList);
        model.addAttribute("pageInfo",pageInfo);
        return "index";
    }

    @RequestMapping("loginPage")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("login")
    public String login(User user){
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUname(),user.getUpwd());
        try{
            subject.login(usernamePasswordToken);
            if(subject.isAuthenticated()){
                return "redirect:index";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:loginPage";

    }

    @ExceptionHandler(value = ArithmeticException.class)
    public String ArithHandler(){
        return "ari";
    }

    @RequestMapping("addUser")
    public String addUser(){
        return "add_user";
    }

    @RequestMapping("unauth")
    public String unauth(){
        return "unauth";
    }


}
