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

import wust.student.illnesshepler.Bean.Problem;
import wust.student.illnesshepler.Investigation;
import wust.student.illnesshepler.R;


public class ScoolInfo extends Fragment {
    public View view;
    private TextView problem1;
    private TextView problem2;
    private TextView problem3;

    private EditText answer1;
    private EditText answer2;
    private EditText answer3;

    private Problem mproblem;

    public ScoolInfo() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ScoolInfo newInstance(Problem info) {
        ScoolInfo fragment = new ScoolInfo();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        problem1 = view.findViewById(R.id.problem1);
        problem2 = view.findViewById(R.id.problem2);
        problem3 = view.findViewById(R.id.problem3);
        answer1 = view.findViewById(R.id.answer1);
        answer2 = view.findViewById(R.id.answer2);
        answer3 = view.findViewById(R.id.answer3);

        answer1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Investigation.jsonObject.put("problem1", s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        answer2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Investigation.jsonObject.put("problem2", s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        answer3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Investigation.jsonObject.put("problem3", s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mproblem = (Problem) getArguments().getSerializable("info");
        problem1.setText(mproblem.problem1);
        if (!mproblem.problem2.equals("")) problem2.setText(mproblem.problem2);
        else {
            problem2.setVisibility(View.GONE);
            answer2.setVisibility(View.GONE);
        }
        if (!mproblem.problem3.equals("")) problem3.setText(mproblem.problem3);
        else {
            problem3.setVisibility(View.GONE);
            answer3.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scool_info, container, false);
        return view;
    }


}
