package services;

import model.Evaluation;
import model.Politician;
import model.Speech;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class EvaluationServiceImplTest {
    private static final String POLITICIAN_1 = "politician1";
    private static final String POLITICIAN_2 = "politician2";
    private static final String POLITICIAN_3 = "politician3";
    private static final int HIGHEST_NUMBER = 100;
    private static final int MIDDLE_NUMBER = 50;
    private static final int LOWEST_NUMBER = 10;


    private static final CSVService CSV_SERVICE_MOCK = mock(CSVService.class);
    private static final SpeechService SPEECH_SERVICE_MOCK = mock(SpeechService.class);

    private static final EvaluationService evaluationService = new EvaluationServiceImpl(CSV_SERVICE_MOCK, SPEECH_SERVICE_MOCK);

    @Test
    public void evaluateForClearWinners() {
        //given
        List<String> urlList = Collections.emptyList();
        List<Speech> speechList = Collections.emptyList();
        Collection<Politician> politicians = Arrays.asList(
                new Politician(POLITICIAN_1, HIGHEST_NUMBER, MIDDLE_NUMBER, MIDDLE_NUMBER),
                new Politician(POLITICIAN_2, MIDDLE_NUMBER, HIGHEST_NUMBER, LOWEST_NUMBER),
                new Politician(POLITICIAN_3, LOWEST_NUMBER, LOWEST_NUMBER, HIGHEST_NUMBER)
        );
        when(CSV_SERVICE_MOCK.getSpeechesFromUrls(urlList)).thenReturn(speechList);
        when(SPEECH_SERVICE_MOCK.sortSpeeches(speechList)).thenReturn(politicians);

        //when
        Evaluation result = evaluationService.evaluate(urlList);

        //then
        assertThat(result.getLeastWordy(), is(POLITICIAN_3));
        assertThat(result.getMostSecurity(), is(POLITICIAN_2));
        assertThat(result.getMostSpeeches(), is(POLITICIAN_3));
    }

    @Test
    public void evaluateForTies() {
        //given
        List<String> urlList = new ArrayList<>();
        List<Speech> speechList = new ArrayList<>();
        Collection<Politician> politicians = Arrays.asList(
                new Politician(POLITICIAN_1, LOWEST_NUMBER, HIGHEST_NUMBER, HIGHEST_NUMBER),
                new Politician(POLITICIAN_2, LOWEST_NUMBER, HIGHEST_NUMBER, HIGHEST_NUMBER),
                new Politician(POLITICIAN_3, LOWEST_NUMBER, HIGHEST_NUMBER, HIGHEST_NUMBER)
        );
        when(CSV_SERVICE_MOCK.getSpeechesFromUrls(urlList)).thenReturn(speechList);
        when(SPEECH_SERVICE_MOCK.sortSpeeches(speechList)).thenReturn(politicians);

        //when
        Evaluation result = evaluationService.evaluate(urlList);

        //then
        assertNull(result.getLeastWordy());
        assertNull(result.getMostSecurity());
        assertNull(result.getMostSpeeches());
    }

}