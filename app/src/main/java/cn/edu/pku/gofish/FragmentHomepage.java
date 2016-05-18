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

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.gofish.Adapter.RecordCardAdapter;
import cn.edu.pku.gofish.Model.Record;


/**
 * Created by leonardo on 16/5/7.
 */
public class FragmentHomepage extends Fragment {
    private RecyclerView recyclerView;
    private RecordCardAdapter recordCardAdapter;
    private List<Record> RecordList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.homepage_recordlist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        RecordList = new ArrayList<Record>();
        initData();
        recordCardAdapter = new RecordCardAdapter(RecordList,getContext());
        recyclerView.setAdapter(recordCardAdapter);
    }

    public void initData()
    {
        RecordList.add(new Record("$1"));
        RecordList.add(new Record("$2"));
        RecordList.add(new Record("$3"));
        RecordList.add(new Record("$4"));
        RecordList.add(new Record("$2"));
        RecordList.add(new Record("$3"));
        RecordList.add(new Record("$4"));
    }
}
