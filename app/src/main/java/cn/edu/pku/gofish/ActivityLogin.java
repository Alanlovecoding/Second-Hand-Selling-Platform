package cn.edu.pku.gofish;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by leonardo on 16/6/2.
 */
public class ActivityLogin extends Activity{
    private EditText userName, password;
    private Button btn_login, btn_visitor;
    private CheckBox savePass;
    private String userNameValue, passwordValue;
    private SharedPreferences sharedPreferences;
    private FragmentRegister register;
    String url;
    AsyncHttpClient client = new AsyncHttpClient();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        userName = (EditText) findViewById(R.id.userNameText);
        password = (EditText) findViewById(R.id.passwdText);
        btn_login = (Button) findViewById(R.id.bnLogin);

        btn_visitor = (Button) findViewById(R.id.bnVisitor);
        sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue = userName.getText().toString();
                passwordValue = password.getText().toString();
                jump2Logon();
            }
        });

        btn_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (register == null) {
                    register = new FragmentRegister();
                    register.setInterface( new FragmentRegister.NoticeDialogListener() {
                        public void onDialogPositiveClick(int key) {
                            if(key == 0)
                            {
                                jump2Logon();
                            }
                        }

                        public void onDialogNegativeClick(int key) {

                        }
                    });

                }
            }});
    }
    public void login()
    {
        RequestParams params = new RequestParams();
        params.put("username", userNameValue);
        params.put("password", passwordValue);

        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                    USR.usrname = userNameValue;
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }

    public void jump2Logon() {
        Intent intent = new Intent(ActivityLogin.this, cn.edu.pku.gofish.MainActivity.class);
        ActivityLogin.this.startActivity(intent);
        this.finish();
    }
}
