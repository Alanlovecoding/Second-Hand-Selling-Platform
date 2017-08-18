package cn.edu.pku.gofish;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    String url="http://gofish.hackpku.com:8003/api/register";
    NoticeDialogListener mListener;
    AsyncHttpClient client = new AsyncHttpClient();

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(int key);

        public void onDialogNegativeClick(int key);
    }
   public void setInterface(NoticeDialogListener _Listener)
    {
        mListener = _Listener;
        Log.d("Register","go");
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

        username.setText("666");
        e_mail.setText("123@test.com");
        password.setText("123456");
        password_makesure.setText("123456");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onDialogNegativeClick(key);
                dismiss();
            }});
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                mListener.onDialogPositiveClick(key);
                //dismiss();
            }
        });
        return view;
    }
    public void register()
    {
        Log.d("NET","register begin");
        String usr = username.getText().toString();
        String psw = password.getText().toString();
        String pswm = password_makesure.getText().toString();
        String e = e_mail.getText().toString();
        if(!psw.equals(pswm)){
            key = 1;
            Log.d("NET", "register begin "+key);
            return;
        }
        if(!checkmail(e))
        {
            key = 2;
            Log.d("NET", "register begin "+key);
            return;
        }
        RequestParams params = new RequestParams();
        params.put("name", usr);
        params.put("password", psw);
        params.put("email", e);
        Log.d("NET", "register begin "+params.toString());
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                    key = 0;
                Log.d("NET","register success "+new String(bytes));
                Toast.makeText(getContext(), "register success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                    key = 3;
                Log.d("NET","register failed "+new String(bytes));
                Toast.makeText(getContext(), "register failed", Toast.LENGTH_LONG).show();
            }
        });

    }
    public boolean checkmail(String e)
    {
        return true;
    }


}
