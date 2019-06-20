package com.example.quizapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class QuizAppController {

    private List<Quiz> quizzes = new ArrayList<>();

    // クイズ一覧表示
    @GetMapping("/show")
    public List<Quiz> show() {
        return quizzes;
    }

    // クイズ作成
    @PostMapping("/create")
    public void create(@RequestParam String question, @RequestParam boolean answer) {
        Quiz quiz = new Quiz(question, answer);

        // クイズを追加
        quizzes.add(quiz);
    }

    // クイズ回答
    @GetMapping("/check")
    public String check(@RequestParam String  question, @RequestParam boolean answer) {
        // TODO: 回答が正しいかどうかチェックして、結果を返却
        // 指定されたquestionを登録済みのクイズから検索
        for(Quiz quiz : quizzes) {
            // クイズが見つかった場合
            if (quiz.getQuestion().equals(question)) {
                // 正解の場合
                return "見つかった!：" + quiz.getQuestion();
                // 不正解の場合

            }
        }
        // クイズが見つからなかった場合
        return "";
    }
}
