package sanju.com.gitsquare.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import sanju.com.gitsquare.R;
import sanju.com.gitsquare.adapter.ContributorsAdapter;
import sanju.com.gitsquare.controller.ContributionComparator;
import sanju.com.gitsquare.model.Contributor;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    String URL = "https://api.github.com/repos/square/retrofit/contributors";
    ArrayList<Contributor> arrayList;
    ListView lv_items;
    Button btn_filter;
    private SwipeRefreshLayout swipe_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_items = (ListView) findViewById(R.id.lv_items);
        btn_filter = (Button) findViewById(R.id.btn_filter);

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(arrayList, new ContributionComparator());
                setAdapter();
            }
        });

        swipe_view = (SwipeRefreshLayout) findViewById(R.id.swipe_view);
        swipe_view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                getContributors();

            }
        });

        /*Picasso.with(this).load("").transform(new CircleTransform())
                .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).
                networkPolicy(NetworkPolicy.NO_CACHE).into(img_user_profile_pic);*/

        swipe_view.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        getContributors();
    }

    private void setAdapter() {
        ContributorsAdapter contributorsAdapter = new ContributorsAdapter(MainActivity.this, arrayList);
        lv_items.setAdapter(contributorsAdapter);
        contributorsAdapter.notifyDataSetChanged();
    }

    private void getContributors() {
        swipe_view.setRefreshing(true);
        arrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, "Response :" + s);
                        swipe_view.setRefreshing(false);

                        try {
//                            JSONObject object = new JSONObject(s);
                            JSONArray jsonArray = new JSONArray(s);
                            Log.d(TAG, "jsonArray size : " + jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Contributor contributor = new Contributor();
                                String login = jsonObject.getString("login");
                                String avatar_url = jsonObject.getString("avatar_url");
                                String repos_url = jsonObject.getString("repos_url");
                                String contributions = jsonObject.getString("contributions");

                                contributor.setLogin(login);
                                contributor.setAvatar_url(avatar_url);
                                contributor.setRepos_url(repos_url);
                                contributor.setContributions(contributions);
                                Log.d(TAG, "login : " + login + " avatar_url : " +
                                        avatar_url + " repos_url : " + repos_url + " contributions : " + contributions);
                                arrayList.add(contributor);
                            }
                            setAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Something went wrong..!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(MainActivity.this, "Internet not available..! \n either slow Internet...!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(stringRequest);
    }
}
