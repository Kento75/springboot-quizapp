package com.example.quizapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("page")
public class QuizAppController {

    private List<Quiz> quizzes = new ArrayList<>();

    private QuizFileDao quizFileDao = new QuizFileDao();

    // ランダムにクイズを１件取得
    @GetMapping("/quiz")
    public Quiz quiz() {
        int index = new Random().nextInt(quizzes.size());

        return quizzes.get(index);
    }

    // クイズ一覧画面表示
    @GetMapping("/show")
    public String show(Model model) {
        model.addAttribute("quizzes", quizzes);
        // list.html をレスポンス
        return "list";
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

    // クイズファイルデータ取得処理
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
