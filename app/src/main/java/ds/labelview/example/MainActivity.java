package ds.labelview.example;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

import ds.labelview.LabelView;

public class MainActivity extends AppCompatActivity {

    private static final int COUNT = 1000;
    private static final String TEXT =
            "Even on a new device like the Nexus 5, the initial text drawing step for a complex caption with a dozen lines of text can take up to 50ms, and the text " +
                    "measuring step can take up to 30ms";
    public static final int SIZE_DP = 8;
    private static final int COLOR = Color.BLACK;
    private static final int PADDING = 10;
    private static final int GRAVITY = Gravity.START;

    ViewGroup root;
    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = (ViewGroup) findViewById(R.id.content);

    }

    private void fillWithTextViews() {
        root.removeAllViews();
        trace("textView");
        for (int i = 0; i < COUNT; i++) {
            TextView view = new TextView(this);
            root.addView(view);
            view.setPadding(PADDING, PADDING, PADDING, PADDING);
            view.setGravity(GRAVITY);
            view.setTextColor(COLOR);
            view.setTextSize(SIZE_DP);
            view.setShadowLayer(10, 0, 10, Color.RED);
            view.setText(TEXT);
        }
        trace();
    }

    private void fillWithLabelViews() {
        root.removeAllViews();
        trace("labelView");
        for (int i = 0; i < COUNT; i++) {
            LabelView view = new LabelView(this);
            root.addView(view);
            view.setPadding(PADDING, PADDING, PADDING, PADDING);
            view.setGravity(GRAVITY);
            view.setTextColor(COLOR);
            view.setTextSize(SIZE_DP);
            view.setShadowLayer(10, 0, 10, Color.RED);
            view.setText(TEXT);
        }
        trace();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.text_view:
                fillWithTextViews();
                break;
            case R.id.label_view:
                fillWithLabelViews();
                break;
            case R.id.xml:
                fillFromXml();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void fillFromXml() {
        getLayoutInflater().inflate(R.layout.demo,root,true);
    }

    private void trace(String... text) {
        if (text != null && text.length > 0) {
            Log.v("trace", text[0]);
            time = System.currentTimeMillis();
        } else {
            Log.v("trace", "time=" + (System.currentTimeMillis() - time));
        }
    }
}
