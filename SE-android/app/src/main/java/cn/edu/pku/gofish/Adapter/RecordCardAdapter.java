package cn.edu.pku.gofish.Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.pku.gofish.Activity_record;
import cn.edu.pku.gofish.Model.Record;
import cn.edu.pku.gofish.R;

//this class is used for management of the homepage records. Users are not able to modify or delete it.
public class RecordCardAdapter extends RecyclerView.Adapter {
    private List<Record> RecordList;
    private Context context;
    public RecordCardAdapter(List<Record> _RecordList,Context context) {
        RecordList = _RecordList;
        this.context = context;
    }

    public void refresh(List<Record> _RecordList)      //refresh the list
    {
        RecordList = _RecordList;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("ViewHolder", "onCreateViewHolder, i: " + i);
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_item, viewGroup, false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Log.d("ViewHolder", "onBindViewHolder, i: " + i + ", viewHolder: " + viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.position = i;
        final Record record = RecordList.get(i);
        holder.briefview.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){     //click the record for more information
                Intent intent = new Intent(context, Activity_record.class);
                Bundle bundle=new Bundle();
                Log.d("NET","RecordCardAdapter record ID "+record.getID());
                bundle.putInt("id",Integer.parseInt(record.getID()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        Log.d("NET","RecordCardAdapter"+record.getDescribetext());
        holder.information.setText("物品简介:"+record.getDescribetext());       //get the record's information
        holder.usrname.setText("用户名:"+record.getName());
        holder.price.setText("物品价格:"+record.getPrice());
        holder.number.setText("物品数量:"+record.getNumber());

    }

    @Override
    public int getItemCount() {
        return RecordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView information;
        public int position;
        public LinearLayout briefview;
        public TextView usrname;
        public TextView price;
        public TextView number;
        public ViewHolder(View view)
        {
            super(view);
            information = (TextView) view.findViewById(R.id.itemdescribe);
            briefview=(LinearLayout) view.findViewById(R.id.briefview);
            usrname = (TextView) view.findViewById(R.id.itemusrname);
            price=(TextView) view.findViewById(R.id.itemprice);
            number=(TextView) view.findViewById(R.id.itemdnumber);
        }

    }

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private int num;
        public ImageAdapter(Context context,int num) {
            this.mContext=context;
            this.num = num;
        }

        @Override
        public int getCount() {
            return num;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //定义一个ImageView,显示在GridView里
            ImageView imageView;
            if(convertView==null){
                imageView=new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }else{
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }



    }

    private Integer[] mThumbIds = {
            R.drawable.icon_photo, R.drawable.icon_photo,
            R.drawable.icon_photo

    };

}