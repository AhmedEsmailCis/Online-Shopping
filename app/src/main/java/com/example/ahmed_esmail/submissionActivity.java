package com.example.ahmed_esmail;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class submissionActivity extends AppCompatActivity {

    private  static String CustId;
    private  static String Address;
    private static EcommerceDatabase DB;
    private DatePickerDialog.OnDateSetListener listener;
    private EditText Email_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        //------------------------------------------------------------------
        DB=new EcommerceDatabase(this);
        CustId=getIntent().getExtras().getString("ID");
        Address=getIntent().getExtras().getString("Address");
        //------------------------------------------------------------------
        final EditText address_txt=(EditText)findViewById(R.id.address_edittext) ;
        final EditText Date_txt=(EditText)findViewById(R.id.date_edittext) ;
        Email_txt=(EditText)findViewById(R.id.email_editText) ;

        FloatingActionButton map_btn=(FloatingActionButton)findViewById(R.id.address_btn) ;
        FloatingActionButton Date_btn=(FloatingActionButton)findViewById(R.id.date_btn) ;
        Button Submission=(Button)findViewById(R.id.sub_btn) ;
        Button Cancle=(Button)findViewById(R.id.cancle_btn) ;

        address_txt.setText(Address);
        //--------------------------------------------------------------------
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent T0_DetermineAddress=new Intent(submissionActivity.this,MapsActivity.class);
                T0_DetermineAddress.putExtra("ID",CustId);
                startActivity(T0_DetermineAddress);
            }
        });
        //-------------------------------------------------------------------
        Date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(submissionActivity.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,listener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth + "-" + month + "-" + year;
                Date_txt.setText(date);
            }
        };
        //-------------------------------------------------------------------
        Submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int DateIsOkay=0;
                int EmailIsOkay=0;
                if((address_txt.getText().toString()).equals(""))
                { Toast.makeText(getApplicationContext(),"Enter Your Address",Toast.LENGTH_LONG).show(); }
                //---------------------------------------------------------
                else if((Date_txt.getText().toString()).equals(""))
                {Toast.makeText(getApplicationContext(),"Enter Order Date that wants to arrive to you.",Toast.LENGTH_LONG).show();}
                //---------------------------------------------------------
                else
                {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String date = Date_txt.getText().toString();
                        java.util.Date parsedDate = dateFormat.parse(date);
                        DateIsOkay++;
                    }
                    catch (java.text.ParseException e) {
                        Toast.makeText(getApplicationContext()," Enter The Date Of Order As Example: 20-12-2018 ",Toast.LENGTH_LONG).show();
                    }

                }
                //---------------------------------------------------------------------------
                if(DateIsOkay==1)
                {
                    if((Email_txt.getText().toString()).equals(""))
                    { Toast.makeText(getApplicationContext(),"Enter Your Email",Toast.LENGTH_LONG).show(); }
                    //---------------------------------------------------------
                    else
                    {
                        Boolean EmailIsValid=android.util.Patterns.EMAIL_ADDRESS.matcher(Email_txt.getText().toString()).matches();
                        if(EmailIsValid==true)
                            EmailIsOkay++;
                        else
                            Toast.makeText(getApplicationContext(),"invalid E-mail ",Toast.LENGTH_LONG).show();

                    }
                }
                //----------------------------------------------------------------------------
                if(EmailIsOkay==1)
                {
                    Cursor Ids=DB.getOrdersIds();
                    int OrderId;
                    if(Ids==null)
                        OrderId=1;
                    else
                    {
                        Ids.moveToLast();
                        OrderId=Ids.getInt(Ids.getColumnIndex("OrdID"))+1;
                    }
                    DB.InsertInOrders(OrderId,Date_txt.getText().toString(),address_txt.getText().toString(),Email_txt.getText().toString(),CustId);
                    Cursor Products=DB.GetProductOfShoppingCart(CustId);
                    while (!Products.isAfterLast())
                    {
                        DB.InsertInOrdersDetails(OrderId,Products.getInt(Products.getColumnIndex("ProductId")),Products.getInt(Products.getColumnIndex("Quantity")));
                        //product id =Products.getInt(Products.getColumnIndex("ProductId"));
                        //get quantity from product by product id
                        Cursor OldQuantity=DB.GetAvaliableQuantityOfProduct(Products.getInt(Products.getColumnIndex("ProductId")));
                        int NewQuantity=OldQuantity.getInt(OldQuantity.getColumnIndex("Quantity"))-Products.getInt(Products.getColumnIndex("Quantity"));
                        //update quantity in product by product id
                        DB.UpdateQuantityOfProduct(Products.getInt(Products.getColumnIndex("ProductId")),NewQuantity);
                        Products.moveToNext();
                    }
                    DB.DeleteAllShoppingCartContent(CustId);
                    SendEmail("Submitted");
                    Toast.makeText(getApplicationContext(),"Check your Email Now to know the details.", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent T0_MainPage=new Intent(submissionActivity.this,MainPageActivity.class);
                    T0_MainPage.putExtra("ID",CustId);
                    startActivity(T0_MainPage);
                }
                //----------------------------------------------------------------------------
            }
        });
        //-------------------------------------------------------------------
        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                DB.DeleteAllShoppingCartContent(CustId);
                Intent T0_MainPage=new Intent(submissionActivity.this,MainPageActivity.class);
                T0_MainPage.putExtra("ID",CustId);
                startActivity(T0_MainPage);

            }
        });
        //-------------------------------------------------------------------

    }
    protected void SendEmail(String Messege)
    {
       /* Intent EmailIntent=new Intent(Intent.ACTION_SEND);
        EmailIntent.setType("plain/txt");
        EmailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{Email_txt.getText().toString()});
        EmailIntent.putExtra(Intent.EXTRA_SUBJECT,"Order Details");
        EmailIntent.putExtra(Intent.EXTRA_TEXT,Messege);
        startActivity(Intent.createChooser(EmailIntent,"Send mail..."));*/

    }
}
