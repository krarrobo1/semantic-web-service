package com.pizza.app.pizzapp.controllers;


import com.pizza.app.pizzapp.services.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/recommendation")
public class RecommendationController {
    @Autowired
    RecommendationService recommendationService;
    /**
     * Returns all customer orders. 
     * @return application/json
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRecommendation(){
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
        "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
        "PREFIX pizza: <http://www.semanticweb.org/pizzatutorial/ontologies/2020/PizzaTutorial#>"+
        "SELECT * WHERE { ?customer pizza:purchasedPizza ?pizza }";

        return recommendationService.getRecommendation(query);
    }
}
