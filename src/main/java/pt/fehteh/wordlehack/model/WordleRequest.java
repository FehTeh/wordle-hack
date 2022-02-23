package pt.fehteh.wordlehack.model;

import lombok.Data;

@Data
public class WordleRequest {
    
    private String lang;
    private WordleCorrectInfo[] correctLetters;
    private WordlePartialyInfo[] partialyCorrectLetters;
    private String[] excludedLetters;
}
