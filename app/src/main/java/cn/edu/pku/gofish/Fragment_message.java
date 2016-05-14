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

import cn.edu.pku.gofish.Adapter.MessageAdapter;
import cn.edu.pku.gofish.Model.Message1;


/**
 * Created by Iris on 16/5/14.
 */

public class Fragment_message extends Fragment {
    private List<Message1> MessageList;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
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
        MessageList = new ArrayList<Message1>();
        initData();
        messageAdapter=new MessageAdapter(MessageList);
        recyclerView.setAdapter(messageAdapter);
    }

    public void initData()
    {
        MessageList.add(new Message1("01:00","wmc","hello!"));
        MessageList.add(new Message1("02:00","wxp","hello!"));
        MessageList.add(new Message1("03:00","wcs","hello!"));
        MessageList.add(new Message1("04:00","wy","hello!"));
        MessageList.add(new Message1("10:00","xyj","hello!"));
        MessageList.add(new Message1("11:00","dh","hello!"));
    }
}
