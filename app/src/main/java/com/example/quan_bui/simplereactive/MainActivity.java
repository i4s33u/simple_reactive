package com.example.quan_bui.simplereactive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import java.util.concurrent.TimeUnit;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity
    extends AppCompatActivity {

    public static final String END_POINT = "http://jsonplaceholder.typicode.com";

    WebService service;

    RxJavaCallAdapterFactory rxAdapter =
        RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

    Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(END_POINT)
        .addCallAdapterFactory(rxAdapter)
        .build();

    RecyclerView rv;

    Button btnAdd;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.rv);
        if (rv != null) {
            rv.setHasFixedSize(true);
        }
        rv.setLayoutManager(new LinearLayoutManager(this));

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setText("Get Data");

        if (btnReset != null) {
            btnReset.setOnClickListener(v -> {

                btnReset.setText("Reset Data");
                service = retrofit.create(WebService.class);

                service.getPosts()
                    .delay(2,TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(posts -> {
                        rv.setAdapter(new CardAdapter(posts));
                    });
            });
        }
    }
}
