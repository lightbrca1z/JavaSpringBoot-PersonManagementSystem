package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        // 初期化
        if (session.getAttribute("total") == null) {
            session.setAttribute("total", 0);
        }

        model.addAttribute("msg", "合計は " + session.getAttribute("total") + " です");
        return "index";
    }

    @GetMapping("/result")
    public String result(@RequestParam("number") int number, Model model, HttpSession session) {
        // セッションから現在の合計を取得
        Integer total = (Integer) session.getAttribute("total");
        if (total == null) {
            total = 0;
        }

        // 合計に入力値を加算
        total += number;

        // セッションに保存
        session.setAttribute("total", total);

        model.addAttribute("msg", "合計は " + total + " です");
        return "index";
    }

    @GetMapping("/reset")
    public String reset(HttpSession session) {
        session.setAttribute("total", 0);
        return "redirect:/";
    }
}
