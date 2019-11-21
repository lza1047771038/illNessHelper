package wust.student.illnesshepler.Activities.Fragments.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.Adapters.CourseAdapter;
import wust.student.illnesshepler.Adapters.HomeWorkAdapter;
import wust.student.illnesshepler.Bean.Data_course;
import wust.student.illnesshepler.CustomViews.MyRecyclerView;
import wust.student.illnesshepler.R;

public class ClassFragment extends Fragment {

    private CourseAdapter adapter;
    private List<Data_course> list;
    private MyRecyclerView recyclerView;
    View view, statusBarBackground;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_class,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*statusBarBackground = view.findViewById(R.id.statusBarBackground);
        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusBarBackground.setLayoutParams(params);*/

        recyclerView = view.findViewById(R.id.classes);
        recyclerView.setNestedScrollingEnabled(false);
        initData();
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManage);
        adapter = new CourseAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(CourseClickListener);

    }

    private void initData(){
        list =new ArrayList<>();
        list.add(new Data_course("第一次作业","开发者",null));
        list.add(new Data_course("第二次作业","开发者",null));
        list.add(new Data_course("第三次作业","开发者",null));
        list.add(new Data_course("第四次作业","开发者",null));
        list.add(new Data_course("第五次作业","开发者",null));
    }

    private HomeWorkAdapter.OnItemClickListener CourseClickListener=new HomeWorkAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, HomeWorkAdapter.ViewName viewName, int position) {
            switch (v.getId()) {
                case R.id.Title:
                    TextView Title = (TextView) view.findViewById(R.id.Title);
                    Toast.makeText(getContext(), Title.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.Author:
                    TextView Author = (TextView) view.findViewById(R.id.Author);
                    Toast.makeText(getContext(), Author.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getContext(), "你点击了第" + (position+1) + "条item", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

        @Override
        public void onItemLongClick(View v) {

        }
    };
}
