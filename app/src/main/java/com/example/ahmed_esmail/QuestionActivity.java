package com.example.ahmed_esmail;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        final String Id=getIntent().getExtras().getString("ID");
        final String username=getIntent().getExtras().getString("Username");
        final String actions=getIntent().getExtras().getString("action");
        TextView Username=(TextView)findViewById(R.id.textView1);
        Username.setText("Your Username : "+username);
        //------------------------------------------------------------------------------
        final EditText question1=(EditText)findViewById(R.id.question1_editText);
        final EditText question2=(EditText)findViewById(R.id.question2_editText);
        final EcommerceDatabase DB=new EcommerceDatabase(this);
        Button Ok=(Button)findViewById(R.id.ok_button);
        final Intent To_ResetPassword=new Intent(QuestionActivity.this,ResetPasswordActivity.class);
        final Intent To_LoginAgain=new Intent(QuestionActivity.this,LoginActivity.class);
        final Intent T0_MainPage=new Intent(QuestionActivity.this,MainPageActivity.class);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((question1.getText().toString()).equals(""))
                { Toast.makeText(getApplicationContext(),"Enter your answer for Question1 ..",Toast.LENGTH_LONG).show(); }
                //---------------------------------------------------------
                else if((question2.getText().toString()).equals(""))
                {Toast.makeText(getApplicationContext(),"Enter your answer for Question2 ..",Toast.LENGTH_LONG).show();}
                //---------------------------------------------------------
                else
                {
                    if(actions.equals("ForgotPassword"))
                    {
                        //Check Q1 and Q2 is right
                        //if rigth go to reset password with id and username
                        //if wrong toast(اتاكد من اليوسرنيم) and go to login
                        Cursor c=DB.ChechRigthQuestions(question1.getText().toString(),question2.getText().toString(),Id);
                        if (c==null)
                        {
                            //go to reset password with id and username
                            To_ResetPassword.putExtra("ID",Id);
                            To_ResetPassword.putExtra("Username",username);
                            startActivity(To_ResetPassword);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Your Answer is Wrong try again Later .. ",Toast.LENGTH_LONG).show();
                            //Go to Login
                            startActivity(To_LoginAgain);
                        }
                    }
                    else if (actions.equals("InsertQuestions"))
                    {
                        //Go To Main Page With Id And UserName
                        DB.InserQuestions(question1.getText().toString(),question2.getText().toString(),Id);
                        T0_MainPage.putExtra("ID",Id);
                        T0_MainPage.putExtra("Username",username);
                        //Toast.makeText(getApplicationContext(),"Welcome ",Toast.LENGTH_LONG).show();
                        startActivity(T0_MainPage);


                    }
                }
            }
        });

    }
}
