// DTO 클래스
package com.example.finalproj.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class MemoDto {
    private int id;
    private String content;
    private Timestamp created_at;
}
