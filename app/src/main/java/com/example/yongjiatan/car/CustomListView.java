package com.example.yongjiatan.car;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomListView extends ArrayAdapter<String> {
    String[] rid;
    String[] parkingstructure;
    String[]time;
    String[]date;
    String[] UID;
    private Activity context;
    public CustomListView(Activity context,String[]rid,String[]parkingstructure,String[]time,String[]date,String[]UID){
        super(context,R.layout.listviewlayout,rid);

        this.context=context;
        this.rid=rid;
        this.parkingstructure=parkingstructure;
        this.time=time;
        this.date=date;
        this.UID=UID;
            }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View r = convertView;
       ViewHolder viewHolder=null;
       if(r==null)
       {
           LayoutInflater layoutInflater=context.getLayoutInflater();
           r=layoutInflater.inflate(R.layout.listviewlayout,null,true);
           viewHolder=new ViewHolder(r);
           r.setTag(viewHolder);
       }
       else {
           viewHolder=(ViewHolder)r.getTag();
       }
       viewHolder.rid.setText(rid[position]);
        viewHolder.parkingstructure.setText(parkingstructure[position]);
        viewHolder.time.setText(time[position]);
        viewHolder.date.setText(date[position]);
        viewHolder.UID.setText(UID[position]);
        return r;
    }
    class ViewHolder
    {
        TextView UID;
        TextView rid;
        TextView date;
        TextView time;
        TextView parkingstructure;
        ViewHolder(View v)
        {
            rid=(TextView) v.findViewById(R.id.t1);
            parkingstructure=(TextView) v.findViewById(R.id.t2);
           time=(TextView) v.findViewById(R.id.t3);
            date=(TextView) v.findViewById(R.id.t4);
           UID=(TextView) v.findViewById(R.id.t5);
        }
    }
}
