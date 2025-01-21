package com.hrmofturing.hrm.repository;

import com.hrmofturing.hrm.model.TrainingScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingScoreRepository extends JpaRepository<TrainingScore, Long> {
    List<TrainingScore> findByUserId(Long userId);
    List<TrainingScore> findByTrainingId(Long trainingId);
}