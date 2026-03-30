package com.example.demo.controller;

import java.util.Collections;
import java.util.Optional;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entry.Person;
import com.example.demo.repository.PersonRepository;

@Controller
public class HelloController {

    @Autowired
    private PersonRepository repository;

    /** 空文字を null に変換（mail 未入力で @Email エラーにならないようにする） */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, "mail", new StringTrimmerEditor(true));
        binder.registerCustomEditor(String.class, "memo", new StringTrimmerEditor(true));
    }

    // 一覧表示（＋追加フォーム表示）
    @GetMapping("/")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("index");
        mav.addObject("title", "Person Management System");
        mav.addObject("msg", "Please use it!");

        Iterable<Person> list = repository.findAll();
        mav.addObject("data", list != null ? list : Collections.emptyList());
        mav.addObject("formModel", new Person());

        return mav;
    }

    // 追加（Create）＋バリデーションエラー時は一覧画面に戻す
    // ★ POST "/" はこれ1本だけにする
    @PostMapping("/")
    @Transactional
    public ModelAndView createOrBackToIndex(
            @ModelAttribute("formModel") @Validated Person person,
            BindingResult result,
            ModelAndView mav) {

        if (!result.hasErrors()) {
            repository.saveAndFlush(person);
            return new ModelAndView("redirect:/");
        }

        // エラー時：一覧画面を再表示（入力値とエラーメッセージを保持）
        mav.setViewName("index");
        mav.addObject("title", "Person Management System");
        mav.addObject("msg", "入力に誤りがあります。下記を修正してください。");

        Iterable<Person> list = repository.findAll();
        mav.addObject("data", list != null ? list : Collections.emptyList());
        mav.addObject("formModel", person);

        return mav;
    }

    // 削除（Delete）: POST /delete?id=xx
    @PostMapping("/delete")
    @Transactional
    public ModelAndView delete(@RequestParam("id") Long id) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/");
    }

    // 初期データ投入（Dev用途）
    // ※ 毎回投入されるのが嫌なら「件数0の時だけ」にするのがおすすめ
    @PostConstruct
    public void init() {
        if (repository.count() > 0) return;

        Person p1 = new Person();
        p1.setName("taro");
        p1.setAge(39);
        p1.setMail("taro@yamada");
        repository.saveAndFlush(p1);

        Person p2 = new Person();
        p2.setName("hanako");
        p2.setAge(28);
        p2.setMail("hanako@flower");
        repository.saveAndFlush(p2);

        Person p3 = new Person();
        p3.setName("sachiko");
        p3.setAge(17);
        p3.setMail("sachico@happy");
        repository.saveAndFlush(p3);
    }

    // 編集画面（GET /edit/{id}）
    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable("id") Long id, ModelAndView mav) {
        mav.setViewName("edit");
        mav.addObject("title", "edit Person.");
        Optional<Person> data = repository.findById(id);
        mav.addObject("formModel", data.orElseThrow());
        return mav;
    }

    // 更新（Update）: POST /edit
    @PostMapping("/edit")
    @Transactional
    public ModelAndView update(@ModelAttribute("formModel") Person person) {
        repository.save(person);
        return new ModelAndView("redirect:/");
    }

    // 削除確認画面（GET /delete/{id}）
    @GetMapping("/delete/{id}")
    public ModelAndView deleteForm(@PathVariable("id") Long id, ModelAndView mav) {
        mav.setViewName("delete");
        mav.addObject("title", "Delete Person.");
        mav.addObject("msg", "Can I delete this record?");
        Optional<Person> data = repository.findById(id);
        mav.addObject("formModel", data.orElseThrow());
        return mav;
    }

    // 全件削除（Reset）
    @PostMapping("/reset")
    @Transactional
    public ModelAndView reset() {
        repository.truncateTable(); // 全件削除 + auto_increment初期化（ネイティブSQL想定）
        return new ModelAndView("redirect:/");
    }
}
