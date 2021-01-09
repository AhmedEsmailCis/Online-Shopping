package com.example.ahmed_esmail;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //----------------------------------------------------------------------------------
        final EcommerceDatabase Db=new EcommerceDatabase(this);
        final EditText Username=(EditText)findViewById(R.id.username_editText);
        final EditText Password=(EditText)findViewById(R.id.password2_editText);
        final CheckBox cb=(CheckBox)findViewById(R.id.checkBox);
        //------------------------------------------------------------------------------------
        Cursor f=Db.selectToRemember();
        if(f!=null)
        {
            Username.setText(f.getString(f.getColumnIndex("Username")));
            Password.setText(f.getString(f.getColumnIndex("Password")));
            cb.setChecked(true);
        }

        //------------------------------------------------------------------------------------
        Button Login=(Button)findViewById(R.id.login_button);
        final Intent T0_MainPage=new Intent(LoginActivity.this,MainPageActivity.class);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Username.getText().toString()).equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Username is required.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Cursor cursor = Db.Login(Username.getText().toString(), Password.getText().toString(), getApplicationContext());
                    if (cursor != null) {
                        Db.deleteToRemember();
                        if (cb.isChecked()) {
                            Db.addToRemember(Username.getText().toString(), Password.getText().toString());
                        }
                        finish();
                        Db.DeleteAllShoppingCartContent(cursor.getString(cursor.getColumnIndex("CustID")));
                        T0_MainPage.putExtra("ID", cursor.getString(cursor.getColumnIndex("CustID")));
                        T0_MainPage.putExtra("Username", Username.getText().toString());
                        startActivity(T0_MainPage);
                        //Go to Main Page with id and Username
                        //Toast.makeText(getApplicationContext(),cursor.getString(cursor.getColumnIndex("CustID")),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //-----------------------------------------------------------------------------------
        Button Signup=(Button)findViewById(R.id.sign_up_button);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
        //------------------------------------------------------------------------------------
        TextView ForgotPassword=(TextView)findViewById(R.id.ForgotPassword_textView);
        final Intent i=new Intent(LoginActivity.this,QuestionActivity.class);
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Username.getText().toString()).equals(""))
                { Toast.makeText(getApplicationContext(),"Enter Your Username",Toast.LENGTH_LONG).show(); }
                else
                {
                    Cursor data=Db.GetCustomerId(Username.getText().toString());
                    if(data==null)
                    {
                        Toast.makeText(getApplicationContext(),"Username not Found ... Please enter the correct Username.",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        i.putExtra("ID",data.getString(data.getColumnIndex("CustID")));
                        i.putExtra("Username", Username.getText().toString());
                        i.putExtra("action", "ForgotPassword");
                        startActivity(i);
                    }
                }

            }
        });
        //------------------------------------------------------------------------------------

    }
}
