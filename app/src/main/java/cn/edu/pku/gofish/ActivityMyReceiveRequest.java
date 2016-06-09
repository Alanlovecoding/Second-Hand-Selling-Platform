package cn.edu.pku.gofish;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.gofish.Adapter.MyMessageAdapter;
import cn.edu.pku.gofish.Model.Message1;
import cz.msebera.android.httpclient.Header;

/**
 * Created by leonardo on 16/6/4.
 */
public class ActivityMyReceiveRequest extends AppCompatActivity {
    private List<Message1> MessageList;
    private RecyclerView recyclerView;
    private MyMessageAdapter messageAdapter;
    private List<Integer> idList;
    private String url = "http://gofish.hackpku.com:8003/";
    private SwipeRefreshLayout mSwipeLayout;
    private boolean flag = true;
    AsyncHttpClient client = new AsyncHttpClient();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message);
        recyclerView = (RecyclerView) findViewById(R.id.messagelist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        MessageList = new ArrayList<Message1>();
        idList = new ArrayList<Integer>();
        downloadList();
        messageAdapter=new MyMessageAdapter(MessageList,getApplicationContext());
        messageAdapter.setMessageManager(getSupportFragmentManager());
        recyclerView.setAdapter(messageAdapter);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadList();
                flag = false;
            }
        });
    }
    public void refresh()
    {
        Log.d("NET", "ActivityMyReceiveRequest" + "refresh");
        MessageList.clear();
        for(int i=0;i<idList.size();i++)
        {
            int id = idList.get(i);
            Message1 tmp = new Message1(id);
            MessageList.add(tmp);
            MessageList.get(i).setInterface(new Message1.NoticeDialogListener() {

                @Override
                public void onDialogPositiveClick() {
                    newPage();
                }

                @Override
                public void onDialogNegativeClick() {

                }
            });
            MessageList.get(i).downloadFile();
        }
        mSwipeLayout.setRefreshing(false);

    }

    public void downloadList()
    {
        client.get("http://gofish.hackpku.com:8003/api/users/"+USR.usr_id+"/trade_requests/received", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONArray list = response;
                    idList.clear();
                    Log.d("NET", "ActivityMyReceiveRequest get success");
                    for (int i = 0; i < list.length(); i++) {
                        idList.add(list.getInt(i));
                        Log.d("NET", "ActivityMyReceiveRequest get " + list.getInt(i));
                    }
                    refresh();
                    mSwipeLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void newPage()
    {
        messageAdapter.refresh(MessageList);
    }
}
