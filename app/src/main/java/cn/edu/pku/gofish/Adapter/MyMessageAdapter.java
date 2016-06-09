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

import cn.edu.pku.gofish.FragmentItemCheck;
import cn.edu.pku.gofish.Model.Message1;
import cn.edu.pku.gofish.R;

/**
 * Created by leonardo on 16/6/6.
 */
public class MyMessageAdapter extends RecyclerView.Adapter {
    private List<Message1> MessageList;
    private Context context;
    private FragmentManager fm;
    AsyncHttpClient client = new AsyncHttpClient();

    public MyMessageAdapter(List<Message1> _MessageList,Context context) {
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
        final Message1 message = MessageList.get(i);

        holder.usrname.setText("收信人昵称: "+message.UsrnameLine());
        holder.briefmessage.setText("我对他说: "+message.BriefMessageLine());
        holder.status.setText(message.getStatus());
        holder.time.setText(message.getTime());
        holder.title.setText("我感兴趣的: "+message.getTitle());

        Log.d("NET", "messageAdapter message1 status" +message.getStatus() );
        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FragmentItemCheck myDialogFragment = new FragmentItemCheck();
                myDialogFragment.setInterface(new FragmentItemCheck.NoticeDialogListener() {
                    @Override
                    public void onDialogPositiveClick(int key) {
                        //Toast.makeText(context, "同意", Toast.LENGTH_LONG).show();
                        //message.uploadFile(true);
                    }

                    @Override
                    public void onDialogNegativeClick(int key) {
                        Toast.makeText(context, "删除", Toast.LENGTH_LONG).show();
                        message.deleteFile();
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
        public TextView title;
        public TextView status;
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
            title=(TextView) view.findViewById(R.id.mvitemtitle);
            status=(TextView) view.findViewById(R.id.mvstate);
            time=(TextView) view.findViewById(R.id.mvtime);

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

