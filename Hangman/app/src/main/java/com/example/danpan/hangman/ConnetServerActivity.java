package com.example.danpan.hangman;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.app.AlertDialog;
import java.io.Serializable;


public class ConnetServerActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "hangman.MESSAGE";
    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect;
    String error_msg=null;
    //private ServerConnection connection=null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connet_server);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);

        textResponse = (TextView) findViewById(R.id.response);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        editTextAddress.setText("");
        editTextPort.setText("");


    }

    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {



                    MyClientTask myClientTask = new MyClientTask(
                            editTextAddress.getText().toString(),
                            editTextPort.getText().toString());
                    myClientTask.execute();
                }
            };



    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        String dstPort;


        String wordToShow;


        MyClientTask(String addr, String port) {
            dstAddress = addr;
            dstPort = port;

        }

        @Override
        protected  Void doInBackground(Void... arg0) {


            try{
                Integer.parseInt(dstPort);

            }catch (NumberFormatException e){
                error_msg="NumberFormatException:"+e.toString();
                return null;

            }
            ServerConnection connection = ServerConnection.getInstance();
            connection.initialize(dstAddress, Integer.parseInt(dstPort));
           //connection.run();
            connection.connect();
            error_msg=connection.getErro_msg();


            return null;
        }



        @Override
        protected void onPostExecute(Void result) {
            if(error_msg==null){
                Intent intent= new Intent(ConnetServerActivity.this,GameActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, connection);
                startActivity(intent);
            }
            else{
                AlertDialog.Builder builder=new AlertDialog.Builder(ConnetServerActivity.this);
                builder.setMessage(error_msg).setTitle("No connection").setPositiveButton("Back to Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(ConnetServerActivity.this,MainActivity.class);
                        startActivity(intent);
                        error_msg=null;
                        editTextAddress.setText("");
                        editTextPort.setText("");

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();


            }




        }


    }


}


