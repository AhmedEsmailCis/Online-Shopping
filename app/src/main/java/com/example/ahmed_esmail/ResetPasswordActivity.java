package com.example.ahmed_esmail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        final String Id=getIntent().getExtras().getString("ID");
        final String username=getIntent().getExtras().getString("Username");
        TextView SetUsername=(TextView)findViewById(R.id.textView) ;
        SetUsername.setText(" Username : "+username);
        //---------------------------------------------------------------
        final EcommerceDatabase DB=new EcommerceDatabase(this);
        final EditText P1=(EditText)findViewById(R.id.password1_editText);
        final EditText P2=(EditText)findViewById(R.id.password2_editText);
        Button Save=(Button)findViewById(R.id.resetpassword_button);
        final Intent To_LoginAgain=new Intent(ResetPasswordActivity.this,LoginActivity.class);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((P1.getText().toString()).equals("")||(P1.getText().toString()).length()<8)
                { Toast.makeText(getApplicationContext(),"Enter Your Password with 8 digit .",Toast.LENGTH_LONG).show(); }
                //---------------------------------------------------------
                else if((P2.getText().toString()).equals(""))
                {Toast.makeText(getApplicationContext(),"Enter Your Confirm Password",Toast.LENGTH_LONG).show();}
                //---------------------------------------------------------
                else if(!(P1.getText().toString().equals(P2.getText().toString())))
                {Toast.makeText(getApplicationContext(),"Confirm password Not match",Toast.LENGTH_LONG).show();}
                //---------------------------------------------------------
                else
                {
                    finish();
                    DB.UpdatePassword(Id,P1.getText().toString());
                    Toast.makeText(getApplicationContext(),"Your password is Reset.. Please login .",Toast.LENGTH_LONG).show();
                    startActivity(To_LoginAgain);
                }
            }
        });

    }
}
