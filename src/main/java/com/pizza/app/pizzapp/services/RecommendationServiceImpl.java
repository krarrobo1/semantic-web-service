package com.pizza.app.pizzapp.services;

import java.io.IOException;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationServiceImpl implements RecommendationService{

    @Autowired
    JenaService jenaService;

    @Override
    public String getRecommendation(String userPreferences) {
        String result = "";
        try {
            result = jenaService.getJSONStringResult(userPreferences);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResultSet execQuery(String query) throws IOException {
       return jenaService.execSparQl(query);
    }
    
}
