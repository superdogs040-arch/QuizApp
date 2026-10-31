package com.example.quizapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends Activity {
    private final String[] questions = {
        "Какой язык используется для разработки Android-приложений?",
        "Как называется файл разметки главного экрана?",
        "Какой компонент показывает текст?",
        "Какой компонент используется для нажатия?",
        "Как называется экран приложения в Android?"
    };
    private final String[][] answers = {
        {"Python", "Kotlin", "HTML", "PHP"},
        {"activity_main.xml", "AndroidManifest.xml", "build.gradle", "README.md"},
        {"Button", "ImageView", "TextView", "EditText"},
        {"TextView", "Button", "ConstraintLayout", "ScrollView"},
        {"Package", "Activity", "Resource", "Manifest"}
    };
    private final int[] correct = {1, 0, 2, 1, 1};
    private int current = 0;
    private int score = 0;
    private TextView questionNumber, questionText;
    private Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionNumber = findViewById(R.id.questionNumber);
        questionText = findViewById(R.id.questionText);
        buttons = new Button[] {
            findViewById(R.id.answer1), findViewById(R.id.answer2),
            findViewById(R.id.answer3), findViewById(R.id.answer4)
        };
        for (int i = 0; i < buttons.length; i++) {
            final int chosen = i;
            buttons[i].setOnClickListener(view -> selectAnswer(chosen));
        }
        if (savedInstanceState != null) {
            current = savedInstanceState.getInt("current", 0);
            score = savedInstanceState.getInt("score", 0);
        }
        if (current >= questions.length) showResult(); else showQuestion();
    }

    private void showQuestion() {
        questionNumber.setText("Вопрос " + (current + 1) + " из " + questions.length);
        questionText.setText(questions[current]);
        for (int i = 0; i < buttons.length; i++) buttons[i].setText(answers[current][i]);
    }

    private void selectAnswer(int chosen) {
        if (chosen == correct[current]) score++;
        current++;
        if (current < questions.length) showQuestion(); else showResult();
    }

    private void showResult() {
        new AlertDialog.Builder(this)
            .setTitle("Викторина завершена")
            .setMessage("Правильных ответов: " + score + " из " + questions.length)
            .setCancelable(false)
            .setPositiveButton("Пройти ещё раз", (dialog, which) -> {
                current = 0;
                score = 0;
                showQuestion();
            })
            .setNegativeButton("На главный экран", (dialog, which) -> finish())
            .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("current", current);
        outState.putInt("score", score);
        super.onSaveInstanceState(outState);
    }
}
