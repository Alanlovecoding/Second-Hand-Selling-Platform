package cn.edu.pku.gofish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by leonardo on 16/5/7.
 */
public class FragmentMe extends Fragment {
    private FrameLayout MyItem,MyReceive,MySend;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyItem = (FrameLayout) getActivity().findViewById(R.id.layout_meIssue);

        MySend = (FrameLayout) getActivity().findViewById(R.id.layout_meRentOut);

        MyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NET", "fragment me ");
                Intent intent = new Intent(getContext(), cn.edu.pku.gofish.ActivityMyItem.class);
                getContext().startActivity(intent);
            }
        });



        MySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NET", "fragment me ");
                Intent intent = new Intent(getContext(), cn.edu.pku.gofish.ActivityMySendRequest.class);
                getContext().startActivity(intent);
            }
        });
    }

}
