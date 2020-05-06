package com.example.rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private int mTotalRounds;
    private int mCurrentRound;
    private int mPlayerOneScore;
    private int mPlayerTwoScore;
    private String mPlayerOneName;
    private String mPlayerTwoName;
    private boolean mFirstsTurn;
    private boolean mIsRoundOver;
    private boolean mIsSinglePlayer;
    private View mPlayerOneOption;
    private View mPlayerTwoOption;
    private ImageView mRockImage;
    private ImageView mPaperImage;
    private ImageView mScissorsImage;
    private Button mNextRoundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mPlayerOneName = extras.getString("player_one_name");
            mIsSinglePlayer = extras.getBoolean("is_single_player");
            if(mIsSinglePlayer){
                mPlayerTwoName = "COMPUTER";
            }
            else{
                mPlayerTwoName = extras.getString("player_two_name");
            }
            mTotalRounds = extras.getInt("rounds_number");
        }
        mRockImage = findViewById(R.id.rock_image);
        mPaperImage = findViewById(R.id.paper_image);
        mScissorsImage = findViewById(R.id.scissors_image);
        mNextRoundButton = findViewById(R.id.next_round_button);
        mFirstsTurn = true;
        mCurrentRound = 1;
        mPlayerOneScore = 0;
        mPlayerTwoScore = 0;
        mPlayerOneOption = null;
        mPlayerTwoOption = null;
        mIsRoundOver = false;
        displayInfo();
    }

    public void onClickOption(View view){
        if(mIsRoundOver)
            return;
        if(mFirstsTurn){
            mPlayerOneOption = view;
            mFirstsTurn = false;
            if(mIsSinglePlayer){
                Random random = new Random();
                switch (random.nextInt(3)){
                    case 0:
                        mPlayerTwoOption = mRockImage;
                        break;
                    case 1:
                        mPlayerTwoOption = mPaperImage;
                        break;
                    case 2:
                        mPlayerTwoOption = mScissorsImage;
                }
                displayResult();
            }
            displayInfo();
        }
        else{
            mPlayerTwoOption = view;
            displayResult();
            displayInfo();
        }
    }

    public void gotoNextRound(View view){
        mPlayerOneOption = null;
        mPlayerTwoOption = null;
        mCurrentRound += 1;
        mFirstsTurn = true;
        mIsRoundOver = false;
        mNextRoundButton.setVisibility(View.INVISIBLE);
        RelativeLayout relativeLayout = findViewById(R.id.relative_layout);
        relativeLayout.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.background, null));
        displayInfo();
    }

    private void displayResult(){
        mIsRoundOver = true;
        TextView resultText= findViewById(R.id.result_text);
        RelativeLayout relativeLayout = findViewById(R.id.relative_layout);
        int green = ResourcesCompat.getColor(getResources(), R.color.green, null);
        int red = ResourcesCompat.getColor(getResources(), R.color.red, null);
        String result = "";
        if(mPlayerOneOption == mPlayerTwoOption){
            result = "Both players chose";
            if (mPlayerOneOption == mPaperImage)
                result += " Paper";
            else if(mPlayerOneOption == mRockImage)
                result += " Rock";
            else if(mPlayerOneOption == mScissorsImage)
                result += " Scissors";
            result += " .\nRound " + mCurrentRound + " is a Draw";
        }
        else{
            if (mPlayerOneOption == mPaperImage) {
                if(mPlayerTwoOption == mRockImage) {
                    result = "Paper beats Rock.\n" + mPlayerOneName + " wins Round " + mCurrentRound;
                    mPlayerOneScore += 1;
                    if (mIsSinglePlayer)
                        relativeLayout.setBackgroundColor(green);
                }
                else if(mPlayerTwoOption == mScissorsImage) {
                    result = "Scissors beats Paper.\n" + mPlayerTwoName + " wins Round " + mCurrentRound;
                    mPlayerTwoScore += 1;
                    if (mIsSinglePlayer)
                        relativeLayout.setBackgroundColor(red);
                }
            }
            else if(mPlayerOneOption == mRockImage) {
                if(mPlayerTwoOption == mScissorsImage) {
                    result = "Rock beats Scissors.\n" + mPlayerOneName + " wins Round " + mCurrentRound;
                    mPlayerOneScore += 1;
                    if (mIsSinglePlayer)
                        relativeLayout.setBackgroundColor(green);
                }
                else if(mPlayerTwoOption == mPaperImage) {
                    result = "Paper beats Rock.\n" + mPlayerTwoName + " wins Round " + mCurrentRound;
                    mPlayerTwoScore += 1;
                    if (mIsSinglePlayer)
                        relativeLayout.setBackgroundColor(red);
                }
            }
            else if(mPlayerOneOption == mScissorsImage) {
                if(mPlayerTwoOption == mPaperImage) {
                    result = "Scissors beats Paper.\n" + mPlayerOneName + " wins Round " + mCurrentRound;
                    mPlayerOneScore += 1;
                    if (mIsSinglePlayer)
                        relativeLayout.setBackgroundColor(green);
                }
                else if(mPlayerTwoOption == mRockImage) {
                    result = "Rock beats Scissors.\n" + mPlayerTwoName + " wins Round " + mCurrentRound;
                    mPlayerTwoScore += 1;
                    if (mIsSinglePlayer)
                        relativeLayout.setBackgroundColor(red);
                }
            }
        }
        if(mCurrentRound < mTotalRounds)
            mNextRoundButton.setVisibility(View.VISIBLE);
        else{
            TextView finalResultText = findViewById(R.id.final_result_text);
            String finalResult;
            if(mPlayerOneScore == mPlayerTwoScore){
                finalResult = "The Game has tied!";
            }
            else{
                if(mPlayerOneScore > mPlayerTwoScore)
                    finalResult = mPlayerOneName + " has won the game.";
                else
                    finalResult = mPlayerTwoName + " has won the game.";
            }
            finalResultText.setVisibility(View.VISIBLE);
            finalResultText.setText(finalResult);
        }
        resultText.setVisibility(View.VISIBLE);
        resultText.setText(result);
    }

    private void displayInfo(){
        TextView totalRoundsText = findViewById(R.id.total_rounds);
        TextView currentRoundText = findViewById(R.id.current_round);
        TextView playerOneText = findViewById(R.id.player_one_text);
        TextView playerTwoText = findViewById(R.id.player_two_text);
        TextView turnText = findViewById(R.id.turn_text);
        totalRoundsText.setText("Total Rounds: " + mTotalRounds);
        currentRoundText.setText("Current Round: " + mCurrentRound);
        playerOneText.setText(mPlayerOneName + ": " +mPlayerOneScore);
        playerTwoText.setText(mPlayerTwoName + ": " + mPlayerTwoScore);
        if(mFirstsTurn){
            turnText.setText("It's " + mPlayerOneName + "'s Turn");
        }
        else{
            turnText.setText("It's " + mPlayerTwoName + "'s Turn");
        }
        if(!mIsRoundOver){
            TextView resultText = findViewById(R.id.result_text);
            resultText.setVisibility(View.INVISIBLE);
        }
    }
}
