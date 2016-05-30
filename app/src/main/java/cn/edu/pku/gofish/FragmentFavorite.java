package cn.edu.pku.gofish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String url="";

    AsyncHttpClient client = new AsyncHttpClient();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.favorite_recordlist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        RecordList = new ArrayList<Record>();
        idList = new ArrayList<Integer>();
        initData();
        recordCardAdapter = new RecordCardAdapter(RecordList,getContext());
        recyclerView.setAdapter(recordCardAdapter);
    }

    public void initData()
    {
        for(int i=0;i<idList.size();i++)
        {
            int id = idList.get(i);
            Record tmp = new Record(id);
            tmp.downloadFile();
            RecordList.add(tmp);
        }
    }

    public void refresh()
    {
        initData();
        recordCardAdapter.refresh(RecordList);
    }

    public void downloadList(String url)
    {
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray list;
                    if(response.has("list"))
                    {
                        list = response.getJSONArray("list");
                        for (int i = 0; i < list.length() ; i++){
                            idList.add(list.getInt(i));
                        }
                    }

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
}