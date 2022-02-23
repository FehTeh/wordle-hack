package pt.fehteh.wordlehack.model;

import lombok.Data;

@Data
public class WordlePartialyInfo {
    
    private Character letter;
    private Integer[] positions;
}
