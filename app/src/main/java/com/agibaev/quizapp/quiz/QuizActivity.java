package com.agibaev.quizapp.quiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agibaev.quizapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizActivity extends AppCompatActivity implements QuizAdapter.OnItemClickListener {
    public static final String SEEK_BAR = "amount";
    public static final String CATEGORY_NAME = "category";
    public static final String DIFF_DIFFICULT = "difficult";

    private QuizViewModel mQuizViewModel;
    private QuizAdapter mQuizAdapter;
    private int amount;

    @BindView(R.id.quiz_recycler_view)
    RecyclerView mQuizRecycler;
    @BindView(R.id.quiz_question_amount)
    TextView amountProgressView;
    @BindView(R.id.navigation)
    TextView categoryTextView;
    @BindView(R.id.quiz_progress_bar)
    ProgressBar amountProgressBar;

    public static void start(Context context, int amount, int category, String difficultValue) {
        Intent fakeIntent = new Intent(context, QuizActivity.class);
        fakeIntent.putExtra(SEEK_BAR, amount);
        fakeIntent.putExtra(CATEGORY_NAME, category);
        fakeIntent.putExtra(DIFF_DIFFICULT, difficultValue);
        context.startActivity(fakeIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mQuizViewModel = ViewModelProviders.of(this)
                .get(QuizViewModel.class);

        ButterKnife.bind(this);
        initViewModel();
        showQuizData();
    }

    @SuppressLint("SetTextI18n")
    private void initViewModel() {
        mQuizViewModel.questions.observe(this, questions -> {
            mQuizAdapter.setQuestions(questions);
            categoryTextView.setText(questions.get(0).getCategory());
            amountProgressBar.setMax(questions.size());
        });

        mQuizViewModel.finishEvent.observe(this, aVoid -> finish());
        mQuizViewModel.currentQuestionPosition.observe(this, position -> {
            amountProgressView.setText((position + 1) + "/" + amount);
            amountProgressBar.setProgress(position + 1);
            mQuizRecycler.smoothScrollToPosition(position + 1);
        });
        initRecycler();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecycler() {
        mQuizAdapter = new QuizAdapter(this);
        mQuizRecycler.setHasFixedSize(true);
        mQuizRecycler.setAdapter(mQuizAdapter);
        mQuizRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mQuizRecycler.setOnTouchListener((v, event) -> true);
    }

    private void showQuizData() {
        mQuizViewModel.parseIntentData(getIntent());
        amount = getIntent().getIntExtra(SEEK_BAR, 0);
    }

    @Override
    public void onAnswerClick(int questionPosition, int answerPosition) {
        mQuizViewModel.onAnswerClick(questionPosition, answerPosition);
    }

    @OnClick(R.id.skip_button)
    void onSkipClick(View view) {
        mQuizViewModel.onSkipButtonClick();
    }

    @OnClick(R.id.image_view_previous)
    void onBackToPreviousQuestion(View view) {
        mQuizViewModel.onPreviousQuestion();
    }
}
}
