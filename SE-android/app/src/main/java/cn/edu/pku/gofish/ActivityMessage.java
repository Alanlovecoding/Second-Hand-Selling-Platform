package cn.edu.pku.gofish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.gofish.Adapter.MsgAdapter;
import cn.edu.pku.gofish.Model.Message;

/**
 * Created by leonardo on 16/5/13.
 */
public class ActivityMessage extends AppCompatActivity {
    private ListView msgListView;
    private EditText inputText;
    private MsgAdapter adapter;
    private List<Message> msgList = new ArrayList<Message>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initMsgs();
        adapter = new MsgAdapter(ActivityMessage.this,R.layout.message_item,msgList);
        msgListView = (ListView) findViewById(R.id.message_list_view);
        msgListView.setAdapter(adapter);
    }

    private void initMsgs()
    {
        Message msg1 = new Message("Hello guy.",Message.TYPE_RECEVIED);
        msgList.add(msg1);
        Message msg2 = new Message("I wang to buy your things, I think it is really cool. Can you understand it?",Message.TYPE_RECEVIED);
        msgList.add(msg2);
        Message msg3 = new Message("You get it",Message.TYPE_SENT);
        msgList.add(msg3);
    }
}
