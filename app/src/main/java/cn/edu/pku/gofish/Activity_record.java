package cn.edu.pku.gofish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.pku.gofish.Model.Record;
import cz.msebera.android.httpclient.Header;

public class Activity_record extends AppCompatActivity {
    private static final int REQUEST_PREVIEW_CODE = 20;
    private Record record;
    private String url="";
    private int id;
    static SyncHttpClient client = new SyncHttpClient();
    static AsyncHttpClient client2 = new AsyncHttpClient();
    private TextView title;
    private TextView describetext;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private TextView addfav;
    private TextView sendreq;
    private Bitmap[] file;
    private int picnum;
    private List<String> picNameList;       //name from the server
    private ArrayList<String> localPicName;
    private ArrayList<String> imagePath;
    private SimpleDateFormat formatter;
    private String[] mThumbIds = {
    };
    private MyDialogFragment sendMessage;
    private boolean flag = true;
    private String urlpath = "http://gofish.hackpku.com:8003/api/items";
    private String urlpath1 = "http://gofish.hackpku.com:8003/api/images";
    //private String picname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_record);
        title = (TextView) findViewById(R.id.addTitle);
        describetext = (TextView) findViewById(R.id.editText1);
        gridView = (GridView) findViewById(R.id.gridView1);
        addfav= (TextView) findViewById(R.id.addfavourite);     //add favourite button
        sendreq=(TextView) findViewById(R.id.sendrequest);        //add request button
        picNameList = new ArrayList<String>();
        localPicName=new ArrayList<String>();
        imagePath=new ArrayList<String>();
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {       //click the add photo button
                String imgs = (String) parent.getItemAtPosition(position);
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(Activity_record.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(localPicName);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            }

        });

        gridAdapter = new GridAdapter(localPicName);
        gridView.setAdapter(gridAdapter);

        Bundle bundle=this.getIntent().getExtras();
        id=bundle.getInt("id");
        String idt=String.valueOf(id);
        record=new Record(idt);
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
        downloadphotolist();
        /*Log.d("NET","before download");
        downloadphoto();
        Log.d("NET","after download");*/

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

    private void downloadphotolist()
    {
        client2.get(urlpath + "/"+id+"/images", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("NET","in onSuccess");
                try {

                    JSONArray list=response;
                    if(list==null)
                        Log.d("NET","null");
                    //picNameList=null;
                    Log.d("NET","ActivityRecord getPictureName success");
                    for (int i = 0; i < list.length(); i++) {
                        picNameList.add(list.getString(i));
                        Log.d("NET","ActivityRecord get" + list.getString(i));
                    }
                    picnum=list.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("NET","ActivityRecord getPictureName Fail");
                }
                downloadphoto();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("info","in onFailure");
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("NET","ActivityRecord getPictureName Fail");
            }
        });
    }

    private void loadAdapter(List<String> paths){
        Log.d("NET","image list number:" + paths.size());
        if (imagePath!=null&& imagePath.size()>0){
            imagePath.clear();
        }
        imagePath.addAll(paths);
        gridAdapter  = new GridAdapter(imagePath);
        gridView.setAdapter(gridAdapter);
    }

    private void downloadphoto(){
        File sdCard = Environment.getExternalStorageDirectory();
        final File dir = new File(sdCard.getAbsolutePath() + "/dir1/dir2");
        final String dirstring=sdCard.getAbsolutePath()+"/dir1/dir2/";
        dir.mkdirs();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Log.d("NET", "get in downloadphoto");
        for(int j=0;j<picnum;j++)
        {
            client2.get(urlpath1 + "/" + picNameList.get(j), new BinaryHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("NET", "picture " + bytes.length);
                    String t = format.format(new Date());
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0,
                            bytes.length);
                    String tmpname="tmpfile" + t + ".jpg";
                    File file = new File(dir, tmpname);
                    tmpname=dirstring+tmpname;
                    localPicName.add(tmpname);
                    Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                    // 压缩比例
                    int quality = 100;
                    try {
                        // 若存在则删除
                        if (file.exists())
                            file.delete();
                        // 创建文件
                        file.createNewFile();
                        //
                        OutputStream stream = new FileOutputStream(file);
                        // 压缩输出
                        bmp.compress(format, quality, stream);
                        stream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("NET","download done");
                    loadAdapter(localPicName);
                }
                    @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Log.d("NET"," picture " + i + " download fail");
                    }
            });
        }
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


    private class GridAdapter extends BaseAdapter {
        public ArrayList<String> listUrls;     //添加一个默认图片路径，存放到List中
        //private int mMaxPosition;
        private LayoutInflater inflater;
        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            inflater = LayoutInflater.from(Activity_record.this);
        }
        public int getCount() {
            return listUrls.size();
        }

        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.griditem_addpic, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final String path=listUrls.get(position);
            Log.e("NET",path);
            Glide.with(Activity_record.this)
                    .load(path)
                    .placeholder(R.mipmap.default_error)
                    .error(R.mipmap.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(holder.image);
            return convertView;
        }
        public class ViewHolder {
            public ImageView image;
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
                Log.d("NET", "favorite delete failed "+responseString);
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
                Log.d("NET", "request send done"+new String(bytes));
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
