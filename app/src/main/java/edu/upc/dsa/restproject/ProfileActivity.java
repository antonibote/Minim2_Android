package edu.upc.dsa.restproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.restproject.models.Insignias;
import edu.upc.dsa.restproject.models.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements RecyclerClickViewListener {

    Api APIservice;
    String idUser;
    String name;
    String image;
    public List<Insignias> userBadges;
    private RecyclerViewAdapterBadges adapterBadges;
    private RecyclerView recyclerViewBadges;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.getIdUser();

        recyclerViewBadges = findViewById(R.id.recyclerviewbadge);
        recyclerViewBadges.setLayoutManager(new LinearLayoutManager(this));

        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Insignias>> call = APIservice.getBadges(this.idUser);
        try {
            adapterBadges = new RecyclerViewAdapterBadges(call.execute().body(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerViewBadges.setAdapter(adapterBadges);
    }
    public void returnFunction(View view) {
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void getIdUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("idUser", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("idUser", null);
    }
    @Override
    public void recyclerViewListClicked(int position) {

    }

}
