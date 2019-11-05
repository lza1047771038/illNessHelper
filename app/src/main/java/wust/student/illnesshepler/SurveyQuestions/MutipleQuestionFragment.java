package wust.student.illnesshepler.SurveyQuestions;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.json.JSONException;

import wust.student.illnesshepler.Investigation;
import wust.student.illnesshepler.R;

public class MutipleQuestionFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    View view;
    private TextView title;
    private TextView puestionPosition;
    private TextView puestionNum;
    private CheckBox mOptiona;
    private CheckBox  mOptionb;
    private CheckBox  mOptionc;
    private CheckBox  mOptiond;
    private CheckBox  mOptione;
    private CheckBox  mOptionf;
    private CheckBox  mOptiong;
    private CheckBox  mOptionh;
    private CheckBox  mOptioni;
    private CheckBox  mOptionj;
    private int position1;
    private int num;
    private MutipleQuestion mMutipleQuestion;


    public MutipleQuestionFragment() {
    }

    public static MutipleQuestionFragment newInstance(MutipleQuestion info,int postion,int num) {
        MutipleQuestionFragment fragment = new MutipleQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
        bundle.putInt("position",postion);
        bundle.putInt("num",num);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_mutiple_question, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        mMutipleQuestion = (MutipleQuestion) getArguments().getSerializable("info");
        position1=getArguments().getInt("position");
        num=getArguments().getInt("num");
        title.setText(mMutipleQuestion.title);
        puestionPosition.setText(position1+"");
        puestionNum.setText("/"+num);

        try {
            Investigation.jsonObject.put(position1+".","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

         if (!mMutipleQuestion.optiona.equals(""))mOptiona.setText(mMutipleQuestion.optiona);else{ mOptiona.setVisibility(View.GONE); }
         if (!mMutipleQuestion.optionb.equals(""))mOptionb.setText(mMutipleQuestion.optionb);else{ mOptionb.setVisibility(View.GONE); }
         if (!mMutipleQuestion.optionc.equals(""))mOptionc.setText(mMutipleQuestion.optionc);else{ mOptionc.setVisibility(View.GONE); }
         if (!mMutipleQuestion.optiond.equals(""))mOptiond.setText(mMutipleQuestion.optiond);else{ mOptiond.setVisibility(View.GONE); }
         if (!mMutipleQuestion.optione.equals(""))mOptione.setText(mMutipleQuestion.optione);else{ mOptione.setVisibility(View.GONE); }
         if (!mMutipleQuestion.optionf.equals(""))mOptionf.setText(mMutipleQuestion.optionf);else{ mOptionf.setVisibility(View.GONE); }
         if (!mMutipleQuestion.optiong.equals(""))mOptiong.setText(mMutipleQuestion.optiong);else{ mOptiong.setVisibility(View.GONE); }
         if (!mMutipleQuestion.optionh.equals(""))mOptionh.setText(mMutipleQuestion.optionh);else{ mOptionh.setVisibility(View.GONE); }
         if (!mMutipleQuestion.optioni.equals(""))mOptioni.setText(mMutipleQuestion.optioni);else{ mOptioni.setVisibility(View.GONE); }
         if (!mMutipleQuestion.optionj.equals(""))mOptionj.setText(mMutipleQuestion.optionj);else{ mOptionj.setVisibility(View.GONE); }


        mOptiona.setOnCheckedChangeListener(this);
        mOptionb.setOnCheckedChangeListener(this);
        mOptionc.setOnCheckedChangeListener(this);
        mOptiond.setOnCheckedChangeListener(this);
        mOptione.setOnCheckedChangeListener(this);
        mOptionf.setOnCheckedChangeListener(this);
        mOptiong.setOnCheckedChangeListener(this);
        mOptionh.setOnCheckedChangeListener(this);
        mOptioni.setOnCheckedChangeListener(this);
        mOptionj.setOnCheckedChangeListener(this);
    }



    public  void initview()
    {
        title = (TextView) view.findViewById(R.id.singgile_title);
        mOptiona = view.findViewById(R.id.optiona);
        mOptionb = view.findViewById(R.id.optionb);
        mOptionc = view.findViewById(R.id.optionc);
        mOptiond = view.findViewById(R.id.optiond);
        mOptione = view.findViewById(R.id.optione);
        mOptionf = view.findViewById(R.id.optionf);
        mOptiong = view.findViewById(R.id.optiong);
        mOptionh = view.findViewById(R.id.optionh);
        mOptioni = view.findViewById(R.id.optioni);
        mOptionj = view.findViewById(R.id.optionj);
        puestionPosition = view.findViewById(R.id.postion_of_question);
        puestionNum = view.findViewById(R.id.number_of_question);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId())
        {
            case R.id.optiona : if(isChecked){mOptiona.setBackground(getActivity().getDrawable(R.drawable.selected_corner_textview)); }else {mOptiona.setBackground(getActivity().getDrawable(R.drawable.corner_textview)); }     break;
            case R.id.optionb : if(isChecked){mOptionb.setBackground(getActivity().getDrawable(R.drawable.selected_corner_textview)); }else {mOptionb.setBackground(getActivity().getDrawable(R.drawable.corner_textview)); }     break;
            case R.id.optionc : if(isChecked){mOptionc.setBackground(getActivity().getDrawable(R.drawable.selected_corner_textview)); }else {mOptionc.setBackground(getActivity().getDrawable(R.drawable.corner_textview)); }     break;
            case R.id.optiond : if(isChecked){mOptiond.setBackground(getActivity().getDrawable(R.drawable.selected_corner_textview)); }else {mOptiond.setBackground(getActivity().getDrawable(R.drawable.corner_textview)); }     break;
            case R.id.optione : if(isChecked){mOptione.setBackground(getActivity().getDrawable(R.drawable.selected_corner_textview)); }else {mOptione.setBackground(getActivity().getDrawable(R.drawable.corner_textview)); }     break;
            case R.id.optionf : if(isChecked){mOptionf.setBackground(getActivity().getDrawable(R.drawable.selected_corner_textview)); }else {mOptionf.setBackground(getActivity().getDrawable(R.drawable.corner_textview)); }     break;
            case R.id.optiong : if(isChecked){mOptiong.setBackground(getActivity().getDrawable(R.drawable.selected_corner_textview)); }else {mOptiong.setBackground(getActivity().getDrawable(R.drawable.corner_textview)); }     break;
            case R.id.optionh : if(isChecked){mOptionh.setBackground(getActivity().getDrawable(R.drawable.selected_corner_textview)); }else {mOptionh.setBackground(getActivity().getDrawable(R.drawable.corner_textview)); }     break;
            case R.id.optioni : if(isChecked){mOptioni.setBackground(getActivity().getDrawable(R.drawable.selected_corner_textview)); }else {mOptioni.setBackground(getActivity().getDrawable(R.drawable.corner_textview)); }     break;
            case R.id.optionj : if(isChecked){mOptionj.setBackground(getActivity().getDrawable(R.drawable.selected_corner_textview)); }else {mOptionj.setBackground(getActivity().getDrawable(R.drawable.corner_textview)); }     break;
        }
        check();
    }

    public  void check()
    {
                String checkdAnswer="";
                if(mOptiona.isChecked())  checkdAnswer=checkdAnswer+"A,";
                if(mOptionb.isChecked())  checkdAnswer=checkdAnswer+"B,";
                if(mOptionc.isChecked())  checkdAnswer=checkdAnswer+"C,";
                if(mOptiond.isChecked())  checkdAnswer=checkdAnswer+"D,";
                if(mOptione.isChecked())  checkdAnswer=checkdAnswer+"E,";
                if(mOptionf.isChecked())  checkdAnswer=checkdAnswer+"F,";
                if(mOptiong.isChecked())  checkdAnswer=checkdAnswer+"G,";
                if(mOptionh.isChecked())  checkdAnswer=checkdAnswer+"H,";
                if(mOptioni.isChecked())  checkdAnswer=checkdAnswer+"I,";
                if(mOptionj.isChecked())  checkdAnswer=checkdAnswer+"J,";

                if(checkdAnswer.length()!=0) {
                    checkdAnswer = checkdAnswer.substring(0, checkdAnswer.length() - 1);

                    try {
                        Investigation.jsonObject.put(position1 + ".", checkdAnswer);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
    }


}
