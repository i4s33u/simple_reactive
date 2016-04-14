package com.example.quan_bui.simplereactive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainActivity
    extends AppCompatActivity {

    final String stringToParse =
        "[{\"id\":1,\"name\":\"Quan Bui\",\"age\":25},{\"id\":2,\"name\":\"Blah Blah\",\"age\":26},{\"id\":3,\"name\":\"Dr Who\",\"age\":999},{\"id\":4,\"name\":\"Sherlock Holmes\",\"age\":54}]";
    Gson gson = new Gson();
    RecyclerView rv;

    Button btnAdd;
    Button btnShow;
    TextView tvThread;

    List<Person> list = new ArrayList<>();

    PeopleAdapter adapter;

    ExecutorService executor;
    MyUIThread UIThread = new MyUIThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executor = Executors.newSingleThreadExecutor();

        rv = (RecyclerView) findViewById(R.id.rv);
        if (rv != null) {
            rv.setHasFixedSize(true);
        }
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PeopleAdapter(list);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnShow = (Button) findViewById(R.id.btnShow);
        tvThread = (TextView) findViewById(R.id.tvThread);

        btnAdd.setOnClickListener(v -> {

            getDataFromString(stringToParse).subscribeOn(Schedulers.from(executor))
                .observeOn(Schedulers.from(executor))
                .subscribe(new Subscriber<Person>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getApplicationContext(),
                                       "Do in background",
                                       Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Person person) {
                        list.add(person);
                        tvThread.setText("Operate On: " + Thread.currentThread().getName());
                    }
                });
        });

        btnShow.setOnClickListener(v -> {

            Observable.from(list)
                .observeOn(UIThread.getScheduler())
                .subscribe(new Subscriber<Person>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getApplicationContext(),
                                       "Do in UI Thread",
                                       Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Person person) {
                        rv.setAdapter(adapter);
                        tvThread.setText("Operate On: " + Thread.currentThread().getName());
                    }
                });
        });
    }

    public Observable<Person> getDataFromString(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            Observable<Person> personObservable =
                Observable.create(new Observable.OnSubscribe<Person>() {
                    @Override
                    public void call(Subscriber<? super Person> subscriber) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                subscriber.onNext(gson.fromJson(jsonArray.getString(i),
                                                                Person.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            return personObservable;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
