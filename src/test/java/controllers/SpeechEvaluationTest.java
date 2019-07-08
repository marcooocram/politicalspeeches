package controllers;

import exceptions.CSVParsingException;
import model.Evaluation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import services.EvaluationService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

public class SpeechEvaluationTest {

    private EvaluationService evaluationServiceMock;
    private SpeechEvaluation speechEvaluation;
    private static final String MOST_SPEECHES = "mostSpeeches";
    private static final String MOST_SECURITY = "mostSecurity";
    private static final String LEAST_WORDY = "leastWordy";

    @Before
    public void setup() {
        evaluationServiceMock = mock(EvaluationService.class);
        speechEvaluation = new SpeechEvaluation(evaluationServiceMock);
    }

    @Test
    public void getEvaluationHappyPath() {
        //given
        List<String> urls = Collections.emptyList();
        Evaluation evaluation = new Evaluation(MOST_SPEECHES, MOST_SECURITY, LEAST_WORDY);
        when(evaluationServiceMock.evaluate(urls)).thenReturn(evaluation);

        //when
        ResponseEntity<Object> response = speechEvaluation.getEvaluation(urls);

        //then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(((Evaluation) response.getBody()).getMostSpeeches(), is(MOST_SPEECHES));
        assertThat(((Evaluation) response.getBody()).getMostSecurity(), is(MOST_SECURITY));
        assertThat(((Evaluation) response.getBody()).getLeastWordy(), is(LEAST_WORDY));
    }

    @Test
    public void getEvaluationShouldReturnBadRequest() {
        //given
        List<String> urls = Collections.emptyList();
        when(evaluationServiceMock.evaluate(urls)).thenThrow(CSVParsingException.class);

        //when
        ResponseEntity<Object> response = speechEvaluation.getEvaluation(urls);

        //then
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }


    @Test
    public void getEvaluationShouldReturnInternalServerError() {
        //given
        List<String> urls = Collections.emptyList();
        when(evaluationServiceMock.evaluate(urls)).thenThrow(RuntimeException.class);

        //when
        ResponseEntity<Object> response = speechEvaluation.getEvaluation(urls);

        //then
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}