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

import cn.edu.pku.gofish.Adapter.MyRecordAdapter;
import cn.edu.pku.gofish.Model.Record;
import cz.msebera.android.httpclient.Header;

/**
 * Created by leonardo on 16/6/4.
 */
public class ActivityMyItem extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyRecordAdapter recordCardAdapter;
    private List<Record> RecordList;
    private List<Integer> idList;
    private SwipeRefreshLayout mSwipeLayout;

    private String url="http://gofish.hackpku.com:8003/";
    boolean flag =true;
    AsyncHttpClient client = new AsyncHttpClient();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorite);
        recyclerView = (RecyclerView) findViewById(R.id.favorite_recordlist_recyclerview);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        RecordList = new ArrayList<Record>();
        idList = new ArrayList<Integer>();
        downloadList();
        recordCardAdapter = new MyRecordAdapter(RecordList,getApplicationContext());
        recordCardAdapter.setMessageManager(getSupportFragmentManager());
        recyclerView.setAdapter(recordCardAdapter);
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

        Log.d("NET", "ActivityMySend refresh");
        RecordList.clear();
        for(int i=0;i<idList.size();i++)
        {
            int idt = idList.get(i);
            String id=String.valueOf(idt);
            String I = ""+id;
            Log.d("NET", "ActivityMySend" + I);
            Record tmp = new Record(id);
            RecordList.add(tmp);
            //if(i == idList.size()-1)
            //{
            RecordList.get(i).setInterface(new Record.NoticeDialogListener(){

                @Override
                public void onDialogPositiveClick() {
                    newPage();
                }

                @Override
                public void onDialogNegativeClick() {

                }
            });
            //}
            RecordList.get(i).downloadFile();
        }

        mSwipeLayout.setRefreshing(false);
        Log.d("NET", "ActivityMySend refresh FALSE");
    }

    public void downloadList()
    {
        Log.d("NET", "ActivityMySend download");
        client.get(url+"api/users/" + USR.usr_id + "/items", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONArray list = response;
                    idList.clear();
                    Log.d("NET", "ActivityMySend get success");

                    for (int i = 0; i < list.length(); i++) {
                        idList.add(list.getInt(i));
                        Log.d("NET", "ActivityMySend get" + list.getInt(i));
                    }

                    refresh();
                    USR.favoriteList = idList;
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
        recordCardAdapter.refresh(RecordList);
    }

}
