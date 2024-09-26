package com.example.finalproj.AlimInf.repository;

import com.example.finalproj.AlimInf.entity.AlimInf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlimInfRepository extends JpaRepository<AlimInf, Integer> {

    //정해진 기간 내 AlimInf 조회
    @Query("SELECT a FROM AlimInf a WHERE a.date BETWEEN :startDate AND :endDate")
    List<AlimInf> findByDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
  
    Optional<AlimInf> findByAlimId(Integer alimId);

    // alimId의 최대 값을 가져오는 쿼리
    @Query("SELECT COALESCE(MAX(a.alimId), 0) FROM AlimInf a")
    Integer findMaxAlimId();

}