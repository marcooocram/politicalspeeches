package services;

import model.Evaluation;

import java.util.List;

public interface EvaluationService {
    Evaluation evaluate(List<String> urls);
}
