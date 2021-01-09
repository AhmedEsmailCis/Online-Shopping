package com.example.ahmed_esmail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class EcommerceDatabase extends SQLiteOpenHelper {
    private static String DBname="EcommerceDatabase";
    SQLiteDatabase EcommerceDatabase;
    public   EcommerceDatabase(Context context){
        super(context,DBname,null,1); }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Customers(CustID text primary key,CustName text not null,Username text not null ,Password text not null,Gender text not null,Birthdate text not null,Job text not null)");
        db.execSQL("create table Questions(QuesId integer  primary key autoincrement,Question1 text not null,Question2 text not null,CustID text not null,FOREIGN key (CustID) references Customers(CustID))");
        db.execSQL("create table Categories(CatID integer primary key,CatName text not null)");
        db.execSQL("create table Orders(OrdID integer primary key ,OrdDate text not null,Address text not null,Email text not null,CustID text not null,FOREIGN key (CustID) references Customers(CustID))");
        db.execSQL("create table Products(ProID integer primary key,ProName text not null,Price integer not null,Quantity integer not null,CatID integer not null,FOREIGN key (CatID) references Categories(CatID))");
        db.execSQL("create table OrderDetails(OrdID integer,ProID integer,Quantity integer not null,primary key(OrdID,ProID),FOREIGN key (OrdID) references Orders(OrdID),FOREIGN key (ProID) references Products(ProID))");
        db.execSQL("create table ShoppingCart(ProductId integer primary key,CustomerId text not null,ProductName text not null,Price integer not null,Quantity integer not null)");
        db.execSQL("create table Remember(Username text primary key ,Password text not null)");

        db.execSQL("insert into Customers(CustID ,CustName ,Username ,Password ,Gender,Birthdate,Job) values ('12345','ahmed esmail','Ahmedacm','12345','male','11-02-1997','developer');");

        //--------------------------------------------------------------------------------------------------------------------------------------------
        db.execSQL("insert into Categories (CatID,CatName) values (1,'Mobiles');");
        db.execSQL("insert into Categories (CatID,CatName) values (2,'Tablets');");
        db.execSQL("insert into Categories (CatID,CatName) values (3,'Tvs');");
        db.execSQL("insert into Categories (CatID,CatName) values (4,'beauty');");
        db.execSQL("insert into Categories (CatID,CatName) values (5,'toys');");
        //--------------------------------------------------------------------------------------------------------------------------------------------
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (1,'Apple iPhone 8 with FaceTime',14000,12,1);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (2,'Samsung Galaxy J4 2018',2700,7,1);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (3,'Xiaomi Redmi Note 5 ',4400,5,1);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (4,'Huawei Y6 Prime 2018 Dual SIM',2499,20,1);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (5,'Sony Xperia XZ1 Dual Sim',5999,10,1);");
        //--------------------------------------------------------------------------------------------------------------------------------------------

        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (6,'Apple iPad Mini 4 with Facetime',4000,7,2);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (7,'Huawei MediaPad T3 Tablet - 10 Inch',3500,4,2);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (8,'Alcatel Pixi 4 (7) Tablet - 7 Inch',1477,9,2);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (9,'Samsung Galaxy Tab A',2460,9,2);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (10,'Apple iPad - March 2017 - 9.7 Inch',8999,6,2);");
        //--------------------------------------------------------------------------------------------------------------------------------------------

        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (11,'Pantene Pro-V Colored',50,100,4);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (12,'Pert Plus shampoo',34,100,4);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (13,'Head & Shoulders',10,100,4);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (14,'EVA Cream with Glycerin',9,100,4);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (15,'NIVEA MEN Creme',43,100,4);");
        //--------------------------------------------------------------------------------------------------------------------------------------------

        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (16,'Toshiba 32 inch',3295,11,3);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (17,'Tornado 32 inch',3150,20,3);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (18,'Samsung 32 inch',4900,3,3);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (19,'JAC 48 inch',4300,6,3);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (20,'JAC 39AS 39 inch',8000,33,3);");
        //--------------------------------------------------------------------------------------------------------------------------------------------

        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (21,'Bingo Nelly and Sherihan Fashionista Doll Purple Silver',99,4,5);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (22,'Hamely 261784 Baby Ellie',420,14,5);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (23,'Nilco Good Luck Bank',30,2,5);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (24,'Bburag Set of 3 Metal Cars',175,5,5);");
        db.execSQL("insert into Products(ProID ,ProName ,Price ,Quantity ,CatID) values (25,'Calebou 3D Puzzle Eiffel Tower',99,6,5);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Questions");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists OrderDetails");
        db.execSQL("drop table if exists ShoppingCart");
        db.execSQL("drop table if exists Remember");

        onCreate(db);
    }
    //----------------------------------------------------------------------------------------
    public void addToRemember(String Username , String password) {
        EcommerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("Username",Username);
        row.put("Password",password);
        EcommerceDatabase.insert("Remember",null,row);
        EcommerceDatabase.close();
    }
    public void deleteToRemember() {
        EcommerceDatabase=getWritableDatabase();
        EcommerceDatabase.delete("Remember",null,null);
        EcommerceDatabase.close();
    }
    public Cursor selectToRemember () {
        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select * from Remember",null);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        if(cursor.getCount()==0)
            return null;
        else
            return cursor;
    }
    //----------------------------------------------------------------------------------------
    public void Signup(String id , String name ,String Username , String password,String gender , String birthdate,String job) {
        EcommerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("CustID",id);
        row.put("CustName",name);
        row.put("Username",Username);
        row.put("Password",password);
        row.put("Gender",gender);
        row.put("Birthdate",birthdate);
        row.put("Job",job);
        EcommerceDatabase.insert("Customers",null,row);
        EcommerceDatabase.close();
    }
    public Cursor CheckCustomerId (String id) {
        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select Job from Customers where CustID like "+id,null);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        if(cursor.getCount()==0)
            return null;
        else
            return cursor;

    }
    //------------------------------------------------------------------------------------------------------------------------------
    public Cursor Login(String UN , String PW ,Context context) {
        String[] arr={UN};
        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select CustID , Password from Customers where Username like ? ",arr);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        if(cursor.getCount()==0)
        {

            Toast.makeText(context,"Username not Found ... Please enter the correct Username.",Toast.LENGTH_LONG).show();
            return null;

        }
        String RightPassword =cursor.getString(cursor.getColumnIndex("Password"));
        if(RightPassword.equals(PW))
            return cursor;
        else
        {
            Toast.makeText(context,"password not match with your username ...",Toast.LENGTH_LONG).show();
            return null;
        }
    }
    //--------------------------------------------------------------------------------------------------
    public Cursor GetCustomerId (String UN) {
        String[] arr={UN};
        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select CustID from Customers where Username like ? ",arr);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        if(cursor.getCount()==0)
            return null;
        else
            return cursor;
    }
    //--------------------------------------------------------------------------------------------------
    public void InserQuestions( String Q1 ,String Q2 ,String Cust_id) {
        EcommerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("Question1",Q1);
        row.put("Question2",Q2);
        row.put("CustID",Cust_id);
        EcommerceDatabase.insert("Questions",null,row);
        EcommerceDatabase.close();
    }
    //--------------------------------------------------------------------------------------------------
    public Cursor ChechRigthQuestions (String Q1 ,String Q2,String Id) {
        String[] arr={Id};
        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select Question1 , Question2 from Questions where CustID like ? ",arr);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        if(cursor.getString(cursor.getColumnIndex("Question1")).equals(Q1)&&cursor.getString(cursor.getColumnIndex("Question2")).equals(Q2))
            return null;
        else
            return cursor;
    }
    //--------------------------------------------------------------------------------------------------
    public void UpdatePassword( String id ,String newPassword) {
        EcommerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("Password",newPassword);
        EcommerceDatabase.update("Customers",row,"CustID like ?",new String[]{id});
        EcommerceDatabase.close();
    }
    //--------------------------------------------------------------------------------------------------
    public int GetCategoryCount () {
        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select * from Categories ",null);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        return cursor.getCount();
    }
    //--------------------------------------------------------------------------------------------------
    public String GetCategoryName (int CiD) {

        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select CatName from Categories where CatID = "+String.valueOf(CiD),null);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        return cursor.getString(cursor.getColumnIndex("CatName"));
    }
    //--------------------------------------------------------------------------------------------------
    public Cursor GetProductOfCategory (int CiD) {

        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select * from Products where CatID = "+String.valueOf(CiD),null);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        return cursor;
    }
    //------------------------------------------------------------------------------------------------------
     public void PutInCart(int proId , String custId ,String proName , int price,int  quantity) {
        EcommerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("ProductId",proId);
        row.put("CustomerId",custId);
        row.put("ProductName",proName);
        row.put("Price",price);
        row.put("Quantity",quantity);
        EcommerceDatabase.insert("ShoppingCart",null,row);
        EcommerceDatabase.close();
    }
    public Cursor CheckProductIdThatInCart (int ProId,String Custid) {
        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select Quantity from ShoppingCart where ProductId like ? and CustomerId like ?",new String[]{String.valueOf(ProId),Custid});
        cursor.moveToFirst();
        EcommerceDatabase.close();
        if(cursor.getCount()==0)
            return null;
        else
            return cursor;

    }
    //--------------------------------------------------------------------------------------------------------------
    public Cursor GetProductOfShoppingCart (String CustId) {

        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select * from ShoppingCart where CustomerId like "+CustId,null);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        return cursor;
    }
    //--------------------------------------------------------------------------------------------------
    public void DeleteProductFromShoppingCart( int ProId,String Custid) {
        EcommerceDatabase=getWritableDatabase();
        EcommerceDatabase.delete("ShoppingCart","ProductId like ? and CustomerId like ?",new String[]{String.valueOf(ProId),Custid});
        EcommerceDatabase.close();
    }
    //--------------------------------------------------------------------------------------------------
    public Cursor GetAvaliableQuantityOfProduct(int ProId) {

        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select Quantity from Products where ProID = "+String.valueOf(ProId),null);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        return cursor;
    }
    //--------------------------------------------------------------------------------------------------
    public void UpdateQuantityOfProduct( int ProId ,int Quantity) {
        EcommerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("Quantity",Quantity);
        EcommerceDatabase.update("Products",row,"ProID like ? ",new String[]{String.valueOf(ProId)});
        EcommerceDatabase.close();
    }
    //--------------------------------------------------------------------------------------------------
    public void UpdateQuantityOfProductInShoppingCart( int ProId ,String CustId,int Quantity) {
        EcommerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("Quantity",Quantity);
        EcommerceDatabase.update("ShoppingCart",row,"ProductId like ? and CustomerId like ?",new String[]{String.valueOf(ProId),CustId});
        EcommerceDatabase.close();
    }
    //---------------------------------------------------------------------------------------------------
    public Cursor getProducts (String ProductName)
    {

        EcommerceDatabase=getReadableDatabase();
        String[] arr={'%'+ProductName+'%'};
        Cursor cursor=EcommerceDatabase.rawQuery("select * from Products where ProName like ?",arr);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        if(cursor.getCount()==0)
            return null;
        else
            return cursor;
    }
    //------------------------------------------------------------------------------------------------------
    public void DeleteAllShoppingCartContent(String Custid) {
        EcommerceDatabase=getWritableDatabase();
        EcommerceDatabase.delete("ShoppingCart","CustomerId like ?",new String[]{Custid});
        EcommerceDatabase.close();
    }
    //-------------------------------------------------------------------------------------------------------
    public void InsertInOrders(int OrderId , String Date ,String Address , String Email,String  CustId)
    {
        EcommerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("OrdID",OrderId);
        row.put("OrdDate",Date);
        row.put("Address",Address);
        row.put("Email",Email);
        row.put("CustID",CustId);
        EcommerceDatabase.insert("Orders",null,row);
        EcommerceDatabase.close();
    }
    //---------------------------------------------------------------------------------------------------
    public Cursor getOrdersIds ()
    {

        EcommerceDatabase=getReadableDatabase();
        Cursor cursor=EcommerceDatabase.rawQuery("select OrdID from Orders ",null);
        cursor.moveToFirst();
        EcommerceDatabase.close();
        if(cursor.getCount()==0)
            return null;
        else
            return cursor;
    }
    //---------------------------------------------------------------------------------------------------------
    public void InsertInOrdersDetails(int OrderId , int ProductId ,int Quantity)
    {
        EcommerceDatabase=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("OrdID",OrderId);
        row.put("ProID",ProductId);
        row.put("Quantity",Quantity);
        EcommerceDatabase.insert("OrderDetails",null,row);
        EcommerceDatabase.close();
    }
    //----------------------------------------------------------------------------------------------------------
}



