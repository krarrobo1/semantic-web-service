package com.pizza.app.pizzapp.services;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class JenaService {
    @Autowired
    private  ResourceLoader resourceLoader;
    private OntModel pizzaOntology;
    private boolean loaded = false;
   

    public JenaService(){
    }

    /**
     * Loads OntModel
     * @return
     * @throws IOException
     */
    OntModel OpenConnectOWL() throws IOException {
        if(!loaded){
            Resource resource = resourceLoader.getResource("classpath:pizzaontology.owl");
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
            InputStream input = resource.getInputStream();
            this.pizzaOntology = (OntModel) model.read(input, "");
            this.loaded = true;
        }
        return this.pizzaOntology;
    }

    /**
     * Exec SPARQL Query
     * @param raw
     * @return
     * @throws IOException
     */
    ResultSet execSparQl(String raw) throws IOException {
        Query query = QueryFactory.create(raw);
        QueryExecution queryExec = QueryExecutionFactory.create(query, OpenConnectOWL());
        ResultSet result = queryExec.execSelect();
        return result;
    }

    /**
     * Exec SPARQL Query and parses to JSON String
     * @param raw
     * @return
     * @throws IOException
     */
    String getJSONStringResult(String raw) throws IOException {
        String str = "";
        try {
            Query query = QueryFactory.create(raw);
            QueryExecution queryExec = QueryExecutionFactory.create(query, OpenConnectOWL());
            ResultSet rs = queryExec.execSelect();
            
            if (rs.hasNext()) {
                ByteArrayOutputStream go = new ByteArrayOutputStream();
                ResultSetFormatter.outputAsJSON((OutputStream) go, rs);
                str = new String(go.toByteArray(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    
}
