package controllers;

import exceptions.CSVParsingException;
import model.Evaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.EvaluationService;

import javax.ws.rs.Produces;
import java.util.List;

@RestController
@Produces("application/json")
public class SpeechEvaluation {

    private final EvaluationService evaluationService;

    @Autowired
    public SpeechEvaluation(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }


    /**
     * Returns
     * 1. which politician gave the most peeches in the year 2013
     * 2. which politician gave the most speaches about security
     * 3. which politician spoke the least words
     *
     * @param url array of urls (e.g. url=url&url=url2) to csv files containing political speeches
     * @return json Object: Evaluation containing: mostSpeeches,mostSecurity,leastWordy
     */
    @GetMapping(value = "/evaluate")
    public ResponseEntity<Object> getEvaluation(@RequestParam List<String> url) {
        try {
            Evaluation evaluation = evaluationService.evaluate(url);
            return new ResponseEntity<>(evaluation, HttpStatus.OK);
        } catch (CSVParsingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
