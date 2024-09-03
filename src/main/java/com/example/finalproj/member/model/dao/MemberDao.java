package com.example.finalproj.member.model.dao;

import java.util.List;
import com.example.finalproj.member.model.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDao {
    public List<MemberDto> mtdMemberList();

    public int mtdMemberJoin(
            @Param("USER_EMAIL") String USER_EMAIL,
            @Param("USER_PW") String USER_PW,
            @Param("USER_NICK") String USER_NICK,
            @Param("USER_PHONE") String USER_PHONE
    );

    public int mtdMemberDelete(@Param("USER_CODE") int USER_CODE);

    // 새로 추가된 메서드
    // public MemberDto findMemberByEmail(@Param("USER_EMAIL") String USER_EMAIL);
}