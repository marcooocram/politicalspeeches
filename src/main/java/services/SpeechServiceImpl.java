package services;

import model.Politician;
import model.Speech;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpeechServiceImpl implements SpeechService {

    @Override
    public Collection<Politician> sortSpeeches(List<Speech> speeches) {
        Map<String, Politician> politicians = new HashMap<>();
        speeches.forEach(speech -> {
            politicians.putIfAbsent(speech.getSpeaker(), new Politician(speech.getSpeaker()));
            Politician politician = politicians.get(speech.getSpeaker());
            if (speech.getDate().getYear() == 2013) {
                politician.incrementSpeechesIn2013();
            }
            if (SECURITY.equals(speech.getTopic())) {
                politician.incrementSecuritySpeeches();
            }
            politician.increaseWords(speech.getWords());
        });
        return politicians.values();
    }
}
