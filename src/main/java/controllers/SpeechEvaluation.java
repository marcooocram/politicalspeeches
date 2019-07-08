package controllers;

import exceptions.CSVParsingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @GetMapping(value = "/evaluate")
    @ApiOperation(value = "evaluates political speeches ",
            notes = "Returns<br/>" +
                    "1. which politician gave the most speeches in the year 2013 <br/>" +
                    "2. which politician gave the most speeches about security <br/>" +
                    "3. which politician spoke the least words",
            response = Evaluation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request - Problem with retrieving or parsing of csv files"),
            @ApiResponse(code = 500, message = "Internal Server error") })
    public ResponseEntity<Object> getEvaluation(
            @ApiParam(value = "array of urls (e.g. url=url&url=url2) to csv files containing political speeches", required = true)
            @RequestParam List<String> url) {
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
