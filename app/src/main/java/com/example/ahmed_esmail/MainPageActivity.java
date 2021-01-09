package com.example.ahmed_esmail;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
//import android.support.v4.view.PagerAdapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainPageActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private  static String CustId;
    private static EcommerceDatabase DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        //------------------------------------------------------------------
        DB=new EcommerceDatabase(this);
        CustId=getIntent().getExtras().getString("ID");
        //------------------------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.cart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainPageActivity.this,ShoppingCartActivity.class);
                i.putExtra("ID",CustId);
                startActivity(i);
            }
        });

    }
    //--------------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_searching) {
            Intent T0_Searching=new Intent(MainPageActivity.this,SearchingActivity.class);
            T0_Searching.putExtra("ID",CustId);
            startActivity(T0_Searching);
            return true;
        }
        if (id == R.id.action_Logout) {

            DB.DeleteAllShoppingCartContent(CustId);
            finish();
            Intent T0_Searching=new Intent(MainPageActivity.this,LoginActivity.class);
            startActivity(T0_Searching);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //--------------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------------
    public static class PlaceholderFragment extends Fragment {

        private static final String CategoryId = "Category_number";
        private   int ProductId =0;
        private   int ProductPrice =0;
        private   int QuantityOfProduct =0;
        private   String ProductName ="name";

        public PlaceholderFragment() { }
        public static PlaceholderFragment newInstance(int Catid) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(CategoryId, Catid);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_page, container, false);

            //----------------------------------------------------------------
            final Button Buy_btn=(Button) rootView.findViewById(R.id.button);
            final EditText Quantity=(EditText) rootView.findViewById(R.id.editText);
            final TextView Details=(TextView)rootView.findViewById(R.id.textView4);
            Buy_btn.setVisibility(View.INVISIBLE);
            Quantity.setVisibility(View.INVISIBLE);
            Details.setVisibility(View.INVISIBLE);
            //----------------------------------------------------------------
            int Catid=getArguments().getInt(CategoryId);
            ListView mylist=(ListView) rootView.findViewById(R.id.myList);
            ArrayAdapter<String> myAdpter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
            mylist.setAdapter(myAdpter);
            //-----------------------------------------------------------------
            final Cursor cursor=DB.GetProductOfCategory(Catid);
            while (! cursor.isAfterLast())
            {
                myAdpter.add(cursor.getString(cursor.getColumnIndex("ProName")));
                cursor.moveToNext();
            }

           mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {

                    cursor.moveToFirst();
                    cursor.move(postion);
                    ProductId=cursor.getInt(cursor.getColumnIndex("ProID"));
                    Cursor c=DB.CheckProductIdThatInCart(ProductId,CustId);
                    if(c==null)
                    {
                        Buy_btn.setVisibility(View.VISIBLE);
                        Quantity.setVisibility(View.VISIBLE);
                        Details.setVisibility(View.VISIBLE);
                        ProductName=cursor.getString(cursor.getColumnIndex("ProName"));
                        ProductPrice=cursor.getInt(cursor.getColumnIndex("Price"));
                        QuantityOfProduct=cursor.getInt(cursor.getColumnIndex("Quantity"));
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
            return rootView;
        }
    }
    //--------------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------------
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {

            return DB.GetCategoryCount();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            return DB.GetCategoryName(position+1);
        }
    }
}
