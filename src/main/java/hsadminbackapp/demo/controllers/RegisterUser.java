package hsadminbackapp.demo.controllers;

import hsadminbackapp.demo.models.Code;
import hsadminbackapp.demo.services.CodeService;
import hsadminbackapp.demo.models.User;
import hsadminbackapp.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/register")
public class RegisterUser {


    private CodeService codeService;
    private UserService userService;

    @Autowired
    public RegisterUser(CodeService codeService, UserService userService) {
        this.codeService = codeService;
        this.userService = userService;
    }

    @GetMapping
    public String getRegister(Model model) {
        model.addAttribute("newUser", new User());
        return "register";
    }

    @GetMapping("/checkcode")
    public String checkCodeView(Model model) {
        model.addAttribute("givenCode", new Code());
        return "checkcode";
    }

    @PostMapping("/checkcode")
    public String checkCodeGiven(@ModelAttribute Code code, Model model) {
        if (codeService.checkCode(code.getCode())) {
            userService.addNewUser(codeService.getTempUser());
            return "redirect:/register";
        }
        System.out.println(codeService.getCode());
        return "redirect:/register/checkcode";
    }

    @PostMapping
    public String addNewUser(@ModelAttribute User user) {
        codeService.setTempUser(user);
        return "redirect:/register/checkcode";
    }

}
