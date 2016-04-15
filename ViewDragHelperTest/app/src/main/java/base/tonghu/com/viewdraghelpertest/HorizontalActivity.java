package base.tonghu.com.viewdraghelpertest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by  on 4/14/16.
 */
public class HorizontalActivity extends AppCompatActivity{
    private View toDragView;
    private ViewDragHelper viewDragHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        toDragView = findViewById(R.id.toDrag);
    }
}
