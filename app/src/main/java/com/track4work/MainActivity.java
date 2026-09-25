package com.track4work;

import android.app.*;
import android.os.*;
import android.graphics.Color;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    LinearLayout root, content;
    int blue = Color.rgb(23,54,93);
    String[] sections = {"Dashboard","Equipment","Maintenance","Vehicles & Trailers","Fuel","Inventory","Incident / Damage","Service Records","Documents"};
    Map<String, ArrayList<String>> records = new HashMap<>();

    public void onCreate(Bundle b){
        super.onCreate(b);
        for(String s:sections) records.put(s,new ArrayList<>());
        build();
    }
    TextView tv(String s,int size){
        TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(Color.DKGRAY); t.setPadding(18,14,18,14); return t;
    }
    Button btn(String s){
        Button b=new Button(this); b.setText(s); return b;
    }
    void build(){
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(Color.rgb(244,246,248));
        TextView head=tv("Track4Work\nBusiness operations & record tracking",21); head.setTextColor(Color.WHITE); head.setBackgroundColor(blue); head.setPadding(18,18,18,18);
        root.addView(head,new LinearLayout.LayoutParams(-1,75));
        HorizontalScrollView navScroll=new HorizontalScrollView(this);
        LinearLayout nav=new LinearLayout(this); nav.setOrientation(LinearLayout.HORIZONTAL); nav.setBackgroundColor(Color.WHITE);
        for(String s:sections){ Button b=btn(s); b.setOnClickListener(v->show(s)); nav.addView(b); }
        navScroll.addView(nav); root.addView(navScroll,new LinearLayout.LayoutParams(-1,65));
        ScrollView sv=new ScrollView(this); content=new LinearLayout(this); content.setOrientation(LinearLayout.VERTICAL); content.setPadding(12,12,12,24); sv.addView(content); root.addView(sv,new LinearLayout.LayoutParams(-1,0,1));
        setContentView(root); show("Dashboard");
    }
    void show(String section){
        content.removeAllViews();
        TextView title=tv(section,25); title.setTextColor(blue); content.addView(title);
        if(section.equals("Dashboard")){
            content.addView(tv("Simpson Outdoor Services",16));
            for(String s:sections) if(!s.equals("Dashboard")){
                TextView c=tv(s+"    "+records.get(s).size()+" records",17); c.setBackgroundColor(Color.WHITE); content.addView(c,new LinearLayout.LayoutParams(-1,65));
            }
            Button add=btn("Quick: Add Incident / Damage Report"); add.setOnClickListener(v->form("Incident / Damage")); content.addView(add);
        } else {
            Button add=btn("+ Add "+section); add.setOnClickListener(v->form(section)); content.addView(add);
            if(records.get(section).isEmpty()) content.addView(tv("No records yet.",15));
            else for(String r:records.get(section)) content.addView(tv(r,16));
        }
    }
    void form(String section){
        LinearLayout box=new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL); box.setPadding(30,10,30,10);
        EditText name=new EditText(this); name.setHint("Name / description"); box.addView(name);
        EditText notes=new EditText(this); notes.setHint("Notes"); box.addView(notes);
        new AlertDialog.Builder(this).setTitle("New "+section).setView(box)
          .setPositiveButton("Save",(d,w)->{String x=name.getText().toString(); if(x.length()==0)x="Record"; records.get(section).add(x+" — "+notes.getText().toString()); show(section);})
          .setNegativeButton("Cancel",null).show();
    }
}