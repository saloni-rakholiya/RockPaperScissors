package com.example.rockpaperscissors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView mRoundsText;
    int mRounds;
    boolean mIsSinglePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoundsText = findViewById(R.id.rounds_text);
        mRounds = Integer.parseInt(mRoundsText.getText().toString());
        if(savedInstanceState != null){
            mRounds = savedInstanceState.getInt("mRounds");
            mRoundsText.setText(""+mRounds);
        }
        mIsSinglePlayer = false;
        CheckBox singlePlayerCheckBox = (CheckBox) findViewById(R.id.single_player_checkbox);
        singlePlayerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LinearLayout playerTwoLayout = findViewById(R.id.player_two_layout);
                if(isChecked){
                    mIsSinglePlayer = true;
                    playerTwoLayout.setVisibility(View.GONE);
                }else{
                    mIsSinglePlayer = false;
                    playerTwoLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mRounds", mRounds);
    }

    public void addRounds(View view){
        if (mRounds < 10){
            mRounds += 1;
            mRoundsText.setText(""+mRounds);
        }
    }

    public void subtractRounds(View view){
        if (mRounds > 1){
            mRounds -= 1;
            mRoundsText.setText(""+mRounds);
        }
    }

    public void submitInfo(View view){
        EditText playerOneEdit = findViewById(R.id.player_one_edit);
        EditText playerTwoEdit = findViewById(R.id.player_two_edit);
        String playerOneName, playerTwoName;
        playerOneName = playerOneEdit.getText().toString();
        playerTwoName = playerTwoEdit.getText().toString();
        if(((playerOneName.equals("") || playerTwoName.equals("")) && !mIsSinglePlayer) ||
                (playerOneName.equals(""))){
            Toast.makeText(this, "Enter names of Players!", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("player_one_name", playerOneName);
            intent.putExtra("is_single_player", mIsSinglePlayer);
            if(!mIsSinglePlayer)
                intent.putExtra("player_two_name", playerTwoName);
            intent.putExtra("rounds_number", mRounds);
            startActivity(intent);
        }
    }
}
