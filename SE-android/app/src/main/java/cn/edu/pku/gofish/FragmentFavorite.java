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
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.gofish.Adapter.RecordCardAdapter;
import cn.edu.pku.gofish.Model.Record;
import cz.msebera.android.httpclient.Header;

/**
 * Created by leonardo on 16/5/7.
 */
public class FragmentFavorite extends Fragment {
    private RecyclerView recyclerView;
    private RecordCardAdapter recordCardAdapter;
    private List<Record> RecordList;
    private List<Integer> idList;
    private SwipeRefreshLayout mSwipeLayout;

    private String url="http://gofish.hackpku.com:8003/";
    boolean flag =true;
    AsyncHttpClient client = new AsyncHttpClient();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.favorite_recordlist_recyclerview);
        mSwipeLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.id_swipe_ly);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        RecordList = new ArrayList<Record>();
        idList = new ArrayList<Integer>();
        downloadList();
        recordCardAdapter = new RecordCardAdapter(RecordList,getContext());
        recyclerView.setAdapter(recordCardAdapter);
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
        Log.d("NET", "favorite initData");
        RecordList.clear();
        for(int i=0;i<idList.size();i++)
        {
            int id = idList.get(i);
            String idt= String.valueOf(id);
            Record tmp = new Record(idt);
            tmp.downloadFile();
            RecordList.add(tmp);
        }
    }

    public void refresh()
    {

        Log.d("NET", "favorite refresh");
        RecordList.clear();
        for(int i=0;i<idList.size();i++)
        {
            int id = idList.get(i);
            String I = ""+id;
            Log.d("NET", "favorite" + I);
            String idt=String.valueOf(id);
            Record tmp = new Record(idt);
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
        Log.d("NET", "afvorite refresh FALSE");
    }

    public void downloadList()
    {
        Log.d("NET", "favorite download");
        client.get(url+"api/users/" + USR.usr_id + "/favorites", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONArray list = response;
                    idList.clear();
                    Log.d("NET", "favorite get success");

                    for (int i = 0; i < list.length(); i++) {
                        idList.add(list.getInt(i));
                        Log.d("NET", "favorite get" + list.getInt(i));
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
