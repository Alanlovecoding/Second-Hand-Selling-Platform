package cn.edu.pku.gofish;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by leonardo on 16/6/6.
 */
public class FragmentMyCheck extends DialogFragment {
    private Button cancelButton;
    private Button okButton;
    private Button deleteButton;
    private int key;
    NoticeDialogListener mListener;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(int key);

        public void onDialogNegativeClick(int key);
    }

    public void setInterface(NoticeDialogListener _Listener)
    {
        mListener = _Listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, null);
        getDialog().setTitle("操作请求");

        cancelButton =(Button) view.findViewById(R.id.button_dialog_cancel);
        okButton = (Button) view.findViewById(R.id.button_dialog_ok);
        deleteButton = (Button) view.findViewById(R.id.button_dialog_delete);
        okButton.setText("修改");
        deleteButton.setText("删除");
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }});
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(0);
                dismiss();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(0);
                dismiss();
            }
        });
        return view;
    }
}