package com.example.ahmed_esmail;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingCartActivity extends AppCompatActivity {

    ArrayAdapter<String> myAdpter;
    EcommerceDatabase DB;
    TextView TotalCost;
    String CustId;
    Cursor cursor;
    int ProductId;
    TextView Dtails;
    Button Edit_btn;
    Button Remove_btn;
    EditText NewQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        //---------------------------------------------------------------------------------------
        DB=new EcommerceDatabase(this);
        CustId=getIntent().getExtras().getString("ID");
        //---------------------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.accounting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cursor.getCount()==0)
                {
                    Toast.makeText(getApplicationContext(),"Your Shopping Cart is Empty..",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent T0_Submission=new Intent(ShoppingCartActivity.this,submissionActivity.class);
                    T0_Submission.putExtra("ID",CustId);
                    T0_Submission.putExtra("Address","");
                    startActivity(T0_Submission);

                }

            }
        });
        //-----------------------------------------------------------------------------
        //invisible
         Dtails=(TextView)findViewById(R.id.textView6);
         Edit_btn=(Button)findViewById(R.id.button2);
         Remove_btn=(Button)findViewById(R.id.button3);
         NewQuantity=(EditText)findViewById(R.id.editText2);
        //------------------------------------------------------------------------------
        TotalCost=(TextView)findViewById(R.id.textView7);
        TextView Topic=(TextView)findViewById(R.id.textView5);
        Topic.setText("     Your Shopping Cart Content...");
        //------------------------------------------------------------------------------
        ListView mylist=(ListView)findViewById(R.id.mylist);
        myAdpter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1);
        mylist.setAdapter(myAdpter);
        SetAdapter();
        //-------------------------------------------------------------------------------

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {
                cursor.moveToFirst();
                cursor.move(postion);
                ProductId=cursor.getInt(cursor.getColumnIndex("ProductId"));
                Dtails.setVisibility(View.VISIBLE);
                Edit_btn.setVisibility(View.VISIBLE);
                Remove_btn.setVisibility(View.VISIBLE);
                NewQuantity.setVisibility(View.VISIBLE);
                Dtails.setText("Product Name : "+cursor.getString(cursor.getColumnIndex("ProductName"))+"\nQuantity : "+String.valueOf(cursor.getInt(cursor.getColumnIndex("Quantity")))+"\nPrice : "+String.valueOf(cursor.getInt(cursor.getColumnIndex("Price"))));

            }
        });
        Remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.DeleteProductFromShoppingCart(ProductId,CustId);
                SetAdapter();
            }
        });
        Edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor AQuantity=DB.GetAvaliableQuantityOfProduct(ProductId);
                if((NewQuantity.getText().toString()).equals(""))
                { Toast.makeText(getApplicationContext(),"Enter the Quantity",Toast.LENGTH_LONG).show(); }
                //---------------------------------------------------------
                else if(Integer.parseInt(NewQuantity.getText().toString())> AQuantity.getInt(AQuantity.getColumnIndex("Quantity")))
                {
                    NewQuantity.setText("");
                    Toast.makeText(getApplicationContext(),"The Avaliable Quantity only "+String.valueOf(AQuantity.getInt(AQuantity.getColumnIndex("Quantity"))),Toast.LENGTH_LONG).show();
                }
                //---------------------------------------------------------
                else
                {

                    DB.UpdateQuantityOfProductInShoppingCart(ProductId,CustId,Integer.parseInt(NewQuantity.getText().toString()));
                    NewQuantity.setText("");
                    SetAdapter();
                }
            }
        });


    }
    protected void SetAdapter()
    {
        myAdpter.clear();
        Dtails.setVisibility(View.INVISIBLE);
        Edit_btn.setVisibility(View.INVISIBLE);
        Remove_btn.setVisibility(View.INVISIBLE);
        NewQuantity.setVisibility(View.INVISIBLE);
        cursor=DB.GetProductOfShoppingCart(CustId);
        int x=0;
        while (! cursor.isAfterLast())
        {
            myAdpter.add("  "+cursor.getString(cursor.getColumnIndex("ProductName")));
            x+=((cursor.getInt(cursor.getColumnIndex("Price")))*(cursor.getInt(cursor.getColumnIndex("Quantity"))));
            cursor.moveToNext();
        }
        TotalCost.setText("The Total Cost : "+String.valueOf(x)+" pounds");
    }
}
