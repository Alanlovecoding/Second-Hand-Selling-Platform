package cn.edu.pku.gofish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import cn.edu.pku.gofish.Model.Record;
import cz.msebera.android.httpclient.Header;

public class Activity_record extends AppCompatActivity {

    private Record record;
    private String url="";
    private int id;
    static SyncHttpClient client = new SyncHttpClient();
    static AsyncHttpClient client2 = new AsyncHttpClient();
    private TextView title;
    private TextView describetext;
    private GridView gridView;
    private TextView addfav;
    private TextView sendreq;
    private Bitmap[] file;
    private int picnum;
    private SimpleDateFormat formatter;
    private String[] mThumbIds = {
    };
    private MyDialogFragment sendMessage;
    private boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_record);
        title = (TextView) findViewById(R.id.addTitle);
        describetext = (TextView) findViewById(R.id.editText1);
        gridView = (GridView) findViewById(R.id.gridView1);
        addfav= (TextView) findViewById(R.id.addfavourite);
        sendreq=(TextView) findViewById(R.id.sendrequest);

        Bundle bundle=this.getIntent().getExtras();
        id=bundle.getInt("id");
        record=new Record(id);
        record.setInterface(new Record.NoticeDialogListener() {

            @Override
            public void onDialogPositiveClick() {
                newPage();
                Log.d("NET", "activity page refresh");
            }

            @Override
            public void onDialogNegativeClick() {

            }
        });
        record.downloadFile();


        flag = find(id);
        if(flag)
        {
            addfav.setText("取消收藏");
        }
        else
        {
            addfav.setText("添加收藏");
        }
        addfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NET", "record flag "+ flag );
                if(flag)
                {
                    deleteFavorite();
                }
                else
                {
                    addFavorite();
                }

            }
        });
        sendreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendMessage == null) {
                    sendMessage = new MyDialogFragment();
                    sendMessage.setInterface(new MyDialogFragment.NoticeDialogListener() {
                                                 @Override
                                                 public void onDialogPositiveClick(String key) {
                                                     sendMessage(key);
                                                 }

                                                 @Override
                                                 public void onDialogNegativeClick(String key) {

                                                 }
                                             }
                    );
                }
                sendMessage.show(getSupportFragmentManager(),"dialog_fragment");

            }
        });
        /*
        file=record.getFile();
        picnum=record.getImgcnt();
        for(int i=0;i<picnum;i++)
        {
            mThumbIds[i]=savingpic(file[i]);
        }*/
    }

    private String savingpic(Bitmap mBitmap)
    {
        int cnt=0;
        String date=null;
        File sdCard=null;
        try{
            sdCard= Environment.getExternalStorageDirectory();
            File dir= new File(sdCard.getAbsolutePath()+"/goFish/img");
            dir.mkdirs();
            formatter=new SimpleDateFormat("yyyy;MM:dd:HH:mm:ss");
            date=formatter.format(new java.util.Date());
            date+=cnt;
            date+=".jpg";
            File file1= new File(dir,date);
            FileOutputStream f=new FileOutputStream(file1,false);
            mBitmap.compress(Bitmap.CompressFormat.JPEG,100,f);
            f.flush();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        date=sdCard.getAbsolutePath()+"/goFish/img"+date;
        return date;
    }


    private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private int num;
        public ImageAdapter(Context context,int num) {
            this.mContext=context;
            this.num = num;
        }
        @Override
        public int getCount() {
            return num;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            //定义一个ImageView,显示在GridView里
            ImageView imageView;
            FileInputStream fis=null;
            if(convertView==null){
                imageView=new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }else{
                imageView = (ImageView) convertView;
            }
            try {
               fis=new FileInputStream(mThumbIds[position]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            imageView.setImageBitmap(bitmap);
            return imageView;
        }

    }

    public boolean find(int id)
    {
        for(int i=0;i<USR.favoriteList.size();i++)
        {
            int ID = USR.favoriteList.get(i);
            if(id == ID)
                return true;
        }
        return false;
    }

    public void addFavorite()
    {

        RequestParams params = new RequestParams();

        params.put("item_id", id);

        Log.d("NET", "favorite post begin " + params.toString());

        client2.post("http://gofish.hackpku.com:8003/api/users/"+USR.usr_id+"/favorites", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("NET", "favorite add done");
                Toast.makeText(getApplicationContext(), "add favorite success", Toast.LENGTH_LONG).show();
                addfav.setText("取消收藏");
                flag = true;
                newPage();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("NET", "favorite add failed" + bytes);
                Toast.makeText(getApplicationContext(), "add favorite failed", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void deleteFavorite()
    {
        client2.get("http://gofish.hackpku.com:8003/api/users/" + USR.usr_id + "/favorites/" + id + "/delete", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("NET", "favorite delete success");
                Toast.makeText(getApplicationContext(), "delete favorite success", Toast.LENGTH_LONG).show();
                addfav.setText("添加收藏");
                flag = false;
                newPage();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("NET", "favorite delete failed");
                Toast.makeText(getApplicationContext(), "delete favorite failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendMessage(String Message)
    {

        RequestParams params = new RequestParams();
        params.put("user_id", USR.usr_id);
        params.put("item_id", id);
        params.put("message",Message);
        params.put("status","create");
        //user_id,item_id,message,status create', 'edit', 'destroy
        Log.d("NET", "request post begin " + params.toString());

        client2.post("http://gofish.hackpku.com:8003/api/trade_requests", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("NET", "request send done");
                Toast.makeText(getApplicationContext(), "send request success", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("NET", "request send failed" + bytes);
                Toast.makeText(getApplicationContext(), "send request failed", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void newPage()
    {
        title.setText(record.getTitle());
        describetext.setText(record.getDescribetext());
        gridView.invalidate();
        title.invalidate();
        describetext.invalidate();
        addfav.invalidate();
    }
}
