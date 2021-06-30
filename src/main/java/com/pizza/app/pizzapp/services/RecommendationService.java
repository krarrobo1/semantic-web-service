package com.pizza.app.pizzapp.services;
import java.io.IOException;

import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Service;

@Service
public interface RecommendationService {
 public String getRecommendation(String userPreferences);
 public ResultSet execQuery(String query) throws IOException;
}
