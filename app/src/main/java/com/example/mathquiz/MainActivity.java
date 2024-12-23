package com.example.mathquiz;


import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private LinearLayout llOptions;
    private Button btnChangeOptions;

    private int correctAnswer; // התשובה הנכונה
    private int numOptions = 4; // ברירת מחדל של כמות האפשרויות
    private Random random = new Random(); // אובייקט רנדומליות

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // אתחול רכיבים מה-XML
        tvQuestion = findViewById(R.id.tv_question);
        llOptions = findViewById(R.id.ll_options);
        btnChangeOptions = findViewById(R.id.btn_change_options);

        // התחלת השאלה הראשונה
        startNewQuestion();

        // כפתור לשינוי כמות האפשרויות
        btnChangeOptions.setOnClickListener(v -> showOptionsDialog());
    }

    // פונקציה שמתחילה שאלה חדשה
    private void startNewQuestion() {
        llOptions.removeAllViews(); // מנקה את האפשרויות הקודמות

        // קביעת סוג השאלה - כפל או חזקה
        boolean isPowerQuestion = random.nextBoolean();
        int a = random.nextInt(10) + 1; // מספר ראשון (1-10)
        int b = random.nextInt(4) + 2;  // מספר שני (חזקה 2-5 או כפל 2-10)

        if (isPowerQuestion) {
            correctAnswer = (int) Math.pow(a, b); // חישוב חזקה
            tvQuestion.setText(a + " ^ " + b); // הצגת שאלה של חזקה
        } else {
            correctAnswer = a * b; // חישוב כפל
            tvQuestion.setText(a + " * " + b); // הצגת שאלה של כפל
        }

        // יצירת אפשרויות תשובה
        List<Integer> options = generateOptions(correctAnswer, numOptions);
        for (int option : options) {
            Button btnOption = new Button(this);
            btnOption.setText(String.valueOf(option));
            btnOption.setOnClickListener(v -> checkAnswer(option)); // מאזין ללחיצה
            llOptions.addView(btnOption); // הוספת הכפתור ל-LinearLayout
        }
    }

    // פונקציה שיוצרת רשימה של אפשרויות תשובה עם אחת נכונה
    private List<Integer> generateOptions(int correctAnswer, int numOptions) {
        List<Integer> options = new ArrayList<>();
        options.add(correctAnswer); // מוסיף את התשובה הנכונה

        while (options.size() < numOptions) {
            int fakeAnswer = random.nextInt(500) + 1; // מספר רנדומלי (1-500)
            if (!options.contains(fakeAnswer)) {
                options.add(fakeAnswer); // מוסיף רק אם לא קיים
            }
        }
        Collections.shuffle(options); // מערבב את הרשימה
        return options;
    }

    // פונקציה לבדיקת תשובה בלחיצה על כפתור
    private void checkAnswer(int userAnswer) {
        if (userAnswer == correctAnswer) {
            Toast.makeText(this, "תשובה נכונה!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "תשובה שגויה! התשובה הנכונה היא: " + correctAnswer, Toast.LENGTH_LONG).show();
        }
        startNewQuestion(); // מעביר לשאלה הבאה
    }

    // דיאלוג לשינוי מספר האפשרויות
    private void showOptionsDialog() {
        String[] options = {"3", "4", "5", "6", "7", "8"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("בחר כמות אפשרויות תשובה:");
        builder.setItems(options, (dialog, which) -> {
            numOptions = Integer.parseInt(options[which]);
            startNewQuestion(); // אתחול מחדש עם מספר אפשרויות חדש
        });
        builder.show();
    }
}

