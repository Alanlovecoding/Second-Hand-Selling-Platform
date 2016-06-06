package cn.edu.pku.gofish.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import java.util.List;

import cn.edu.pku.gofish.ActivityChange;
import cn.edu.pku.gofish.FragmentMyCheck;
import cn.edu.pku.gofish.Model.Record;
import cn.edu.pku.gofish.R;

/**
 * Created by leonardo on 16/6/6.
 */
public class MyRecordAdapter extends RecyclerView.Adapter {
    private List<Record> RecordList;
    private Context context;
    public MyRecordAdapter(List<Record> _RecordList,Context context) {
        RecordList = _RecordList;
        this.context = context;
    }
    private FragmentManager fm;
    public void setMessageManager(FragmentManager fm)
    {
        this.fm = fm;
    }
    public void refresh(List<Record> _RecordList)
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
            public void onClick(View v){
                FragmentMyCheck myDialogFragment = new FragmentMyCheck();
                myDialogFragment.setInterface(new FragmentMyCheck.NoticeDialogListener() {
                    @Override
                    public void onDialogPositiveClick(int key) {
                        Toast.makeText(context, "修改", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, ActivityChange.class);
                        Bundle bundle=new Bundle();
                        Log.d("NET","RecordCardAdapter record ID "+record.getID());
                        bundle.putInt("id", record.getID());
                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //message.uploadFile(true);
                    }

                    @Override
                    public void onDialogNegativeClick(int key) {
                        Toast.makeText(context, "删除", Toast.LENGTH_LONG).show();
                        record.deleteFile();
                        //message.uploadFile(false);
                    }
                });
                myDialogFragment.show(fm, "dialog_fragment");

            }
        });
        Log.d("NET","RecordCardAdapter"+record.getDescribetext());
        holder.information.setText(record.getDescribetext());
        holder.gridView.setAdapter(new ImageAdapter(context,0));

    }

    @Override
    public int getItemCount() {
        return RecordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView information;
        public int position;
        public GridView gridView;
        public ImageView imageView;
        public LinearLayout briefview;
        public ViewHolder(View view)
        {
            super(view);
            information = (TextView) view.findViewById(R.id.textView);
            gridView=(GridView) view.findViewById(R.id.gridview);
            imageView=(ImageView) view.findViewById(R.id.ivPerson);
            briefview=(LinearLayout) view.findViewById(R.id.briefview);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
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