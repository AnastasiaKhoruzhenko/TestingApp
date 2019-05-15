package com.example.testingapp.Modules;

import java.util.ArrayList;
import java.util.List;

public class Question {
    public Integer size;
    private String question, answer1, answer2, answer3, answer4, answer5, correctAnswer;

    public Question(String question, String answer1, String answer2, String answer3, String answer4, String answer5, String correctAnswer)
    {
        this.question = question;
        this.correctAnswer=correctAnswer;
        List<String> list = new ArrayList<>();
        list.add(answer1);
        list.add(answer2);
        list.add(answer3);
        list.add(answer4);
        list.add(answer5);
        list.add(correctAnswer);

        this.answer1 = list.get(0);
        this.answer2 = list.get(1);
        this.answer3 = list.get(2);
        this.answer4=list.get(3);
        this.answer5=list.get(4);
        this.correctAnswer=list.get(5);
        size=7;
    }

    public Question(String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer)
    {
        this.question = question;
        this.correctAnswer=correctAnswer;
        List<String> list = new ArrayList<>();
        list.add(answer1);
        list.add(answer2);
        list.add(answer3);
        list.add(answer4);
        list.add(correctAnswer);

        this.answer1 = list.get(0);
        this.answer2 = list.get(1);
        this.answer3 = list.get(2);
        this.answer4=list.get(3);
        this.correctAnswer=list.get(4);
        size=6;
    }

    public Question(String question, String answer1, String answer2, String answer3, String correctAnswer)
    {
        this.question = question;
        this.correctAnswer=correctAnswer;
        List<String> list = new ArrayList<>();
        list.add(answer1);
        list.add(answer2);
        list.add(answer3);
        list.add(correctAnswer);

        this.answer1 = list.get(0);
        this.answer2 = list.get(1);
        this.answer3 = list.get(2);
        this.correctAnswer=list.get(3);
        size=5;
    }

    public Question(String question, String answer1, String answer2, String correctAnswer)
    {
        this.question = question;
        this.correctAnswer=correctAnswer;
        List<String> list = new ArrayList<>();
        list.add(answer1);
        list.add(answer2);
        list.add(correctAnswer);

        this.answer1 = list.get(0);
        this.answer2 = list.get(1);
        this.correctAnswer=list.get(2);
        size=4;
    }

    public String getQuestion(){return question;}

    public String getAnswer1(){return answer1;}
    public void setAnswer1(String answer1){this.answer1=answer1;}

    public String getAnswer2(){return answer2;}
    public void setAnswer2(String answer2){this.answer2=answer2;}

    public String getAnswer3(){return answer3;}
    public void setAnswer3(String answer3){this.answer3=answer3;}

    public String getAnswer4(){return answer4;}
    public void setAnswer4(String answer4){this.answer4=answer4;}

    public String getAnswer5(){return answer5;}
    public void setAnswer5(String answer5){this.answer5=answer5;}

    public String getCorrectAnswer(){return correctAnswer;}
    public void setCorrectAnswer(String correctAnswer){this.correctAnswer=correctAnswer;}
}
