package com.example.justi.uwipeersales;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.ContentObservable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justi.uwipeersales.models.CartContract;
import com.example.justi.uwipeersales.models.DBHelper;

public class ItemDetailActivity extends AppCompatActivity {
    private int item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey("itemid")){
            int itemid=bundle.getInt("itemid");
            this.item=itemid;
            int defaultVal=0;
            String [] itemList= getResources().getStringArray(R.array.items_available);
            String [] itemDescriptions= getResources().getStringArray(R.array.items_description);
            String itemName=itemList[itemid];
            String itemDescription=itemDescriptions[itemid];
            TypedArray itemImages=getResources().obtainTypedArray(R.array.items_images);

            TextView textView=findViewById(R.id.header);
            textView.setText(itemName);

            TextView textDescription=findViewById(R.id.description);
            textDescription.setText(itemDescription);

            ImageView imageView=findViewById(R.id.main_img);
            imageView.setImageResource(itemImages.getResourceId(itemid, defaultVal));
        }
    }
    public void addToCart(final View view){
        int item =this.item;

        SQLiteOpenHelper helper =new DBHelper(this);

        final SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CartContract.CartEntry.ITEM,item);

        final long cardID = db.insert(CartContract.CartEntry.TABLE_NAME, null, cv);
        if(cardID != -1){
            Snackbar.make(view, "Item Successfully added to the Cart", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sql= "DELETE FROM " + CartContract.CartEntry.TABLE_NAME+ " WHERE "+ CartContract.CartEntry._ID+" = "+cardID+";";
                    db.execSQL(sql);
                    Snackbar.make(view, "Removed Item from Cart", Snackbar.LENGTH_LONG).show();
                }
            }).show();
        }

    }
}
