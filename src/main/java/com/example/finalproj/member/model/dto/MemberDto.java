package com.example.finalproj.member.model.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class MemberDto {

    private int USER_CODE;             // 회원코드
    private String USER_PLATFORM;      // 플랫폼유형
    private String USER_EMAIL;         // 이메일
    private String USER_PW;            // 비밀번호
    private String USER_NICK;          // 닉네임
    private String USER_PHONE;         // 연락처
    private Timestamp USER_SIGNUP;     // 가입일
    private Timestamp USER_LATEST;     // 최근접속일
    private String USER_AUTH;          // 권한
    private Date USER_LEAVE;           // 탈퇴일자
    private String USER_TOKEN;         // 리프레시토큰

}
