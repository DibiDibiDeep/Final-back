package com.example.finalproj.memo.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemoDao {
    // 날짜별로 그룹화된 메모 목록을 조회합니다.
    List<Map<String, Object>> mtdMemoListGroupByDate();

    // 새로운 메모를 작성합니다.
    int mtdMemoWrite(String content);

    // 기존 메모를 수정합니다.
    int mtdMemoUpdate(@Param("id") int id, @Param("content") String content);

    // 메모를 삭제합니다.
    int mtdMemoDelete(int id);

    // 특정 ID의 메모를 가져옵니다.
    Map<String, Object> mtdMemoGet(int id);

    // 검색어로 메모를 검색합니다. (새로 추가)
    List<Map<String, Object>> mtdMemoSearch(String searchTerm);
}