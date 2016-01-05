package com.example.danpan.hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;



public class GameActivity extends AppCompatActivity {
    private Button guessButton;
    private Button startButton;
    private TextView secretView;
    private EditText letter;
    private TextView attempts;
    private TextView status;
    private TextView score;
    private ServerConnection connection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //connection= (ServerConnection) getIntent().getSerializableExtra(ConnetServerActivity.EXTRA_MESSAGE);
        connection = ServerConnection.getInstance();
       // connection.getStrings().add("_start_");


        letter=(EditText)findViewById(R.id.game_letter);
        guessButton=(Button)findViewById(R.id.game_guess_button);
        startButton=(Button)findViewById(R.id.start_game);
        secretView=(TextView)findViewById(R.id.secret_phrase);
        attempts=(TextView)findViewById(R.id.attempts);
        status=(TextView)findViewById(R.id.status);
        score=(TextView)findViewById(R.id.score);
        secretView.setText("");
        status.setText("");
        attempts.setText("");
        score.setText("");

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  new gameTask().execute(letter.getText().toString());
                GameTask gameTask=new GameTask(letter.getText().toString());
                gameTask.execute();
                letter.setText("");
                //startButton.setEnabled(false);

            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              GameTask gameTask= new GameTask("start");
                gameTask.execute();
                startButton.setEnabled(false);
                guessButton.setEnabled(true);



            }
        });





    }
    public class GameTask extends AsyncTask<Void,Void,Void> {
        String msg;
        String result;
        String retr_status;
        int num;
        String retr_attmeps;
        String error_msg;
        int score_num;
        String str_score;


        GameTask(String msg) {
            this.msg = msg;
        }


        @Override
        protected Void doInBackground(Void... params) {
            if(msg.equals("start")) {


                    connection.startgame();
                    connection.callServer();


            }
            else{
                connection.guess(msg);

                    connection.callServer();

            }
            result=connection.getResult();
            num=connection.getAttempts();
            retr_attmeps=String.valueOf(num);
            retr_status=connection.getStatus();
            error_msg=connection.getErro_msg();
            score_num=connection.getScore();
            str_score=String.valueOf(score_num);
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            if(error_msg==null) {


                secretView.setText(result);
                attempts.setText(retr_attmeps);
                status.setText(retr_status);
                score.setText(str_score);
                if (retr_status.equals("YOU WIN!") || (retr_status.equals("YOU LOSE!"))) {
                    guessButton.setEnabled(false);
                    startButton.setEnabled(true);

                }
            }
            else{
                AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
                builder.setMessage(error_msg).setTitle("Error").setPositiveButton("Back To Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(GameActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();


            }


        }
    }
    }