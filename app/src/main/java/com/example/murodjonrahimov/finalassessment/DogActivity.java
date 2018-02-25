package com.example.murodjonrahimov.finalassessment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import conteiner.DogAdapter;
import java.util.List;
import models.ModelResponce;
import network.DogService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogActivity extends AppCompatActivity {

  private EditText password;
  private EditText email;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dog);

    TextView breedTV = findViewById(R.id.breed);
    email = findViewById(R.id.email);
    password = findViewById(R.id.password);
    final RecyclerView recyclerView = findViewById(R.id.images);
    final DogAdapter adapter = new DogAdapter();
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    String breed = getIntent().getStringExtra(BreedsActivity.BREED_NAME_KEY);
    breedTV.setText(breed);

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dog.ceo")
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    DogService service = retrofit.create(DogService.class);

    Call<ModelResponce> call = service.getDogThumbnails(breed);

    call.enqueue(new Callback<ModelResponce>() {
      @Override
      public void onResponse(Call<ModelResponce> call, Response<ModelResponce> response) {
        List<String> images = response.body().getMessage();
        adapter.setData(images);
        adapter.notifyDataSetChanged();
      }

      @Override
      public void onFailure(Call<ModelResponce> call, Throwable t) {
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.USER_NAME_KEY, MODE_PRIVATE);
    switch (item.getItemId()) {
      case R.id.logout:
        sharedPreferences.edit().remove(LoginActivity.PASSWORD_NAME_KEY).apply();
        finish();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        email.setText("");
        password.setText("");
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}