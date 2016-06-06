package cn.edu.pku.gofish;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by leonardo on 16/4/28.
 */
public class MyDialogFragment extends DialogFragment {


    private Button cancelButton;
    private Button okButton;
    private String key;
    NoticeDialogListener mListener;
    private EditText message;
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
        View view = inflater.inflate(R.layout.fragment_dialog, null);
        getDialog().setTitle("");

        cancelButton =(Button) view.findViewById(R.id.button_dialog_cancel);
        okButton = (Button) view.findViewById(R.id.button_dialog_ok);
        message = (EditText)  view.findViewById(R.id.message);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(key);
                dismiss();
            }});
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = message.getText().toString();
                mListener.onDialogPositiveClick(key);
                dismiss();
            }
        });
        return view;
    }


}
