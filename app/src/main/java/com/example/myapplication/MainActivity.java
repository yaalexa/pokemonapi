package com.example.myapplication;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.myapplication.api.PokeapiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

   RecyclerView recyclerView;
   ListaPokemonAdapter listaPokemonAdapter;
    Retrofit retrofit;
    ImageView imageView;
    private static final String TAG = "POKEDEX";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //llamado del recyclevie
        recyclerView=findViewById(R.id.card_recycler_view);
        listaPokemonAdapter=new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ////////////////
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        imageView = findViewById(R.id.imagenGlide);
        setImageView();
        obtenerDatos();
    }

    private void obtenerDatos2() {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon();

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {

                if (response.isSuccessful()) { // si la respuesta ha sido generada con exito
                    PokemonRespuesta pokemonRespuesta = response.body(); // aqui tenemos la informacion obtenenida
                    List<Pokemon> listaPokemon = pokemonRespuesta.getResults(); // traemos los resultados y los guardamos en el arraylist generado para tal fin
                    for (int i = 0; i < listaPokemon.size(); i++) {
                        Pokemon p = listaPokemon.get(i);
                        Log.e(TAG, " pokemon: " + p.getName());
                    }
                   listaPokemonAdapter.add((ArrayList<Pokemon>) listaPokemon);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {

                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
    private void setImageView() {
        String url = "https://i1.sndcdn.com/avatars-000487158516-03ypka-t500x500.jpg";
        Glide.with(this)
                .load(url)
                .error(R.drawable.error)
                .placeholder(R.drawable.placeholder_)
                .into(imageView);
    }
public void obtenerDatos() {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon();
        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                if (response.isSuccessful()) {
                    PokemonRespuesta pokemonRespuesta = response.body();
                    List<Pokemon> listaPokemon = pokemonRespuesta.getResults();
                    for (int i = 0; i < listaPokemon.size(); i++) {
                        Pokemon p = listaPokemon.get(i);
                        Log.e(TAG, "pokemon: " + p.getName());
                    }

                    listaPokemonAdapter.add((ArrayList<Pokemon>) listaPokemon);
                }
                else{
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }
}
