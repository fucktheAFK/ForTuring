package com.hrmofturing.hrm.service;

import com.hrmofturing.hrm.model.Training;
import com.hrmofturing.hrm.repository.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }

    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }
}
