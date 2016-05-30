package cn.edu.pku.gofish;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by leonardo on 16/5/30.
 */
public class FragmentCheck extends DialogFragment {
    private Button cancelButton;
    private Button okButton;
    private Button deleteButton;
    private String key;
    NoticeDialogListener mListener;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(String key);

        public void onDialogNegativeClick(String key);
    }

    public void setInterface(NoticeDialogListener _Listener)
    {
        mListener = _Listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, null);
        getDialog().setTitle("请输入动态口令");

        cancelButton =(Button) view.findViewById(R.id.button_dialog_cancel);
        okButton = (Button) view.findViewById(R.id.button_dialog_ok);
        deleteButton = (Button) view.findViewById(R.id.button_dialog_delete);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(key);
                dismiss();
            }});
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(key);
                dismiss();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(key);
                dismiss();
            }
        });
        return view;
    }
}
