package wust.student.illnesshepler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Adapters.InvestigationListAdapter;
import wust.student.illnesshepler.Adapters.ThemeAdapter;
import wust.student.illnesshepler.Bean.GetInvaestigationList;
import wust.student.illnesshepler.Utils.GsonUtils;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.StatusBarUtil;

import static org.litepal.LitePalApplication.getContext;

public class InvestigationList extends AppCompatActivity implements InvestigationListAdapter.OnItemClickListener {

    public RecyclerView recyclerView;
    public GetInvaestigationList getInvaestigationList;
    public InvestigationListAdapter investigationListAdapter;
    List<GetInvaestigationList.Item> mlist = new ArrayList<>();
    ActionBar actionBar;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigation_list);

        recyclerView = (RecyclerView) findViewById(R.id.in_recycle);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        drawable = getDrawable(R.color.white);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("问卷调查");
            drawable.setAlpha(0);
            actionBar.setBackgroundDrawable(drawable);
        }

        initlayout();
        getdata();
    }


    private void initlayout() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        investigationListAdapter = new InvestigationListAdapter(mlist);
        investigationListAdapter.setOnItemClickListener(this);  //监听
        recyclerView.setAdapter(investigationListAdapter);
    }

    public void getdata() {
        Httputil.sendOkHttpRequestSurvey_List(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InvestigationList.this, "似乎出了一些问题", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.d("tag123Survey_List",result);
                getInvaestigationList = GsonUtils.handleMessages2(result);
                if (getInvaestigationList != null) {
                    /*SharedPreferences preferences = getSharedPreferences("ServylistInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(getInvaestigationList.data., result);
                    editor.apply();*/
                    mlist.addAll(getInvaestigationList.data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            investigationListAdapter.notifyDataSetChanged();
                        }
                    });
                }

            }
        });
    }

    @Override
    public void OnItemClick(int position) {
        Toast.makeText(getContext(), "posion:" + position + "\n" + "type" + mlist.get(position).intype, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Investigation.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", mlist.get(position).intype + "");
        intent.putExtras(bundle);
        Log.d("test1", "" + mlist.get(position).intype);
        startActivity(intent);
    }
}
