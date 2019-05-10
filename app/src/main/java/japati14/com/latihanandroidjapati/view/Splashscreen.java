package japati14.com.latihanandroidjapati.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import japati14.com.latihanandroidjapati.R;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        TextView title1 = (TextView)findViewById(R.id.titlesplash);
        TextView titleCompany = (TextView)findViewById(R.id.titleCompany);
        //ImageView img = (ImageView)findViewById(R.id.imageView2);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Handycheera.otf");
        Typeface fontCompany = Typeface.createFromAsset(getAssets(),"fonts/Captureit.ttf");

        title1.setTypeface(typeface);
        titleCompany.setTypeface(fontCompany);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent menu = new Intent(Splashscreen.this,FormLogin.class);
                startActivity(menu);
                finish();
            }
        },1000);
    }
}
