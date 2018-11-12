package seb.com.springboot404;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private  UserService userService;
    @RequestMapping(value ="/register",method = RequestMethod.GET)
    public String showRegisterationPage(Model model){
         model.addAttribute("user",new User());

         return "registration";
    }

    @RequestMapping(value="/register",method= RequestMethod.POST)
    public String showRegestrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        model.addAttribute("user",user);
        if (result.hasErrors())
        {
            return "regestration";
        }
        else
        {
            userService.saveUser(user);
            model.addAttribute("massage","USer Account Created");
        }
        return "index";
    }
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/secure")
    public String secure(Principal principal,Model model){
        User myuser = ((CustomUserDetails)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser();
        model.addAttribute("myuser",myuser);
        return "secure";
    }
}
