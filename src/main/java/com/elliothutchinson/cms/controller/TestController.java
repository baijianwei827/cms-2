package com.elliothutchinson.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elliothutchinson.cms.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {

    private TestService testService;

    public TestController() {
    }

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping
    public String initializeData(Model model) {
        testService.initializeData();
        model.addAttribute("name", "initilize data success!");
        return "test";
    }

    @RequestMapping("/delete")
    public String deleteData(Model model) {
        testService.deleteSampleData();
        model.addAttribute("name", "delete success!");
        return "test";
    }

    @RequestMapping("/create")
    public String createData(Model model) {
        testService.createSampleData();
        model.addAttribute("name", "create success!");
        return "test";
    }

    @RequestMapping("/tags")
    public String addTags(Model model) {
        testService.addTags();
        model.addAttribute("name", "add tags success!");
        return "test";
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
            Model model) {
        model.addAttribute("name", "hello " + name);
        return "test";
    }
}
