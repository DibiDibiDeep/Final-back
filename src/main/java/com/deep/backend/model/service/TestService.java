package com.deep.backend.model.service;

import com.deep.backend.model.dao.TestMapper;
import com.deep.backend.model.dto.TestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private static TestMapper testMapper;

    public TestService(TestMapper testMapper){ this.testMapper = testMapper;}

    public static TestDTO sayhello() {
        testMapper.incrementCount();
        return testMapper.getCurrentData();
    }

}
