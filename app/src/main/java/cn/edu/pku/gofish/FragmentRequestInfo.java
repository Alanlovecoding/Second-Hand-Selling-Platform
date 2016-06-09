package cn.edu.pku.gofish;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by leonardo on 16/6/9.
 */
public class FragmentRequestInfo extends DialogFragment {
    private Button cancelButton;
    private Button okButton;
    private Button deleteButton;
    private int key;
    private TextView username,email;
    private String user_name;
    private String e_mail;
    String url = "http://gofish.hackpku.com:8003/";
    AsyncHttpClient client = new AsyncHttpClient();

    private int user_id;
    public void setID(int user_id)
    {
        this.user_id = user_id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_info, null);
        getDialog().setTitle("操作请求");
        username = (TextView) view.findViewById(R.id.user_name);
        email = (TextView) view.findViewById(R.id.e_mail);
        cancelButton =(Button) view.findViewById(R.id.button_dialog_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }});
        download();
        return view;
    }
    private void download()
    {
        client.get("http://gofish.hackpku.com:8003/api/users/"+user_id, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    user_name = response.getString("name");
                    e_mail = response.getString("email");
                    username.setText(user_name);
                    email.setText(e_mail);
                    username.invalidate();
                    email.invalidate();
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
