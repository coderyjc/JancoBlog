package com.Jancoyan.Controller;


import com.Jancoyan.Domain.User;
import com.Jancoyan.Service.IUserService;
import com.Jancoyan.Service.Impl.UserServiceImpl;
import com.Jancoyan.Utils.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Jancoyan
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        String uName = request.getParameter("uname");
        String uPwd = request.getParameter("upwd");
        User user = new User();
        user.setUserName(uName);
        user.setUserPwd(uPwd);
        IUserService userService = (IUserService) ServiceFactory.getService(new UserServiceImpl());
        boolean rst = userService.login(user);
        if(rst) {
             modelAndView.addObject("rst", 1);
        } else{
             modelAndView.addObject("rst", 0);
        }
        modelAndView.setViewName("index");
        return modelAndView;
    }
}