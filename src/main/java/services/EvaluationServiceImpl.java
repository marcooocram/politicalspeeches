package services;

import model.Evaluation;
import model.Politician;
import model.Speech;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService{

    private final CSVService csvService;
    private final SpeechService speechService;

    @Autowired
    public EvaluationServiceImpl (CSVService csvService,SpeechService speechService){
        this.csvService = csvService;
        this.speechService = speechService;
    }

    @Override
    public Evaluation evaluate(List<String> urls) {
        List<Speech> speeches = csvService.getSpeechesFromUrls(urls);
        Collection<Politician> politicians = speechService.sortSpeeches(speeches);
        return new Evaluation(getMostSpeeches(politicians),getMostSecurity(politicians),getLeastWordy(politicians));
    }

    private String getLeastWordy(Collection<Politician>  politicians) {
        Optional<Politician> leastWordy = politicians.stream().min(Comparator.comparing(Politician::getTotalWords));
        if (!leastWordy.isPresent()){
            return null;
        }
        if ( politicians
                .stream()
                .filter(politician -> Objects.equals(politician.getTotalWords(), leastWordy.get().getTotalWords()))
                .count() != 1.0){
            return null;
        }
        return leastWordy.get().getName();
    }

    private String getMostSecurity(Collection<Politician> politicians){
        Optional<Politician> mostSecurity = politicians.stream().max(Comparator.comparing(Politician::getSecuritySpeeches));
        if (!mostSecurity.isPresent()){
            return null;
        }
        if ( politicians
                .stream()
                .filter(politician -> Objects.equals(politician.getSecuritySpeeches(), mostSecurity.get().getSecuritySpeeches()))
                .count() != 1L){
            return null;
        }
        return mostSecurity.get().getName();
    }

    private String getMostSpeeches(Collection<Politician> politicians){
        Optional<Politician> mostSpeeches = politicians.stream().max(Comparator.comparing(Politician::getSpeechesIn2013));
        if (!mostSpeeches.isPresent()){
            return null;
        }
        if ( politicians
                .stream()
                .filter(politician -> Objects.equals(politician.getSpeechesIn2013(), mostSpeeches.get().getSpeechesIn2013()))
                .count() != 1L)
        {
            return null;
        }
        return mostSpeeches.get().getName();
    }
}
