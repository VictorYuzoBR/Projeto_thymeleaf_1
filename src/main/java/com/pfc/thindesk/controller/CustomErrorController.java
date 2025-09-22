package com.pfc.thindesk.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        if (statusCode != null) {
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404"; // retorna a view error/404.html (Thymeleaf)
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "401";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            return "403";
            }

        }
        return "error"; // página de erro genérica
    }

    @GetMapping("/400")
    public String erro400(@RequestParam(value = "msg", required = false) String msg, Model model) {
        if (msg == null || msg.isEmpty()) {
            msg = "Bad Request";
        }
        model.addAttribute("errorMessage", msg);
        return "400"; // sua view 400.html
    }
}
