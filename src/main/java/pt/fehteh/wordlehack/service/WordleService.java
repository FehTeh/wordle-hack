package pt.fehteh.wordlehack.service;

import java.io.IOException;

import pt.fehteh.wordlehack.model.WordleRequest;

public interface WordleService {

    String getServiceType();

    String getRandomWord(String lang) throws IOException;

    String getPossibleSolution(WordleRequest request) throws IOException;
}
