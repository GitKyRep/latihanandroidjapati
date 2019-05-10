package japati14.com.latihanandroidjapati.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import japati14.com.latihanandroidjapati.R;
import japati14.com.latihanandroidjapati.util.AlertDialogMsg;
import japati14.com.latihanandroidjapati.util.Server;
import japati14.com.latihanandroidjapati.util.TypefaceSpan;

public class FormMainMenu extends AppCompatActivity {
    private CardView CAMenu1;
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    AlertDialogMsg msg = new AlertDialogMsg();
    private static final String TAG_USER_NIP = "user_nip";
    private static final String TAG = FormMainMenu.class.getSimpleName();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.keluar:
                keluar();
                break;
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_main_menu);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        String nama = sharedpreferences.getString("nama", "");

        TextView txtNamaUser = (TextView) findViewById(R.id.txtNamaUser);


        SpannableString s = new SpannableString("Ngabsen");
        s.setSpan(new TypefaceSpan(this, "Handycheera.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);
        txtNamaUser.setText(nama);
        CAMenu1= (CardView) findViewById(R.id.CAMenu1);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    private void keluar() {
        Runnable ifTrue = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), FormLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //untuk menghapus session anggota
                SharedPreferences preferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                startActivity(intent);

            }
        };

        msg.messageDialog(FormMainMenu.this,"Informasi","Anda yakin ingin keluar aplikasi ?","Ya", "Tidak", ifTrue, null);
    }

}
