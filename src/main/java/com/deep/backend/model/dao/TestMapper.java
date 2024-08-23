package com.deep.backend.model.dao;

import com.deep.backend.model.dto.TestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    void incrementCount();

    TestDTO getCurrentData();
}
