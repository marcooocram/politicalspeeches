package services;

import com.google.common.collect.Iterables;
import model.Politician;
import model.Speech;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SpeechServiceImplTest {

    private static final SpeechService SPEECH_SERVICE = new SpeechServiceImpl();
    private static final int WORD_COUNT_1 = 500;
    private static final int WORD_COUNT_2 = 900;
    private static final String POLITICIAN_1 = "politician1";
    private static final String POLITICIAN_2 = "politician2";
    private static final LocalDate DATE_IN_2013 = LocalDate.of(2013, 5, 12);
    private static final LocalDate DATE_NOT_IN_2013 = LocalDate.of(2012, 5, 12);


    @Test
    public void sortSpeechesHappyPath() {
        //given
        List<Speech> speeches = Arrays.asList(
                new Speech(POLITICIAN_1, SpeechService.SECURITY, DATE_IN_2013, WORD_COUNT_1),
                new Speech(POLITICIAN_2, SpeechService.SECURITY, DATE_NOT_IN_2013, WORD_COUNT_2),
                new Speech(POLITICIAN_1, SpeechService.SECURITY, DATE_IN_2013, WORD_COUNT_1),
                new Speech(POLITICIAN_2, "other topic", DATE_NOT_IN_2013, WORD_COUNT_2)
        );

        //when
        Collection<Politician> result = SPEECH_SERVICE.sortSpeeches(speeches);

        //then
        assertThat(result.size(), is(2));
        assertThat(Iterables.get(result, 0).getName(), is(POLITICIAN_1));
        assertThat(Iterables.get(result, 0).getTotalWords(), is(2 * WORD_COUNT_1));
        assertThat(Iterables.get(result, 0).getSpeechesIn2013(), is(2));
        assertThat(Iterables.get(result, 0).getSecuritySpeeches(), is(2));
        assertThat(Iterables.get(result, 1).getName(), is(POLITICIAN_2));
        assertThat(Iterables.get(result, 1).getTotalWords(), is(2 * WORD_COUNT_2));
        assertThat(Iterables.get(result, 1).getSpeechesIn2013(), is(0));
        assertThat(Iterables.get(result, 1).getSecuritySpeeches(), is(1));
    }
}