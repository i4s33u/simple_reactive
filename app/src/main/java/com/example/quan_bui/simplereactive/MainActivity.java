package com.example.quan_bui.simplereactive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity
    extends AppCompatActivity {

    final String stringToParse =
        "[{\"id\":1,\"name\":\"Quan Bui\",\"age\":25},{\"id\":2,\"name\":\"Blah Blah\",\"age\":26},{\"id\":3,\"name\":\"Dr Who\",\"age\":999},{\"id\":4,\"name\":\"Sherlock Holmes\",\"age\":54}]";

    RecyclerView rv;

    Button btnAdd;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new Gson();

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
                try {
                    JSONArray jsonArray = new JSONArray(stringToParse);
                    List<Person> list = new ArrayList<>();
                    List<Person> people = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(gson.fromJson(jsonArray.getString(i), Person.class));
                    }

                    Observable.from(list)
                        .subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
                        .map(people::add).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(person -> rv.setAdapter(new PeopleAdapter(people)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
