package com.example.quizapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuizAppController {

    private List<Quiz> quizzes = new ArrayList<>();

    private QuizFileDao quizFileDao = new QuizFileDao();

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
                if(quiz.isAnswer() == answer) {
                    return "正解！";
                } else {
                  // 不正解の場合
                  return "不正解！";
                }
            }
        }
        // クイズが見つからなかった場合
        return "";
    }

    // クイズデータをファイルに保存
    @PostMapping("/save")
    public String save() {
        try {
            quizFileDao.write(quizzes);
            return "ファイルに保存しました";
        } catch (IOException e) {
            e.printStackTrace();
            return "ファイルの保存に失敗しました";
        }
    }

    //
    @GetMapping("/load")
    public String load() {
        try {
            quizzes = quizFileDao.read();
            return "ファイルを読み込みました";
        } catch (IOException e) {
            e.printStackTrace();
            return "ファイルの読み込みに失敗しました";
        }
    }
}
