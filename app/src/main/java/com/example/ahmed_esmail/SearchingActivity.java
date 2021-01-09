package com.example.ahmed_esmail;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchingActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private  static String CustId;
    private static EcommerceDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        //------------------------------------------------------------------
        DB=new EcommerceDatabase(this);
        CustId=getIntent().getExtras().getString("ID");
        //------------------------------------------------------------------
        //----------------------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //--------------------------------------------------------------
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.setupWithViewPager(mViewPager);

        //-----------------------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.cart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchingActivity.this,ShoppingCartActivity.class);
                i.putExtra("ID",CustId);
                startActivity(i);
            }
        });

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class SearchingFragment extends Fragment {

        //----------------
        private EditText Search;
        int voiceCode=1;
        //----------------
        private Cursor c;
        private   int ProductId =0;
        private   int ProductPrice =0;
        private   int QuantityOfProduct =0;
        private   String ProductName ="name";
        public SearchingFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_searching, container, false);
            //----------------------------------------------------------------
            final Button Buy_btn=(Button) rootView.findViewById(R.id.button5);
            final EditText Quantity=(EditText) rootView.findViewById(R.id.editText4);
            final TextView Details=(TextView)rootView.findViewById(R.id.textView8);
            Buy_btn.setVisibility(View.INVISIBLE);
            Quantity.setVisibility(View.INVISIBLE);
            Details.setVisibility(View.INVISIBLE);
            //----------------------------------------------------------------
            ImageButton Voice_btn=(ImageButton)rootView.findViewById(R.id.imageButton);
            Search=(EditText) rootView.findViewById(R.id.editText3);
            Button Search_btn=(Button) rootView.findViewById(R.id.button4);
            ListView mylist=(ListView) rootView.findViewById(R.id.SearchingResult);
            final ArrayAdapter<String> myAdpter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
            mylist.setAdapter(myAdpter);
            //-----------------------------------------------------------------
            Voice_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Search.setText("");
                    myAdpter.clear();
                    Buy_btn.setVisibility(View.INVISIBLE);
                    Quantity.setVisibility(View.INVISIBLE);
                    Details.setVisibility(View.INVISIBLE);
                    Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    startActivityForResult(i,voiceCode);
                }
            });
            //-----------------------------------------------------------------
            Search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myAdpter.clear();
                    Buy_btn.setVisibility(View.INVISIBLE);
                    Quantity.setVisibility(View.INVISIBLE);
                    Details.setVisibility(View.INVISIBLE);
                    c =DB.getProducts((Search.getText().toString()).toLowerCase());
                    if(c==null)
                    {
                        Search.setText("");
                        Toast.makeText(getActivity(),"There is no product with this name",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        while (! c.isAfterLast())
                        {
                            myAdpter.add(c.getString(c.getColumnIndex("ProName")));
                            c.moveToNext();
                        }
                    }

                }
            });
            //------------------------------------------------------------------------------------------------------------------
            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {

                    c.moveToFirst();
                    c.move(postion);
                    ProductId=c.getInt(c.getColumnIndex("ProID"));
                    Cursor cr=DB.CheckProductIdThatInCart(ProductId,CustId);
                    if(cr==null)
                    {
                        Buy_btn.setVisibility(View.VISIBLE);
                        Quantity.setVisibility(View.VISIBLE);
                        Details.setVisibility(View.VISIBLE);
                        ProductName=c.getString(c.getColumnIndex("ProName"));
                        ProductPrice=c.getInt(c.getColumnIndex("Price"));
                        QuantityOfProduct=c.getInt(c.getColumnIndex("Quantity"));
                        Details.setText("Product Name : "+ProductName+"\nAvaliable Quantity : "+String.valueOf(QuantityOfProduct)+"\nPrice : "+String.valueOf(ProductPrice));

                    }
                    else
                    {
                        Toast.makeText(getActivity(),"You added this product in your shopping cart ..check it now .",Toast.LENGTH_LONG).show();
                        Buy_btn.setVisibility(View.INVISIBLE);
                        Quantity.setVisibility(View.INVISIBLE);
                        Details.setVisibility(View.INVISIBLE);
                    }

                }
            });
            //-------------------------------------------------------------------------------------------------------
            Buy_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if((Quantity.getText().toString()).equals(""))
                    { Toast.makeText(getActivity(),"Enter the Quantity",Toast.LENGTH_LONG).show(); }
                    //---------------------------------------------------------
                    else if(Integer.parseInt(Quantity.getText().toString())> QuantityOfProduct)
                    {
                        Quantity.setText("");
                        Toast.makeText(getActivity(),"The Avaliable Quantity only "+String.valueOf(QuantityOfProduct),Toast.LENGTH_LONG).show();
                    }
                    //---------------------------------------------------------
                    else
                    {

                        DB.PutInCart(ProductId,CustId,ProductName,ProductPrice,Integer.parseInt(Quantity.getText().toString()));
                        Snackbar.make(view, "The Product added in your Shopping Cart.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Quantity.setText("");
                        Buy_btn.setVisibility(View.INVISIBLE);
                        Quantity.setVisibility(View.INVISIBLE);
                        Details.setVisibility(View.INVISIBLE);
                    }
                }
            });
            //----------------------------------------------------------------------------

            return rootView;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode==voiceCode&&resultCode==getActivity().RESULT_OK)
            {
                ArrayList<String>result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Search.setText(result.get(0));
            }
            else
            {
                Toast.makeText(getActivity(),"Try Again..",Toast.LENGTH_SHORT).show();
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class ScanningFragment extends Fragment {
        int x=1;
        public ScanningFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_scanning, container, false);
            FloatingActionButton fab1 = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i,x);

                }
            });
            return rootView;
        }
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:return new SearchingFragment();
                case 1:return new ScanningFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0:return "Search";
                case 1:return "Scan Barcode";
            }
            return super.getPageTitle(position);
        }
    }
}
