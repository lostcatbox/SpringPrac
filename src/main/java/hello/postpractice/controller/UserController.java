package hello.postpractice.controller;

import hello.postpractice.domain.UserDto;
import hello.postpractice.domain.UserSessionDto;
import hello.postpractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    private final HttpSession session;

    @GetMapping("/")
    public String servetologin() {
        return "redirect:/login";
    }

    @GetMapping("/join")
    public String join() {
        return "/user/signup";
    }

    @PostMapping("/joinProc")
    public String join(UserDto userDto) { // 회원 추가
        userService.join(userDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String mainPage(Model model){
        UserSessionDto user = (UserSessionDto) session.getAttribute("user");
        model.addAttribute("user", user);
        return "/user/main";
    }
    @GetMapping("/admin")
    public String adminPage(){
        return "/user/admin";
    }
}