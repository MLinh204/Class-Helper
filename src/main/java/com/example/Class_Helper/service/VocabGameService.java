package com.example.Class_Helper.service;

import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.model.Vocab;
import com.example.Class_Helper.model.VocabGameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class VocabGameService {
    @Autowired
    private VocabService vocabService;
    @Autowired
    private StudentService studentService;
    private final Map<String, VocabGameSession> activeGames = new HashMap<>();
    public void initializeGame(String gameId, Long studentId, Long vocabGroupId) {
        VocabGameSession session = new VocabGameSession();
        session.setGameId(gameId);
        session.setStudentId(studentId);
        session.setVocabGroupId(vocabGroupId);
        session.setRemainingVocabs(initializeVocabList(vocabGroupId));
        session.setTotalPoints(0);
        session.setCorrectAnswers(0);
        session.setIncorrectAnswers(0);

        activeGames.put(gameId, session);
    }
    public List<Vocab> initializeVocabList(Long vocabGroupId){
        List<Vocab> findVocabs = vocabService.getAllVocabsInGroup(vocabGroupId);
        List<Vocab> vocabList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Vocab vocab = findVocabs.get(new Random().nextInt(findVocabs.size()));
            vocabList.add(vocab);
        }
        return vocabList;
    }
    public Vocab getNextVocab(String gameId){
        VocabGameSession session = activeGames.get(gameId);
        List<Vocab> vocabs = session.getRemainingVocabs();
        if (vocabs.size() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No more vocabulary words available. The game has ended.");
        }
        int index = new Random().nextInt(session.getRemainingVocabs().size());
        Vocab nextVocab = vocabs.get(index);
        session.getRemainingVocabs().remove(index);
        return nextVocab;
    }
    public void submitAnswer(String gameId, boolean isCorrect, boolean useHint){
        VocabGameSession session = activeGames.get(gameId);
        if(isCorrect){
            if (useHint){
                session.setCorrectAnswers(session.getCorrectAnswers() + 1);
                session.setTotalPoints(session.getTotalPoints() + 10);
            } else {
                session.setCorrectAnswers(session.getCorrectAnswers() + 1);
                session.setTotalPoints(session.getTotalPoints() + 20);
            }
        } else {
            session.setIncorrectAnswers(session.getIncorrectAnswers() + 1);
        }
    }
    public VocabGameSession gameEnd(String gameId, Long studentId){
        VocabGameSession session = activeGames.get(gameId);
        if (session.getRemainingVocabs().size() == 0){
            try {
                Student student = studentService.getStudentById(studentId);
                student.setPoint(student.getPoint() + session.getTotalPoints());
                studentService.updateStudent(student);
            } catch (NoSuchElementException e){
                System.out.println("Student not found");
            }
        }
        activeGames.remove(gameId);
        return session;
    }
    public VocabGameSession getGameSession(String gameId){
        return activeGames.get(gameId);
    }

}
