package com.lanou3g.autohome.welcome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseActivity;
import com.lanou3g.autohome.main.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dllo on 16/5/5.
 */
public class WelcomeActivity extends BaseActivity {

    private ImageView imageView;
    private TextView timeTv;
    private CountDownTimer timer;
    Bitmap bitmap;
    InputStream is;
    HttpURLConnection connection;


    @Override
    protected int getLayout() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.aty_welcome;
    }

    @Override
    protected void initView() {
        imageView = bindView(R.id.welcome_iv);
        timeTv = (TextView) findViewById(R.id.welcome_tv);
        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();//在跳转的时候 取消timer
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        });

        timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // timeTv.setText(millisUntilFinished / 1000 + "s");
                timeTv.setText("点击跳过");
            }

            @Override
            public void onFinish() {
                timeTv.setText("跳转");
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        }.start();
    }

    @Override
    protected void initData() {
        MyImage myImage = new MyImage();
        myImage.execute("http://adm3.autoimg.cn/admdfs/g10/M12/76/1B/wKgH0Vcv_RqAafI9AAMYdEmLL2w312.jpg");

    }


    class MyImage extends AsyncTask<String , Void , Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            //下载图片
            String imgUrl = params[0];

            try {
                URL url = new URL(imgUrl);
                connection = (HttpURLConnection) url.openConnection();
                is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);

                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imageView.setImageBitmap(bitmap);
        }
    }

}
