package services;

import exceptions.CSVParsingException;
import model.Speech;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class CSVServiceImplTest {

    private static final CSVService CSVSERVICE = new CSVServiceImpl("yyyy-MM-dd", "Redner", "Thema", "Datum", "Wörter");

    @Test
    public void getSpeechesFromUrlsHappyPath() {
        //given
        List<String> urls = Arrays.asList(
                getResourceUrl("politics.csv"),
                getResourceUrl("additionalpolitics.csv")
        );

        //when
        List<Speech> result = CSVSERVICE.getSpeechesFromUrls(urls);

        //then
        assertThat(result.size(), is(5));
        assertThat(result.get(0).getSpeaker(), is("Alexander Abel"));
        assertThat(result.get(1).getDate().toString(), is("2012-11-05"));
        assertThat(result.get(2).getTopic(), is("Kohlesubventionen"));
        assertThat(result.get(3).getWords(), is(911));
        assertThat(result.get(4).getTopic(), is("Java"));
    }

    @Test(expected = CSVParsingException.class)
    public void getSpeechesFromUrlsWithMalformedUrl() {
        //given
        List<String> urls = Collections.singletonList("");

        //when
        CSVSERVICE.getSpeechesFromUrls(urls);
    }


    @Test(expected = CSVParsingException.class)
    public void getSpeechesFromUrlsWithwrongData() {
        //given
        List<String> urls = Collections.singletonList(getResourceUrl("falseinput.csv"));

        //when
        CSVSERVICE.getSpeechesFromUrls(urls);
    }

    private String getResourceUrl(String resourceFileName) {
        return getClass().getClassLoader().getResource(resourceFileName).toString();
    }

}