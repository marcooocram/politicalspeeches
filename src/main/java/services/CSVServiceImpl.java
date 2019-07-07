package services;

import exceptions.CSVParsingException;
import model.Speech;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVServiceImpl implements CSVService {

    private String dateFormat;
    private String speaker;
    private String topic;
    private String date;
    private String words;

    public CSVServiceImpl(
            @Value("${csv.dateFormat}") String dateFormat,
            @Value("${csv.header.speaker}") String speaker,
            @Value("${csv.header.topic}") String topic,
            @Value("${csv.header.date}") String date,
            @Value("${csv.header.words}") String words
    ) {
        this.dateFormat = dateFormat;
        this.speaker = speaker;
        this.topic = topic;
        this.date = date;
        this.words = words;
    }

    @Override
    public List<Speech> getSpeechesFromUrls(List<String> urls) {
        List<Speech> speeches = new ArrayList<>();
        urls.forEach(url -> speeches.addAll(parse(url)));
        return speeches;
    }

    private List<Speech> parse(String urlString) {
        List<Speech> speeches = new ArrayList<>();
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new CSVParsingException("Malformed url: " + urlString, e);
        }
        try (
                Reader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withHeader(speaker, topic, date, words)
                        .withIgnoreSurroundingSpaces()
                        .withSkipHeaderRecord()
                        .withIgnoreEmptyLines())
        ) {
            for (CSVRecord csvRecord : csvParser) {
                speeches.add(new Speech(
                        csvRecord.get(speaker),
                        csvRecord.get(topic),
                        LocalDate.parse(csvRecord.get(date), DateTimeFormatter.ofPattern(dateFormat)),
                        Integer.parseInt(csvRecord.get(words))

                ));
            }
            return speeches;
        } catch (IOException e) {
            throw new CSVParsingException("couldnt read from url: " + urlString, e);
        }catch (RuntimeException e){
            throw new CSVParsingException("couldnt parse file from url: " + urlString, e);
        }
    }
}
