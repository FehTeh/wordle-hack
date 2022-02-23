package pt.fehteh.wordlehack.service.impl;

import pt.fehteh.wordlehack.model.WordlePartialyInfo;
import pt.fehteh.wordlehack.model.WordleCorrectInfo;
import pt.fehteh.wordlehack.model.WordleRequest;
import pt.fehteh.wordlehack.service.WordleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

@Service
public class LocalWordleService implements WordleService {

    Logger logger = Logger.getLogger(LocalWordleService.class.getName());

    public String getServiceType() { return "LOCAL";};

    public String getRandomWord(String lang) throws IOException {
        
        ClassLoader classLoader = LocalWordleService.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(lang + ".csv");

        String[] words = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).split(System.lineSeparator());
        
        List<String> possibleSolutions = new ArrayList<String>(Arrays.asList(words));  
        
        Random rand = new Random();
        return possibleSolutions.get(rand.nextInt(possibleSolutions.size()));
    }

    public String getPossibleSolution(WordleRequest request) throws IOException {

        ClassLoader classLoader = LocalWordleService.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(request.getLang() + ".csv");

        String[] words = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).split("\n");
        
        List<String> possibleSolutions = new ArrayList<String>(Arrays.asList(words));  

        for(WordleCorrectInfo correctChar : request.getCorrectLetters())
        {
            possibleSolutions.removeIf(w -> !getSimilarChars(correctChar.getLetter()).contains(w.charAt(correctChar.getPosition() - 1)));
        }

        for(WordlePartialyInfo partialyChar : request.getPartialyCorrectLetters())
        {
            for(Integer position : partialyChar.getPositions())
            {
                possibleSolutions.removeIf(w -> !w.contains(partialyChar.getLetter().toString()));
                possibleSolutions.removeIf(w -> getSimilarChars(partialyChar.getLetter()).contains(w.charAt(position - 1)));
            }
        }

        for(String excludeChar : request.getExcludedLetters())
        {
            possibleSolutions.removeIf(w -> w.contains(excludeChar));
        }

        logger.info("Possible Solutions: " + possibleSolutions.size());

        Random rand = new Random();

        return possibleSolutions.get(rand.nextInt(possibleSolutions.size()));
    }

    private List<Character> getSimilarChars(Character letter)
    {
        List<Character> letters = new ArrayList<Character>();

        switch(letter){
            case 'a':
                letters.add('á');
                letters.add('à');
                letters.add('ã');
                break;
            case 'c':
                letters.add('ç');
                break;
            case 'e':
                letters.add('é');
                break;
            case 'o':
                letters.add('ó');
                break;
            case 'i':
                letters.add('í');
                break;
        }

        letters.add(letter);
        return letters; 
    } 
}
