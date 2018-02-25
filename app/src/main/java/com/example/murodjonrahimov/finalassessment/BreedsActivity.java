package com.example.murodjonrahimov.finalassessment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import models.DogImage;
import network.DogService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BreedsActivity extends AppCompatActivity implements View.OnClickListener {

  public static final String BREED_NAME_KEY = "breedKey";
  private static final String[] BREEDS_KEY = { "springer", "sheepdog", "retriever", "hound" };
  private ImageView springerIV;
  private ImageView sheepdogIV;
  private ImageView retrieverIV;
  private ImageView houndIV;

  private SharedPreferences sharedPreferences;

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_breeds);

    TextView greeting = findViewById(R.id.greeting);
    springerIV = findViewById(R.id.springer);
    sheepdogIV = findViewById(R.id.sheepdog);
    retrieverIV = findViewById(R.id.retriever);
    houndIV = findViewById(R.id.hound);

    CardView springerCard = findViewById(R.id.springer_card);
    CardView sheepdogCard = findViewById(R.id.sheepdog_card);
    CardView retrieverCard = findViewById(R.id.retriever_card);
    CardView houndCard = findViewById(R.id.hound_card);
    springerCard.setOnClickListener(this);
    sheepdogCard.setOnClickListener(this);
    retrieverCard.setOnClickListener(this);
    houndCard.setOnClickListener(this);

    sharedPreferences = getSharedPreferences(LoginActivity.USER_NAME_KEY, MODE_PRIVATE);
    String savedUsername = sharedPreferences.getString(LoginActivity.PASSWORD_NAME_KEY, "");

    if (TextUtils.isEmpty(savedUsername)) {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      finish();
    }

    greeting.setText("What kind of dog would you like to see, " + savedUsername);

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dog.ceo")
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    DogService service = retrofit.create(DogService.class);

    for (int i = 0; i < BREEDS_KEY.length; i++) {
      final String currentBreed = BREEDS_KEY[i];

      Call<DogImage> call = service.getDogImage(currentBreed);

      call.enqueue(new Callback<DogImage>() {
        @Override
        public void onResponse(Call<DogImage> call, Response<DogImage> response) {
          String image = response.body()
            .getMessage();
          ImageView currentImageView = springerIV;
          if (currentBreed.equals("sheepdog")) {
            currentImageView = sheepdogIV;
          } else if (currentBreed.equals("retriever")) {
            currentImageView = retrieverIV;
          } else if (currentBreed.equals("hound")) {
            currentImageView = houndIV;
          }

          Picasso.with(getApplicationContext())
            .load(image)
            .into(currentImageView);
        }

        @Override
        public void onFailure(Call<DogImage> call, Throwable t) {
          t.getMessage();
        }
      });
    }
  }

  @Override
  public void onClick(View v) {
    String breed = "";
    switch (v.getId()) {
      case R.id.springer_card:
        breed = "springer";
        break;
      case R.id.sheepdog_card:
        breed = "sheepdog";
        break;
      case R.id.retriever_card:
        breed = "retriever";
        break;
      case R.id.hound_card:
        breed = "hound";
        break;
    }

    Intent intent = new Intent(this, DogActivity.class);
    intent.putExtra(BREED_NAME_KEY, breed);
    startActivity(intent);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.logout:
        sharedPreferences.edit()
          .remove(LoginActivity.PASSWORD_NAME_KEY)
          .apply();
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
