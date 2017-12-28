package com.surowka.service;

import com.surowka.util.Constatns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class QuizMaker {

    private static final Logger log = LoggerFactory.getLogger(QuizMaker.class);
    private Map<String, String> quizData;

    public GuiConsole getGuiConsole() {
        return guiConsole;
    }

    public QuizMaker setGuiConsole(GuiConsole guiConsole) {
        this.guiConsole = guiConsole;
        return this;
    }

    private GuiConsole guiConsole;

    public QuizMaker prepareQuiz(Map<String, String> quizData) {
        //log.info("quiz data:");
        //quizData.entrySet().stream().forEach(System.out::println);
        this.quizData = quizData;
        return this;
    }

    public void startQuiz() {
        try {
            Map<String, Boolean> result = new LinkedHashMap<>();
            int questionNr = 1;
            Iterator<Map.Entry<String, String>> iterator = quizData.entrySet().iterator();

            guiConsole.print(Constatns.LINE_SEPARATOR);
            guiConsole.print(" Welcome Star Wars and Pokemon quiz ");
            guiConsole.print(Constatns.LINE_SEPARATOR);
            guiConsole.print("possible answers:");
            guiConsole.print(" s - for StarWars");
            guiConsole.print(" p - for Pokemon");
            guiConsole.print(" e - exit quiz");
            guiConsole.print(Constatns.LINE_SEPARATOR);
            guiConsole.print("Sample question number 0, 'Pikachu' is this species belongs to either StarWars or Pokemon world ?");
            guiConsole.print("p");
            guiConsole.print("Correct answer!");
            guiConsole.print(Constatns.LINE_SEPARATOR);
            guiConsole.print("Quiz has started");
            guiConsole.print(Constatns.LINE_SEPARATOR);
            while (true) {
                Map.Entry<String, String> entry = iterator.next();
                String question = String.format("Question number %s, '%s' is this species belongs to either StarWars's or Pokemon's world ? ", questionNr, entry.getKey());
                Boolean isCorrectAnswer = false;
                guiConsole.print(question);
                String userAnswer = guiConsole.getUserAnswer();
                while (StringUtils.isEmpty(userAnswer) || (!"p".equals(userAnswer) && !"s".equals(userAnswer) && !"e".equals(userAnswer))) {
                    guiConsole.print("Please type 's', 'p' or 'e'");
                    userAnswer = guiConsole.getUserAnswer();
                }
                if ("e".equals(userAnswer)) {
                    printOutcome(result);
                    break;
                }
                if (userAnswer.equals(entry.getValue())) {
                    guiConsole.print("Correct!");
                    isCorrectAnswer = true;
                } else
                    guiConsole.print("WRONG");

                result.put(question, isCorrectAnswer);

                if (questionNr == 15) {
                    guiConsole.print(Constatns.LINE_SEPARATOR);
                    guiConsole.print("You have answered for all questions");
                    guiConsole.print(Constatns.LINE_SEPARATOR);
                    printOutcome(result);
                    break;
                }
                ++questionNr;
            }
        } catch (Exception ex) {
            log.error("error!", ex);
        } finally {
            guiConsole.destroyGui();
        }
    }

    public void printOutcome(Map<String, Boolean> result) {
        StringBuilder msgToUser = new StringBuilder();
        result.entrySet().stream().forEach(entry -> log.info(String.join(",", entry.getKey(), entry.getValue().toString())));
        long points = result.entrySet().stream().filter(entry -> entry.getValue()).count();
        msgToUser.append(Constatns.LINE_SEPARATOR);
        msgToUser.append(System.getProperty("line.separator"));
        msgToUser.append("Your score is: " + points);
        msgToUser.append(System.getProperty("line.separator"));
        msgToUser.append(Constatns.LINE_SEPARATOR);
        msgToUser.append(System.getProperty("line.separator"));
        msgToUser.append("QUIZ MESSAGE:  ");
        if (points <= 5) {
            msgToUser.append("You're a SF Noob!");
        } else if (points > 5 && points <= 10) {
            msgToUser.append("Looks like you're a huge SF fan!");
        } else {
            msgToUser.append("You're a SF master! Get out of your basement and see the outside world!");
        }
        msgToUser.append(System.getProperty("line.separator"));
        msgToUser.append(Constatns.LINE_SEPARATOR);
        msgToUser.append(System.getProperty("line.separator"));
        guiConsole.print(msgToUser.toString());
    }
}
