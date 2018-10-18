package com.example.justi.uwipeersales;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.justi.uwipeersales.models.CartContract;
import com.example.justi.uwipeersales.models.DBHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String [] fields= {CartContract.CartEntry.ITEM, CartContract.CartEntry.TIME};

        String sortedOrder = CartContract.CartEntry.TIME+ " DESC";

        SQLiteOpenHelper helper =new DBHelper(this);
        final SQLiteDatabase db= helper.getReadableDatabase();

        Cursor res =db.query(CartContract.CartEntry.TABLE_NAME, fields, null,null,null,null,sortedOrder);
        ArrayList<String> itemList=new ArrayList();
        String[] items=getResources().getStringArray(R.array.items_available);

        while (res.moveToNext()){
            int itemId=res.getInt(res.getColumnIndex(CartContract.CartEntry.ITEM));

            itemList.add(items[itemId]);

        }
        ListView lv=(ListView)findViewById(R.id.cart_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, itemList);
        lv.setAdapter(adapter);
    }

}
