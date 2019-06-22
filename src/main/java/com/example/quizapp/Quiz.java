package com.example.quizapp;

public class Quiz {

    /**
     * 問題文
     */
    private String question;

    /**
     * クイズ正解
     */
    private boolean answer;

    public Quiz(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isAnswer() {
        return answer;
    }

    // 問題文と答えを文字列として返却
    // 例： 問題文 ○
    @Override
    public String toString() {
        String marubatsu = answer ? "○" : "×";
        return question + " " + marubatsu;
    }
}
