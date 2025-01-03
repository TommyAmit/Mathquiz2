package com.example.mathquiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // משתנים גלובליים
    private TextView questionTextView, scoreTextView;
    private Button option1Button, option2Button, option3Button, option4Button;
    private int correctAnswer, score = 0, totalQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // חיבור אלמנטים מה-XML
        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);

        // יצירת השאלה הראשונה
        generateQuestion();

        // האזנה ללחיצות על כפתורי תשובות
        View.OnClickListener answerClickListener = v -> {
            Button clickedButton = (Button) v;
            int selectedAnswer = Integer.parseInt(clickedButton.getText().toString());
            if (selectedAnswer == correctAnswer) {
                score++;
            }
            totalQuestions++;
            updateScore();
            generateQuestion();
        };

        option1Button.setOnClickListener(answerClickListener);
        option2Button.setOnClickListener(answerClickListener);
        option3Button.setOnClickListener(answerClickListener);
        option4Button.setOnClickListener(answerClickListener);
    }

    // פונקציה ליצירת שאלה חדשה
    private void generateQuestion() {
        Random random = new Random();
        int num1 = random.nextInt(20) + 1; // מספר ראשון
        int num2 = random.nextInt(20) + 1; // מספר שני
        int operation = random.nextInt(4); // 0: חיבור, 1: חיסור, 2: כפל, 3: חילוק

        switch (operation) {
            case 0: // חיבור
                correctAnswer = num1 + num2;
                questionTextView.setText(num1 + " + " + num2);
                break;
            case 1: // חיסור
                correctAnswer = num1 - num2;
                questionTextView.setText(num1 + " - " + num2);
                break;
            case 2: // כפל
                correctAnswer = num1 * num2;
                questionTextView.setText(num1 + " × " + num2);
                break;
            case 3: // חילוק
                correctAnswer = num1; // התוצאה תהיה מספר שלם
                questionTextView.setText((num1 * num2) + " ÷ " + num2);
                break;
        }

        // יצירת תשובות אפשריות
        int[] answers = generateAnswers(correctAnswer);
        option1Button.setText(String.valueOf(answers[0]));
        option2Button.setText(String.valueOf(answers[1]));
        option3Button.setText(String.valueOf(answers[2]));
        option4Button.setText(String.valueOf(answers[3]));
    }

    // פונקציה ליצירת תשובות אפשריות
    private int[] generateAnswers(int correctAnswer) {
        Random random = new Random();
        int[] answers = new int[4];
        answers[0] = correctAnswer;

        for (int i = 1; i < 4; i++) {
            int wrongAnswer = 0;
            boolean foundWrongAnswer = false; // משתנה כדי לדעת אם מצאנו תשובה שגויה שמופיעה כבר
            while (!foundWrongAnswer) {
                wrongAnswer = correctAnswer + random.nextInt(20) - 10; // תשובה שגויה קרובה
                foundWrongAnswer = true;
                // בדיקה אם wrongAnswer כבר מופיעה במערך
                for (int j = 0; j < i; j++) {
                    if (answers[j] == wrongAnswer) {
                        foundWrongAnswer = false; // אם התשובה כבר קיימת, נמשיך לחפש תשובה אחרת
                        break;
                    }
                }
            }
            answers[i] = wrongAnswer;
        }

        // ערבוב התשובות
        Collections.shuffle(Arrays.asList(answers));
        return answers;
    }

    // עדכון ניקוד
    private void updateScore() {
        scoreTextView.setText("Score: " + score + "/" + totalQuestions);
    }
}