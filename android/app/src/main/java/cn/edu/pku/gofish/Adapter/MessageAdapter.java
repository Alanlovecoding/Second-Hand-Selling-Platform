package cn.edu.pku.gofish.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.pku.gofish.ActivityMessage;
import cn.edu.pku.gofish.Model.Message1;
import cn.edu.pku.gofish.R;

/**
 * Created by Iris on 16/5/13.
 */

//消息页面中的Adapter

public class MessageAdapter extends RecyclerView.Adapter {
    private List<Message1> MessageList;
    private Context context;
    public MessageAdapter(List<Message1> _MessageList,Context context) {
        MessageList = _MessageList;
        this.context = context;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("ViewHolder", "onCreateViewHolder, i: " + i);
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_message, viewGroup, false);
        return new ViewHolder(v,context);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Log.d("ViewHolder", "onBindViewHolder, i: " + i + ", viewHolder: " + viewHolder);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.position = i;
        Message1 message = MessageList.get(i);
        holder.time.setText(message.TimeLine());
        holder.usrname.setText(message.UsrnameLine());
        holder.briefmessage.setText(message.BriefMessageLine());

    }

    public int getItemCount() {
        return MessageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView time;
        public TextView usrname;
        public TextView briefmessage;
        //public ImageView usrpic;
        public int position;
        public LinearLayout cardview;
        private Context context;
        public ViewHolder(View view,Context _context)
        {
            super(view);
            this.context = _context;
            time = (TextView) view.findViewById(R.id.mvTime);
            usrname = (TextView) view.findViewById(R.id.mvusrname);
            briefmessage = (TextView) view.findViewById(R.id.mvbriefmessage);
            cardview = (LinearLayout) view.findViewById(R.id.cardview);
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityMessage.class);
                    context.startActivity(intent);

                }
            });
           // usrpic = (ImageView) view.findViewById(R.id.mvPerson);

        }

    }
}
