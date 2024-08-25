package com.deep.backend.controller;

import com.deep.backend.model.dto.TestDTO;
import com.deep.backend.model.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/hello")
    public TestDTO sayHello(){
        return testService.sayhello();
    }

}
