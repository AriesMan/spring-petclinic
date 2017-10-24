package org.springframework.samples.petclinic.system;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class LoginController {

    @RequestMapping("/login")
    public String welcome() {
        return "login";
    }
}
