package cn.edu.pku.gofish.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import java.util.List;

import cn.edu.pku.gofish.FragmentCheck;
import cn.edu.pku.gofish.Model.Message1;
import cn.edu.pku.gofish.R;

/**
 * Created by Iris on 16/5/13.
 */

//消息页面中的Adapter

public class MessageAdapter extends RecyclerView.Adapter {
    private List<Message1> MessageList;
    private Context context;
    private FragmentManager fm;
    AsyncHttpClient client = new AsyncHttpClient();

    public MessageAdapter(List<Message1> _MessageList,Context context) {
        MessageList = _MessageList;
        this.context = context;

    }
    public void setMessageManager(FragmentManager fm)
    {
        this.fm = fm;
    }
    public void refresh(List<Message1> _RecordList)
    {
        MessageList = _RecordList;
        notifyDataSetChanged();
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

        holder.usrname.setText(message.UsrnameLine());
        holder.briefmessage.setText("send time: "+message.TimeLine()+"\r\n"+"brief message: "+message.BriefMessageLine());

        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FragmentCheck myDialogFragment = new FragmentCheck();
                myDialogFragment.setInterface(new FragmentCheck.NoticeDialogListener() {
                    @Override
                    public void onDialogPositiveClick(int key) {
                        Toast.makeText(context, "同意", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDialogNegativeClick(int key) {
                        Toast.makeText(context, "拒绝", Toast.LENGTH_LONG).show();
                    }
                });
                myDialogFragment.show(fm, "dialog_fragment");
                return false;
            }
        });
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
        public Button correct,cancel;
        public ViewHolder(View view,Context _context)
        {
            super(view);
            this.context = _context;

            usrname = (TextView) view.findViewById(R.id.mvusrname);
            briefmessage = (TextView) view.findViewById(R.id.mvbriefmessage);
            cardview = (LinearLayout) view.findViewById(R.id.cardview);

            /*cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityMessage.class);
                    context.startActivity(intent);

                }
            });*/
           // usrpic = (ImageView) view.findViewById(R.id.mvPerson);

        }

    }
}
