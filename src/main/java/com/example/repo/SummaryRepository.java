package com.example.repo;

import com.example.model.TrainerMonthlySummary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SummaryRepository extends MongoRepository<TrainerMonthlySummary, String> {
    public Optional<TrainerMonthlySummary> findByTrainerUsername(String username);
}
