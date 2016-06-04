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
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

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
public class FragmentHomepage extends Fragment {
    private RecyclerView recyclerView;
    private RecordCardAdapter recordCardAdapter;
    private List<Record> RecordList;
    private List<Integer> idList;
    private String url = "http://gofish.hackpku.com:8003/api/items";
    private ImageView search;
    private EditText searchText;
    private SwipeRefreshLayout mSwipeLayout;
    private boolean flag = true;
    AsyncHttpClient client = new AsyncHttpClient();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.homepage_recordlist_recyclerview);
        search = (ImageView) getActivity().findViewById(R.id.ivSearchText);
        searchText = (EditText) getActivity().findViewById(R.id.etSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        mSwipeLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.id_swipe_ly);
        RecordList = new ArrayList<Record>();
        idList = new ArrayList<Integer>();
        downloadList();
        Log.d("NET", "homepage card");
        recordCardAdapter = new RecordCardAdapter(RecordList,getContext());
        recyclerView.setAdapter(recordCardAdapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = searchText.getText().toString();
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadList();
                flag = false;
            }
        });

    }

    public void initData() {
        Log.d("NET", "Homepage initData");
        for(int i=0;i<idList.size();i++)
        {
            int id = idList.get(i);
            String I = ""+id;
            Record tmp = new Record(id);
            RecordList.add(tmp);
            RecordList.get(i).downloadFile();
        }


    }

    public void refresh()
    {

        Log.d("NET", "Homepage refresh");
        RecordList.clear();
        for(int i=0;i<idList.size();i++)
        {
            int id = idList.get(i);
            String I = ""+id;
            Log.d("NET", "Homepage" + I);
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
        Log.d("NET","homepage refresh FALSE");
    }

    public void downloadList()
    {
        Log.d("NET", "HomePage");
        mSwipeLayout.setRefreshing(true);
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("NET", "HomePage get success");
                try {
                    JSONArray list = response;
                    idList.clear();
                    for (int i = 0; i < list.length(); i++) {
                        idList.add(list.getInt(i));
                        Log.d("NET", "HomePage get "+list.getInt(i));
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
        recordCardAdapter.refresh(RecordList);
    }
}
