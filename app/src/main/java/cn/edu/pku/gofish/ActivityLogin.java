package cn.edu.pku.gofish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by leonardo on 16/6/2.
 */
public class ActivityLogin extends FragmentActivity {
    private EditText userName, password;
    private Button btn_login, btn_visitor;
    private CheckBox savePass;
    private String userNameValue, passwordValue;
    private SharedPreferences sharedPreferences;
    private FragmentRegister register;
    private String url = "http://gofish.hackpku.com:8003/api/login";

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
        userName.setText("123@test.com");
        password.setText("123456");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue = userName.getText().toString();
                passwordValue = password.getText().toString();
                login();
                //USR.usr_id = 1;
                //jump2Logon();
            }
        });

        btn_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (register == null) {
                    register = new FragmentRegister();
                    register.setInterface(new FragmentRegister.NoticeDialogListener() {
                        public void onDialogPositiveClick(int key) {
                            if (key == 0) {

                            }
                        }

                        public void onDialogNegativeClick(int key) {

                        }
                    });

                }
                register.show(getSupportFragmentManager(), "dialog_fragment");

            }});
    }
    public void login()
    {
        RequestParams params = new RequestParams();
        params.put("email", userNameValue);
        params.put("password", passwordValue);
        Log.d("NET", "login " + params.toString());
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                String message = new String(bytes);
                if(message.equals("0"))
                {
                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
                }
                JSONObject tmp = new JSONObject();
                try {
                    tmp = new JSONObject(message);
                    USR.usrname=tmp.getString("name");
                    USR.usr_id = Integer.parseInt(tmp.getString("id"));
                    USR.email = tmp.getString("email");
                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
                }
                jump2Logon();
                Log.d("NET","login success"+tmp.toString());
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("NET","login failes"+new String(bytes));
            }
        });

    }

    public void jump2Logon() {
        Intent intent = new Intent(ActivityLogin.this, cn.edu.pku.gofish.MainActivity.class);
        ActivityLogin.this.startActivity(intent);
        this.finish();
    }


}