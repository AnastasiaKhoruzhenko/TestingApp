package com.example.testingapp.Modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
    public String question;
    public String answer1, answer2, answer3, answer4, correctAnswer;

    public Question(String question, String answer1, String answer2, String answer3, String answer4)
    {
        this.question = question;
        this.correctAnswer = answer1;
        List<String> list = new ArrayList<>();
        list.add(answer1);
        list.add(answer2);
        list.add(answer3);
        list.add(answer4);
        Collections.shuffle(list);
        this.answer1 = list.get(0);
        this.answer2 = list.get(1);
        this.answer3 = list.get(2);
        this.answer4=list.get(3);
    }

    public Question(){}

    public String getQuestion(){return question;}
    public void setQuestion(String question){this.question=question;}

    public String getAnswer1(){return answer1;}
    public void setAnswer1(String answer1){this.answer1=answer1;}

    public String getAnswer2(){return answer2;}
    public void setAnswer2(String answer2){this.answer2=answer2;}

    public String getAnswer3(){return answer3;}
    public void setAnswer3(String answer3){this.answer3=answer3;}

    public String getCorrectAnswer(){return correctAnswer;}
    public void setCorrectAnswer(String correctAnswer){this.correctAnswer=correctAnswer;}
}
