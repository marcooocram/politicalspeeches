package services;

import model.Politician;
import model.Speech;

import java.util.Collection;
import java.util.List;

public interface SpeechService {
    String SECURITY = "Innere Sicherheit";
    Collection<Politician> sortSpeeches(List<Speech> speeches);
}
