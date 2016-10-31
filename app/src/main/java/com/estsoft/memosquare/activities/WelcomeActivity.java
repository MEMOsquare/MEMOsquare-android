package com.estsoft.memosquare.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.estsoft.memosquare.R;
import com.estsoft.memosquare.database.ServiceGenerator;
import com.estsoft.memosquare.database.TokenService;
import com.estsoft.memosquare.models.FbUserModel;
import com.estsoft.memosquare.utils.PrefUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by sun on 2016-10-27.
 */

public class WelcomeActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private FbUserModel user;
    @BindView(R.id.login_button) LoginButton loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Facebook sdk initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_welcome);
        //Butterknife
        ButterKnife.bind(this);

        // 1.1 If current user exists, go to MainActivity
        if(PrefUtils.getCurrentUser(WelcomeActivity.this) != null){
            Intent homeIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }

    // 1.2 no current user exists
    @Override
    protected void onResume() {
        super.onResume();

        //2. Facebook login callback
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(WelcomeActivity.this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            //2.1 login success: get token & get information from facebook server & send token to backend server
            @Override
            public void onSuccess(final LoginResult result) {

                //get token & get information from facebook server
                GraphRequest request = GraphRequest.newMeRequest(
                        result.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback(){
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Timber.i("response: "+response);
                                try {
                                    //받아온 user정보 sharedPreference에 등록
                                    user = new FbUserModel();
                                    user.setFacebookID(object.getString("id"));
                                    user.setEmail(object.getString("email"));
                                    user.setName(object.getString("name"));
                                    user.setGender(object.getString("gender"));
                                    PrefUtils.setCurrentUser(user,WelcomeActivity.this);

                                    //send token to backend server
                                    //// TODO: 2016-10-31 server한테 성공 시 메세지 달라고 하기 
                                    TokenService service = ServiceGenerator.createService(TokenService.class);
                                    Call<String> call = service.getresult();
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Timber.d("server conn success: "+response.body());
                                        }
                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Timber.d("server conn fail: "+t.getMessage());
                                        }
                                    });

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                finish();
                                //MainActivity로 넘어감
                                Toast.makeText(WelcomeActivity.this,"welcome "+user.getName(),Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            //2.2 Error
            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                finish();}

            //2.3 canceled
            @Override
            public void onCancel() { finish();}
        });
    }

    //Facebook Dialog 떴다가 꺼졌을 때 callback
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    
    //// TODO: 2016-10-31 로그아웃 액티비티도 만들기(언젠가) 
}
