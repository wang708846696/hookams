package cn.life.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author wangwei
 */

public class AActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.tv_aaa);
        textView.setText("AActivity");
    }
}
