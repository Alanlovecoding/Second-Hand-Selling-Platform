package cn.edu.pku.gofish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;

import org.json.JSONArray;

import java.util.ArrayList;

import cn.edu.pku.gofish.Model.Record;

/**
 * Created by leonardo on 16/6/6.
 */
public class ActivityChange extends AppCompatActivity {
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ImageCaptureManager captureManager;       //相机拍照处理类

    private GridView gridView;
    private GridAdapter gridAdapter;
    private EditText title;
    private EditText describetext;
    private EditText pricetext,numbertext;
    private TextView issueConfirm;
    private String TAG =Activity_add.class.getSimpleName();

    int id;
    Record record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        gridView = (GridView) findViewById(R.id.gridView1);
        title = (EditText) findViewById(R.id.addTitle);
        describetext = (EditText) findViewById(R.id.editText1);
        pricetext = (EditText) findViewById(R.id.addPrice);
        issueConfirm = (TextView) findViewById(R.id.IssueConfirm);
        numbertext = (EditText) findViewById(R.id.addType);
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                if ("000000".equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(ActivityChange.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(9); // 最多选择照片数量，默认为9
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(ActivityChange.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }

        });
        imagePaths.add("000000");
        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
        issueConfirm.setText("修改请求");
        issueConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NET", "activity click");
                String titleString = title.getText().toString();
                String describeString = describetext.getText().toString();
                String priceString = pricetext.getText().toString();
                String num = numbertext.getText().toString();
                String price;
                String number;
                if (priceString != null) {
                    try {
                        price = priceString;
                    } catch (Exception e) {
                        price = "0";
                    }
                } else {
                    price = "0";

                }
                if (num != null) {
                    try {
                        number = num;
                    } catch (Exception e) {
                        number = "0";
                    }
                } else {
                    number = "0";
                }
                Record item = new Record(String.valueOf(USR.usr_id), titleString, describeString, price, number, null, "unreviewed");
                item.setID(record.getID());
                item.setInterface(new Record.NoticeDialogListener() {

                    @Override
                    public void onDialogPositiveClick() {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onDialogNegativeClick() {
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);
                    }
                });
                try {
                    Log.d("NET", "activity");
                    item.changeFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "list: " + "list = [" + list.size());
                    loadAdpater(list);

                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("000000")){
            paths.remove("000000");
        }
        paths.add("000000");
        imagePaths.addAll(paths);
        gridAdapter  = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;     //添加一个默认图片路径，存放到List中
        //private int mMaxPosition;
        private LayoutInflater inflater;

        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            if(listUrls.size()==10)
            {
                listUrls.remove(listUrls.size()-1);
            }
            inflater = LayoutInflater.from(ActivityChange.this);
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
                convertView = inflater.inflate(R.layout.griditem_addpic, parent,false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }

            final String path=listUrls.get(position);
            if(path.equals("000000")){
                holder.image.setImageResource(R.drawable.square_plus_icon);
            }
            else {
                Glide.with(ActivityChange.this)
                        .load(path)
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }
        public class ViewHolder {
            public ImageView image;
        }
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(ActivityChange.this, "change success", Toast.LENGTH_LONG).show();
                    ActivityChange.this.finish();
                    break;
                case 0:
                    Toast.makeText(ActivityChange.this, "change failed", Toast.LENGTH_LONG).show();
                    ActivityChange.this.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void newPage()
    {
        title.setText(record.getTitle());
        describetext.setText(record.getDescribetext());
        gridView.invalidate();
        title.invalidate();
        describetext.invalidate();

    }
}
