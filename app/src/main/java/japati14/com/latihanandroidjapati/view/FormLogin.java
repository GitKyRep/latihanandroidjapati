package japati14.com.latihanandroidjapati.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import japati14.com.latihanandroidjapati.R;
import japati14.com.latihanandroidjapati.config.AppController;
import japati14.com.latihanandroidjapati.config.Config;
import japati14.com.latihanandroidjapati.util.AlertDialogMsg;
import japati14.com.latihanandroidjapati.util.NetInfo;
import japati14.com.latihanandroidjapati.util.NotificationUtils;
import japati14.com.latihanandroidjapati.util.RequestPermissionHandler;
import japati14.com.latihanandroidjapati.util.Server;

public class FormLogin extends AppCompatActivity {
    private static String url_cek_login 	 = Server.URL + "cekLogin.php";
    public static final String TAG_store_code ="store_code";
    public static final String TAG_PASSWORD ="passoword";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    private EditText txtUserEmail,txtUserPassword,txtKode,txtIp;
    private Button btnLogin;
    Activity context = this;
    private TextView txtRegId,lblLogin;
    String userNip,userPassword,reg_id,IP,staf_nip_baru,userEmail,staf_id,staf_email,staf_name,jabatan_id,unit_id,store_latitude,store_longitude,store_name,store_id,staf_nip_lama,staf_login,staf_kota_lahir,staf_tgl_lahir,staf_alamat,staf_kota,staf_provinsi,staf_telepon,staf_phone,staf_status,staf_foto,staff_bank_account_number,staff_bank_account_name,staf_npwp,staf_ktp_number,staf_kelamin,staf_status_nikah, lokasi_project,nama;
    String tag_json_obj = "json_obj_req";
    int success;
    private static final String TAG = FormLogin.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private AlertDialogMsg msg = new AlertDialogMsg();
    private RequestPermissionHandler mRequestPermissionHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        TextView title=(TextView) findViewById(R.id.title);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Handycheera.otf");
        title.setTypeface(typeface);

        mRequestPermissionHandler = new RequestPermissionHandler();
        handleButtonClicked();


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        staf_nip_baru = sharedpreferences.getString("staf_nip_baru", "");

        txtIp = (EditText) findViewById(R.id.txtIp);

        //get ip
        NetInfo netInfo = new NetInfo(this);
        final String ipAddress = netInfo.getWifiIpAddress();

        txtIp.setText(ipAddress);
        //Log.e(TAG, "Ip Address: " + ipAddress);
        //cek session masih ada atau tidak
        if(!staf_nip_baru.equals("")){
            Intent i = new Intent(FormLogin.this,FormMainMenu.class);
            startActivity(i);
        }else{

            txtUserEmail = (EditText) findViewById(R.id.txtUserEmail);
            txtUserPassword= (EditText) findViewById(R.id.txtUserPassword);
            btnLogin = (Button) findViewById(R.id.btnLogin);
            txtRegId = (TextView) findViewById(R.id.reg_id);


            btnLogin.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                userEmail = txtUserEmail.getText().toString();
                userPassword= txtUserPassword.getText().toString();
                reg_id = txtRegId.getText().toString();
                IP = txtIp.getText().toString();

                cekLogin();

                }

            });

            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    // checking for type intent filter
                    if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                        // gcm successfully registered
                        // now subscribe to `global` topic to receive app wide notifications
                        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                        displayFirebaseRegId();

                    } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                        // new push notification is received
                        String message = intent.getStringExtra("message");
                        Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    }
                }
            };

            displayFirebaseRegId();


        }
    }

    private void handleButtonClicked(){
        mRequestPermissionHandler.requestPermission(this, new String[] {
                android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                //Toast.makeText(FormLogin.this, "request permission success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                //Toast.makeText(FormLogin.this, "request permission failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        //Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText(regId);
        else
            txtRegId.setText("");
    }


    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    public void cekLogin(){
        final ProgressDialog progressDialog = new ProgressDialog(FormLogin.this, R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        StringRequest strReq = new StringRequest(Request.Method.POST, url_cek_login, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        nama = jObj.getString("nama");
                        final String msg = jObj.getString(TAG_MESSAGE);
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        Toast.makeText(FormLogin.this, msg, Toast.LENGTH_LONG).show();

                                        //menyimpan data pada sharedpreferences
                                        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("nama",nama);

                                        editor.commit();

                                        // login berhasil mengirim variabel ke class menu
                                        Intent ni = new Intent(FormLogin.this, FormMainMenu.class);
                                        startActivity(ni);

                                        // onLoginFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 3000);

                    } else {
                        msg.messageDialog(FormLogin.this,"Informasi",jObj.getString(TAG_MESSAGE),"OK", null, null, null);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Error: " + error.getMessage());
                msg.messageDialog(FormLogin.this,"Informasi","Ada masalah "+error.getMessage(),"OK", null, null, null);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_email", userEmail);
                params.put("password", userPassword);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
}
