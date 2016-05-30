package cn.edu.pku.gofish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.edu.pku.gofish.Model.Record;
import cz.msebera.android.httpclient.Header;

public class Activity_record extends AppCompatActivity {

    private Record record;
    private String url="";
    private int id;
    AsyncHttpClient client = new AsyncHttpClient();
    private EditText title;
    private EditText describetext;
    private GridView gridView;
    private TextView addfav;
    private TextView sendreq;
    private Bitmap[] file;
    private int picnum;
    private SimpleDateFormat formatter;
    private String[] mThumbIds = {
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_record);
        title = (EditText) findViewById(R.id.addTitle);
        describetext = (EditText) findViewById(R.id.editText1);
        gridView = (GridView) findViewById(R.id.gridView1);
        addfav= (TextView) findViewById(R.id.addfavourite);
        sendreq=(TextView) findViewById(R.id.sendrequest);

        Bundle bundle=this.getIntent().getExtras();
        id=bundle.getInt("id");
        record=new Record(id);
        record.downloadFile();

        title.setText(record.getTitle());
        describetext.setText(record.getDescribetext());
        file=record.getFile();
        picnum=record.getImgcnt();
        for(int i=0;i<picnum;i++)
        {
            mThumbIds[i]=savingpic(file[i]);
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
}
