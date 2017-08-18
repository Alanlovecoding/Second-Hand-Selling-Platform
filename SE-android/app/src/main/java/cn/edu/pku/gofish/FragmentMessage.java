package cn.edu.pku.gofish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.gofish.Adapter.MessageAdapter;
import cn.edu.pku.gofish.Model.Message1;
import cz.msebera.android.httpclient.Header;

/**
 * Created by leonardo on 16/5/7.
 */
public class FragmentMessage extends Fragment {

    private List<Message1> MessageList;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Integer> idList;
    private String url = "http://gofish.hackpku.com:8003/";
    private SwipeRefreshLayout mSwipeLayout;
    private boolean flag = true;
    AsyncHttpClient client = new AsyncHttpClient();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.messagelist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        mSwipeLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.id_swipe_ly);
        MessageList = new ArrayList<Message1>();
        idList = new ArrayList<Integer>();
        downloadList();
        messageAdapter=new MessageAdapter(MessageList,this.getContext());
        messageAdapter.setMessageManager(getChildFragmentManager());
        recyclerView.setAdapter(messageAdapter);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadList();
                flag = false;
            }
        });
    }

    public void initData()
    {
        for(int i=0;i<idList.size();i++)
        {
            int id = idList.get(i);
            Message1 tmp = new Message1(id);
            tmp.downloadFile();
            MessageList.add(tmp);
        }
    }

    public void refresh()
    {
        Log.d("NET", "Message" + "refresh");
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
                    Log.d("NET", "request get success");
                    for (int i = 0; i < list.length(); i++) {
                        idList.add(list.getInt(i));
                        Log.d("NET", "request get " + list.getInt(i));
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
