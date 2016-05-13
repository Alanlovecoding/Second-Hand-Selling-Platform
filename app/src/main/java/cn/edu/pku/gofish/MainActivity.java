package cn.edu.pku.gofish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * Created by leonardo on 16/5/7.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentHomepage fragmentHomepage;
    private FragmentMessage fragmentMessage;
    private FragmentFavorite fragmentFavorite;
    private FragmentMe fragmentMe;

    private FrameLayout homepageFl, messageFl, favoriteFl, meFl;


    private ImageView homepageIv, messageIv, favoriteIv, meIv;


    private ImageView toggleImageView, plusImageView;


    private PopupWindow popWindow;

    private DisplayMetrics dm;
    Toolbar toolbar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();


        clickHomepage();
    }


    private void initView() {

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        homepageFl = (FrameLayout) findViewById(R.id.layout_homepage);
        messageFl = (FrameLayout) findViewById(R.id.layout_message);
        favoriteFl = (FrameLayout) findViewById(R.id.layout_favorite);
        meFl = (FrameLayout) findViewById(R.id.layout_me);


        homepageIv = (ImageView) findViewById(R.id.image_homepage);
        messageIv = (ImageView) findViewById(R.id.image_message);
        favoriteIv = (ImageView) findViewById(R.id.image_favorite);
        meIv = (ImageView) findViewById(R.id.image_me);


        toggleImageView = (ImageView) findViewById(R.id.toggle_btn);
        plusImageView = (ImageView) findViewById(R.id.plus_btn);

        fragmentHomepage = new FragmentHomepage();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_content, fragmentHomepage)
                .commit();

    }

    /**
     *
     */
    private void initData() {

        homepageFl.setOnClickListener(this);
        messageFl.setOnClickListener(this);
        favoriteFl.setOnClickListener(this);
        meFl.setOnClickListener(this);


        toggleImageView.setOnClickListener(this);
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.layout_homepage:
                clickHomepage();
                break;

            case R.id.layout_message:
                clickMessage();
                break;

            case R.id.layout_favorite:
                clickFavorite();
                break;

            case R.id.layout_me:
                clickMe();
                break;

            case R.id.toggle_btn:
               // clickToggleBtn();
                break;
        }
    }

    /**
     *
     */
    private void clickHomepage() {
        if(fragmentHomepage == null)
            fragmentHomepage = new FragmentHomepage();

        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_content, fragmentHomepage);

        fragmentTransaction.commit();

        homepageFl.setSelected(true);
        homepageIv.setSelected(true);

        messageFl.setSelected(false);
        messageIv.setSelected(false);

        favoriteFl.setSelected(false);
        favoriteIv.setSelected(false);

        meFl.setSelected(false);
        meIv.setSelected(false);
    }

    /**
     *
     */
    private void clickMessage() {
        if(fragmentMessage == null)
            fragmentMessage = new FragmentMessage();

        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_content, fragmentMessage);

        fragmentTransaction.commit();

        homepageFl.setSelected(false);
        homepageIv.setSelected(false);

        messageFl.setSelected(true);
        messageIv.setSelected(true);

        favoriteFl.setSelected(false);
        favoriteIv.setSelected(false);

        meFl.setSelected(false);
        meIv.setSelected(false);

        Intent intent = new Intent(MainActivity.this,ActivityMessage.class);
        startActivity(intent);
    }

    /**
     *
     */
    private void clickFavorite() {
        if(fragmentFavorite == null)
            fragmentFavorite = new FragmentFavorite();

        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_content, fragmentFavorite);

        fragmentTransaction.commit();

        homepageFl.setSelected(false);
        homepageIv.setSelected(false);

        messageFl.setSelected(false);
        messageIv.setSelected(false);

        favoriteFl.setSelected(true);
        favoriteIv.setSelected(true);

        meFl.setSelected(false);
        meIv.setSelected(false);
    }

    /**
     *
     */
    private void clickMe() {
        if(fragmentMe == null)
            fragmentMe = new FragmentMe();

        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_content, fragmentMe);

        fragmentTransaction.commit();

        homepageFl.setSelected(false);
        homepageIv.setSelected(false);

        messageFl.setSelected(false);
        messageIv.setSelected(false);

        favoriteFl.setSelected(false);
        favoriteIv.setSelected(false);

        meFl.setSelected(true);
        meIv.setSelected(true);
    }

    /*
    private void clickToggleBtn() {
        showPopupWindow(toggleImageView);

        plusImageView.setSelected(true);
    }


    private void changeButtonImage() {
        plusImageView.setSelected(false);
    }


    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.popwindow_layout, null);
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            // ����һ��PopuWidow����
            popWindow = new PopupWindow(view, dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // ʹ��ۼ� ��Ҫ������˵���ؼ����¼��ͱ���Ҫ���ô˷���
        popWindow.setFocusable(true);
        // ����������������ʧ
        popWindow.setOutsideTouchable(true);
        // ���ñ����������Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı���
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        // PopupWindow����ʾ��λ������
        // popWindow.showAtLocation(parent, Gravity.FILL, 0, 0);
        popWindow.showAsDropDown(parent, 0,0);

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // �ı���ʾ�İ�ťͼƬΪ����״̬
                changeButtonImage();
            }
        });

        // ���������¼�
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // �ı���ʾ�İ�ťͼƬΪ����״̬
                changeButtonImage();
                popWindow.dismiss();
                return false;
            }
        });
    }*/

}
