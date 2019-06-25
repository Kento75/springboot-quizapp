package com.example.quizapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    // クイズ画面表示
    @GetMapping("/quiz")
    public String quiz(Model model) {

        int index = 0;
        if(quizzes.size() != 0) {
            index = new Random().nextInt(quizzes.size());
            model.addAttribute("quiz", quizzes.get(index));
        } else {
            model.addAttribute("quiz", null);
        }


        // クイズ画面
        return "quiz";
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
    public String create(@RequestParam String question, @RequestParam boolean answer) {
        Quiz quiz = new Quiz(question, answer);

        // クイズを追加
        quizzes.add(quiz);

        return "redirect:/page/show";
    }

    // クイズ回答
    @GetMapping("/check")
    public String check(Model model, @RequestParam String  question, @RequestParam boolean answer) {
        // 指定されたquestionを登録済みのクイズから検索
        for(Quiz quiz : quizzes) {
            // 問題を追加
            model.addAttribute("quiz", quiz);

            // クイズが見つかった場合
            if (quiz.getQuestion().equals(question)) {
                // 正解の場合
                if(quiz.isAnswer() == answer) {
                    model.addAttribute("result", "正解！");
                } else {
                    // 不正解の場合
                    model.addAttribute("result", "不正解！");
                }
            }
        }
        return "answer";
    }

    // クイズデータをファイルに保存
    @PostMapping("/save")
    public String save(RedirectAttributes attributes) {
        try {
            quizFileDao.write(quizzes);
            attributes.addFlashAttribute("successMessage","ファイルに保存しました");
        } catch (IOException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage","ファイルの保存に失敗しました");
        }

        return "redirect:/page/show";
    }

    // クイズファイルデータ取得処理
    @GetMapping("/load")
    public String load(RedirectAttributes attributes) {
        try {
            quizzes = quizFileDao.read();
            attributes.addFlashAttribute("successMessage", "ファイルを読み込みました");
        } catch (IOException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage", "ファイルの読み込みに失敗しました");
        }

        return "redirect:/page/show";
    }
}
