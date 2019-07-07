package services;

import model.Speech;

import java.util.List;

public interface CSVService {

   List<Speech> getSpeechesFromUrls(List<String> urls);

}
