package com.example.proyectooaut2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectooaut2.api.WebServiceOAuth;
import com.example.proyectooaut2.api.WebServiceOAuthApi;
import com.example.proyectooaut2.model.Token;
import com.example.proyectooaut2.shared_pref.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.proyectooaut2.shared_pref.TokenManager.SHARED_PREFERENCES;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btObtenerToken;

    private TokenManager tokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
    }
    private void setUpView() {
        tokenManager = TokenManager.getIntance(getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE));
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btObtenerToken = findViewById(R.id.btObtenerToken);
        btObtenerToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hola", Toast.LENGTH_SHORT).show();
                obtenerToken();
            }
        });
    }
    private void obtenerToken(){
        String authHeader = "Basic " + Base64.encodeToString(("angularapp:1234567").getBytes(), Base64.NO_WRAP);
        Call<Token> call = WebServiceOAuth
                .getInstance()
                .createService(WebServiceOAuthApi.class)
                .obtenerToken(
                        authHeader,
                        etUsername.getText().toString(),
                        etPassword.getText().toString(),
                        "password"
                );

        call.enqueue(new Callback<Token>() {
            Token token = new Token();
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.code()==200){
                    Log.d("TAG1", "Access Token: " + response.body().getAccessToken()
                            +" Refresh Token: " + response.body().getRefreshToken());

                    token = response.body();
                    tokenManager.saveToken(token);
                    //TODO start new Activity
                    //startActivity(new Intent(getApplicationContext(), LogeadoActivity.class));

                }else{
                    Log.d("TAG1", "Error");
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }
}