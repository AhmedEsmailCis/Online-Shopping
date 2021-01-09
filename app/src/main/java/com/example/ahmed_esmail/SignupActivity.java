package com.example.ahmed_esmail;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "sign_up";
    private DatePickerDialog.OnDateSetListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //---------------------------------------------------------
        final EcommerceDatabase DB=new EcommerceDatabase(this);
        final EditText id=(EditText)findViewById(R.id.id_editText);
        final EditText name=(EditText)findViewById(R.id.name_editText);
        final EditText username=(EditText)findViewById(R.id.username_editText);
        final EditText password=(EditText)findViewById(R.id.password2_editText);
        final TextView birthdate=(TextView)findViewById(R.id.birthdate_textView);
        final EditText job=(EditText)findViewById(R.id.job_editText);
        final RadioButton male=(RadioButton)findViewById(R.id.male_radioButton) ;
        final RadioButton female=(RadioButton)findViewById(R.id.female_radioButton) ;
        FloatingActionButton determine=(FloatingActionButton) findViewById(R.id.floatingActionButton3);
        Button regist =(Button)findViewById(R.id.registration_button);
        //-----------------------------------------------------------------------------------------
        determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(SignupActivity.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,listener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth + "-" + month + "-" + year;
                birthdate.setText(date);
            }
        };
        ///////////////////////////////////////////////////////////////////////////////////////////

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkSuccesfull=0;
                if((id.getText().toString()).equals(""))
                { Toast.makeText(getApplicationContext(),"Enter Your Id",Toast.LENGTH_LONG).show(); }
                //---------------------------------------------------------
                else if((name.getText().toString()).equals(""))
                {Toast.makeText(getApplicationContext(),"Enter Your Full Name",Toast.LENGTH_LONG).show();}
                //---------------------------------------------------------
                else if((username.getText().toString()).equals("")||(username.getText().toString()).length()<8)
                {Toast.makeText(getApplicationContext(),"Enter Your Username with length 8 digit",Toast.LENGTH_LONG).show();}
                //---------------------------------------------------------
                else if((password.getText().toString()).equals("")||(password.getText().toString()).length()<8)
                {
                    password.setText("");
                    Toast.makeText(getApplicationContext(),"Enter Your Password with length 8 digit",Toast.LENGTH_LONG).show();}
                //---------------------------------------------------------
                else if(!(male.isChecked())&&!(female.isChecked()))
                {
                    Toast.makeText(getApplicationContext(),"Choose Your gender ,please ..",Toast.LENGTH_LONG).show();}
                //---------------------------------------------------------
                else if((birthdate.getText().toString()).equals(""))
                {Toast.makeText(getApplicationContext(),"Enter Your Birthdate",Toast.LENGTH_LONG).show();}
                //---------------------------------------------------------
                else if((job.getText().toString()).equals(""))
                {Toast.makeText(getApplicationContext(),"Enter Your job",Toast.LENGTH_LONG).show();}
                //---------------------------------------------------------
                else
                {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String date = ((TextView)findViewById(R.id.birthdate_textView)).getText().toString();
                        java.util.Date parsedDate = dateFormat.parse(date);
                        checkSuccesfull++;
                    }
                    catch (java.text.ParseException e) {
                        Toast.makeText(getApplicationContext()," Choose your Birthdate ",Toast.LENGTH_LONG).show();
                    }

                }
                //---------------------------------------------------------
                if(checkSuccesfull==1)
                {
                    Cursor cursor=DB.CheckCustomerId(id.getText().toString());
                    if(cursor==null)
                    {
                        if(male.isChecked())
                        {
                            DB.Signup(id.getText().toString(),name.getText().toString(),username.getText().toString(),password.getText().toString(),"male",birthdate.getText().toString(),job.getText().toString());
                        }
                        if(female.isChecked())
                        {
                            DB.Signup(id.getText().toString(),name.getText().toString(),username.getText().toString(),password.getText().toString(),"female",birthdate.getText().toString(),job.getText().toString());
                        }
                        // go to new activity with name and id
                        //Toast.makeText(getApplicationContext(),"Congratulation ...added .",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(SignupActivity.this,QuestionActivity.class);
                        i.putExtra("ID",id.getText().toString());
                        i.putExtra("Username",username.getText().toString());
                        i.putExtra("action","InsertQuestions");
                        startActivity(i);
                    }
                    else
                    {
                        id.setText("");
                        Toast.makeText(getApplicationContext(),"your Id was Used Before ..please Login .",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }
}
