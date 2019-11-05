package wust.student.illnesshepler.SurveyQuestions;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;

import wust.student.illnesshepler.Bean.SingleQuestion;
import wust.student.illnesshepler.Investigation;
import wust.student.illnesshepler.R;

public class SingleChoiceFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    public View view;
    public TextView title;
    private TextView puestionPosition;
    private TextView puestionNum;
    private RadioGroup radioGroup;
    private RadioButton mOptiona;
    private RadioButton mOptionb;
    private RadioButton mOptionc;
    private RadioButton mOptiond;
    private RadioButton mOptione;
    private RadioButton mOptionf;
    private RadioButton mOptiong;
    private RadioButton mOptionh;
    private RadioButton mOptioni;
    private RadioButton mOptionj;
    private int position1;
    private int num;
    private SingleQuestion msingleQuestion;

    public static SingleChoiceFragment newInstance(SingleQuestion info, int postion, int num) {
        SingleChoiceFragment fragment = new SingleChoiceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
        bundle.putInt("position", postion);
        bundle.putInt("num", num);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();

        try {
            msingleQuestion = (SingleQuestion) getArguments().getSerializable("info");
            if (msingleQuestion == null)
                throw new NullPointerException();
            position1 = getArguments().getInt("position");
            num = getArguments().getInt("num");
            title.setText(msingleQuestion.title);
            puestionPosition.setText(position1 + "");
            puestionNum.setText("/" + num);

            Investigation.jsonObject.put(position1 + ".", "");
        } catch (NullPointerException | JSONException e) {
            e.printStackTrace();
        }

        if (!msingleQuestion.optiona.equals("")) mOptiona.setText(msingleQuestion.optiona);else{ mOptiona.setVisibility(View.GONE); }
       if (!msingleQuestion.optionb.equals("")) mOptionb.setText(msingleQuestion.optionb);else{ mOptionb.setVisibility(View.GONE); }
       if (!msingleQuestion.optionc.equals("")) mOptionc.setText(msingleQuestion.optionc);else{ mOptionc.setVisibility(View.GONE); }
       if (!msingleQuestion.optiond.equals("")) mOptiond.setText(msingleQuestion.optiond);else{ mOptiond.setVisibility(View.GONE); }
       if (!msingleQuestion.optione.equals("")) mOptione.setText(msingleQuestion.optione);else{ mOptione.setVisibility(View.GONE); }
       if (!msingleQuestion.optionf.equals("")) mOptionf.setText(msingleQuestion.optionf);else{ mOptionf.setVisibility(View.GONE); }
       if (!msingleQuestion.optiong.equals("")) mOptiong.setText(msingleQuestion.optiong);else{ mOptiong.setVisibility(View.GONE); }
       if (!msingleQuestion.optionh.equals("")) mOptionh.setText(msingleQuestion.optionh);else{ mOptionh.setVisibility(View.GONE); }
       if (!msingleQuestion.optioni.equals("")) mOptioni.setText(msingleQuestion.optioni);else{ mOptioni.setVisibility(View.GONE); }
       if (!msingleQuestion.optionj.equals("")) mOptionj.setText(msingleQuestion.optionj);else{ mOptionj.setVisibility(View.GONE); }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_single_choice, container, false);
        return view;
    }

    private void initview() {
        title = view.findViewById(R.id.singgile_title);
        radioGroup = view.findViewById(R.id.radio_grop);
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

        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        restcolor();
        try {
        switch (checkedId) {
            case R.id.optiona:if(msingleQuestion.A_next!=0) { display(msingleQuestion.A_next-1); }else {display(position1); } Investigation.jsonObject.put(position1+".","A");   mOptiona.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optionb:if(msingleQuestion.B_next!=0) { display(msingleQuestion.B_next-1); }else {display(position1); } Investigation.jsonObject.put(position1+".","B");  mOptionb.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optionc:if(msingleQuestion.C_next!=0) { display(msingleQuestion.C_next-1); }else {display(position1); } Investigation.jsonObject.put(position1+".","C");  mOptionc.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optiond:if(msingleQuestion.D_next!=0) { display(msingleQuestion.D_next-1); }else {display(position1); } Investigation.jsonObject.put(position1+".","D");  mOptiond.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optione:if(msingleQuestion.E_next!=0) { display(msingleQuestion.E_next-1); }else {display(position1); } Investigation.jsonObject.put(position1+".","E");  mOptione.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optionf:if(msingleQuestion.F_next!=0) { display(msingleQuestion.F_next-1); }else {display(position1); } Investigation.jsonObject.put(position1+".","F");  mOptionf.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optiong:if(msingleQuestion.G_next!=0) { display(msingleQuestion.G_next-1); }else {display(position1); } Investigation.jsonObject.put(position1+".","G");  mOptiong.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optionh:if(msingleQuestion.H_next!=0) { display(msingleQuestion.H_next-1); }else {display(position1); } Investigation.jsonObject.put(position1+".","H");  mOptionh.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optioni:if(msingleQuestion.I_next!=0) { display(msingleQuestion.I_next-1); }else {display(position1); } Investigation.jsonObject.put(position1+".","I");  mOptioni.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optionj:if(msingleQuestion.J_next!=0) { display(msingleQuestion.J_next-1); }else {display(position1); } Investigation.jsonObject.put(position1+".","J");  mOptionj.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void display(final int position) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Investigation.mViewPager.setCurrentItem(position + Investigation.problemnum);
            }
        }, 500);

    }

    public void restcolor() {
        mOptiona.setBackground(getResources().getDrawable(R.drawable.corner_textview));
        mOptionb.setBackground(getResources().getDrawable(R.drawable.corner_textview));
        mOptionc.setBackground(getResources().getDrawable(R.drawable.corner_textview));
        mOptiond.setBackground(getResources().getDrawable(R.drawable.corner_textview));
        mOptione.setBackground(getResources().getDrawable(R.drawable.corner_textview));
        mOptionf.setBackground(getResources().getDrawable(R.drawable.corner_textview));
        mOptiong.setBackground(getResources().getDrawable(R.drawable.corner_textview));
        mOptionh.setBackground(getResources().getDrawable(R.drawable.corner_textview));
        mOptioni.setBackground(getResources().getDrawable(R.drawable.corner_textview));
        mOptionj.setBackground(getResources().getDrawable(R.drawable.corner_textview));
    }
}
