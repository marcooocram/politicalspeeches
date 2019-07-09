package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Politician {

    private String name;
    private Integer totalWords = 0;
    private Integer securitySpeeches = 0;
    private Integer speechesIn2013 = 0;

    public Politician(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Politician)) return false;
        Politician that = (Politician) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public void increaseWords (Integer additionalWords){
        this.totalWords += additionalWords;
    }

    public void incrementSecuritySpeeches(){
        securitySpeeches++;
    }

    public void incrementSpeechesIn2013(){
        speechesIn2013++;
    }
}
