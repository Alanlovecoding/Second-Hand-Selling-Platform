package cn.edu.pku.gofish;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by leonardo on 16/5/30.
 */
public class FragmentRegister extends DialogFragment {
    private Button cancelButton;
    private Button okButton;
    private int key;
    private EditText username,e_mail,password,password_makesure;
    String url;
    NoticeDialogListener mListener;
    AsyncHttpClient client = new AsyncHttpClient();
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
        View view = inflater.inflate(R.layout.fragment_register, null);
        getDialog().setTitle("Register");

        cancelButton =(Button) view.findViewById(R.id.button_dialog_cancel);
        okButton = (Button) view.findViewById(R.id.button_dialog_ok);
        username = (EditText) view.findViewById(R.id.user_name);
        e_mail = (EditText) view.findViewById(R.id.e_mail);
        password = (EditText) view.findViewById(R.id.password);
        password_makesure = (EditText) view.findViewById(R.id.password_makesure);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onDialogNegativeClick(key);
                dismiss();
            }});
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //register();
                mListener.onDialogPositiveClick(key);
                dismiss();
            }
        });
        return view;
    }
    public void register()
    {
        String usr = username.getText().toString();
        String psw = password.getText().toString();
        String pswm = password_makesure.getText().toString();
        String e = e_mail.getText().toString();
        if(psw != pswm){
            key = 1;
            return;
        }
        if(!checkmail(e))
        {
            key = 2;
            return;
        }
        RequestParams params = new RequestParams();
        params.put("name", usr);
        params.put("password", psw);
        params.put("email", e);
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                    key = 0;
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                    key = 3;
            }
        });

    }
    public boolean checkmail(String e)
    {
        return true;
    }


}
