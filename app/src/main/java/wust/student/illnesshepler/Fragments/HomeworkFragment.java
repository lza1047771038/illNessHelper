package wust.student.illnesshepler.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.Adapters.HomeWorkAdapter;
import wust.student.illnesshepler.Bean.Data_HomeWork;
import wust.student.illnesshepler.R;

public class HomeworkFragment extends Fragment {
    private HomeWorkAdapter adapter;
    private List<Data_HomeWork> list;
    private RecyclerView recyclerView;
    View view;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_homework, container, false);
        return view;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler);
        initData();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new HomeWorkAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(HomeWorkClickListener);
    }

    private void initData(){
        list =new ArrayList<>();
        list.add(new Data_HomeWork("第一次作业","开发者",false));
        list.add(new Data_HomeWork("第二次作业","开发者",false));
        list.add(new Data_HomeWork("第三次作业","开发者",true));
        list.add(new Data_HomeWork("第四次作业","开发者",true));
        list.add(new Data_HomeWork("第五次作业","开发者",false));
    }

    private HomeWorkAdapter.OnItemClickListener HomeWorkClickListener=new HomeWorkAdapter.OnItemClickListener() {
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
