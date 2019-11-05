package wust.student.illnesshepler.SurveyQuestions;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import wust.student.illnesshepler.Bean.ManualQuestion;
import wust.student.illnesshepler.Investigation;
import wust.student.illnesshepler.R;


public class ManualQuestionFragment extends Fragment {
    public View view;

    public TextView title;
    public TextView puestionPosition;
    public TextView puestionNum;

    public EditText eAnswer;

    public int position1;
    public int num;

    public ManualQuestion msingleQuestion;

    public ManualQuestionFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_manual_question, container, false);
        return view;
    }

    public static ManualQuestionFragment newInstance(ManualQuestion info, int postion, int num) {
        ManualQuestionFragment fragment = new ManualQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
        bundle.putInt("position",postion);
        bundle.putInt("num",num);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        puestionPosition = view.findViewById(R.id.postion_of_question);
        puestionNum = view.findViewById(R.id.number_of_question);
        eAnswer = view.findViewById(R.id.answer);

        eAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Investigation.jsonObject.put(position1+".",s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        msingleQuestion = (ManualQuestion) getArguments().getSerializable("info");
        position1=getArguments().getInt("position");
        num=getArguments().getInt("num");
        title.setText(msingleQuestion.title);
        puestionPosition.setText(position1+"");
        puestionNum.setText("/"+num);
        title.setText(msingleQuestion.title);


        try {
            Investigation.jsonObject.put(position1+".","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




}
