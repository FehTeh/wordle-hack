package pt.fehteh.wordlehack.controller;

import org.springframework.web.bind.annotation.RestController;

import pt.fehteh.wordlehack.model.WordleRequest;
import pt.fehteh.wordlehack.model.WordleResponse;
import pt.fehteh.wordlehack.service.WordleService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private WordleService service;

    @GetMapping("/word")
    public WordleResponse getWord() throws IOException {
        return new WordleResponse(service.getRandomWord("pt-br"));
    }

	@PostMapping("/words")
    public String resolve(@RequestBody WordleRequest request) throws IOException {
        return service.getPossibleSolution(request);
    }

}
