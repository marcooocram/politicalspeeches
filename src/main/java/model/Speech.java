package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Speech {

    private String speaker;
    private String topic;
    private LocalDate date;
    private Integer words;
}
