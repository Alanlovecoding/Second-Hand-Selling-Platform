package cn.edu.pku.gofish.Adapter;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.pku.gofish.Model.Record;
import cn.edu.pku.gofish.R;


public class RecordCardAdapter extends RecyclerView.Adapter {
    private List<Record> RecordList;
    public RecordCardAdapter(List<Record> _RecordList) {
        RecordList = _RecordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("ViewHolder", "onCreateViewHolder, i: " + i);
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_item, viewGroup, false);
        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);*/
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Log.d("ViewHolder", "onBindViewHolder, i: " + i + ", viewHolder: " + viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.position = i;
        Record record = RecordList.get(i);
        //if(holder != null&&record!=null)
            holder.information.setText(record.TextLine());
    }

    @Override
    public int getItemCount() {
        return RecordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView information;
        public int position;

        public ViewHolder(View view)
        {
            super(view);
            information = (TextView) view.findViewById(R.id.textView);
        }

    }



}