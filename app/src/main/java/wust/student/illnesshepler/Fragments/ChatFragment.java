package wust.student.illnesshepler.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Adapters.ThemeAdapter;
import wust.student.illnesshepler.Bean.GetTheme;
import wust.student.illnesshepler.Bean.Posting;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.GsonUtils;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.StatusBarUtil;


public class ChatFragment extends Fragment implements ThemeAdapter.OnItemClickListener {

    private GetTheme themeInfo;

    private View view;
    private RecyclerView recyclerView;
    private ThemeAdapter themeAdapter;
    private SwipeRefreshLayout refreshLayout;

    List<Posting> themeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_chat, container, false);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View statusBarBackground = view.findViewById(R.id.statusBarBackground);
        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusBarBackground.setLayoutParams(params);


        recyclerView = view.findViewById(R.id.chat_recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        layoutInit();

        SharedPreferences preferences = getActivity().getSharedPreferences("themeInfo", Context.MODE_PRIVATE);
        String cache = preferences.getString("ThemeCache", null);
        if (cache != null) {
            themeInfo = GsonUtils.handleMessages(cache);
            if (themeInfo != null) {
                themeList.addAll(0,themeInfo.data);
                updateRecyclerView();
            }
        }

        Posting posting = new Posting();
        posting.author_id = BigInteger.TEN;
        posting.comments_num = 2;
        posting.contains = "哈哈哈哈哈哈哈\n哈哈哈哈\n";
        posting.likes = 3;
        posting.theme_id = "1234567890";
        posting.time = "1234567890";

        themeList.add(posting);

    }

    /**
     * 动态刷新加载RecyclerView
     * 这个能解决notifystatechanged不加载动画的问题
     */
    private void updateRecyclerView() {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return themeList.size();
            }

            @Override
            public int getNewListSize() {
                return 0;
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return false;
            }
        }, false);
        diffResult.dispatchUpdatesTo(themeAdapter);

        Log.d("tag123sizeofthemelist", themeList.size() + "");
    }

    /**
     * 控件的设置以及监听器的设置
     */

    private void layoutInit() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        themeAdapter = new ThemeAdapter(themeList);
        themeAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(themeAdapter);
        //占用空间，用空间换取性能，提高滑动流畅性
        recyclerView.setItemViewCacheSize(50);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setHasFixedSize(true);
        recyclerView.getItemAnimator().setAddDuration(200);
        recyclerView.getItemAnimator().setRemoveDuration(200);
        recyclerView.getItemAnimator().setMoveDuration(200);
        recyclerView.getItemAnimator().setChangeDuration(200);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                refreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestThemes();
            }
        });
    }

    /**
     * 向服务器请求主题信息
     * http://192.168.1.100:8080/theme_request
     * Edit by Lza
     */
    private void requestThemes() {
        Httputil.sendOKHttpRequest(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "似乎出现了一些问题,无法连接至服务器", Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.d("tag123request", result);
                themeInfo = GsonUtils.handleMessages(result);
                if (themeInfo != null) {
                    SharedPreferences preferences = getActivity().getSharedPreferences("themeInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("ThemeCache", result);
                    editor.apply();
                    themeList.addAll(0, themeInfo.data);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "已为你加载8条新内容", Toast.LENGTH_SHORT).show();
                            updateRecyclerView();
                            refreshLayout.setRefreshing(false);
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "似乎出现了一些问题", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void OnItemClick(View view, int position) {
        switch (view.getId())
        {
            case R.id.comments_area:
                Toast.makeText(getContext(),"点击了评论:" + position,Toast.LENGTH_SHORT).show();
                break;
            case R.id.shares_area:
                Toast.makeText(getContext(),"点击了分享:" + position,Toast.LENGTH_SHORT).show();
                break;
            case R.id.likes_area:
                Toast.makeText(getContext(),"点击了点赞:" + position,Toast.LENGTH_SHORT).show();
                break;
            case R.id.hole_area:
                Toast.makeText(getContext(),"整个item:" + position,Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(),"点击了item:" + position,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
