package wust.student.illnesshepler.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;

import wust.student.illnesshepler.Bean.SingleQuestion;
import wust.student.illnesshepler.Investigation;
import wust.student.illnesshepler.R;

public class SingleChoiceFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    public View view;
    public TextView title;
    public TextView puestionPosition;
    public TextView puestionNum;
    public RadioGroup radioGroup;
    public RadioButton mOptiona;
    public RadioButton mOptionb;
    public RadioButton mOptionc;
    public RadioButton mOptiond;
    public RadioButton mOptione;
    public RadioButton mOptionf;
    public RadioButton mOptiong;
    public RadioButton mOptionh;
    public RadioButton mOptioni;
    public RadioButton mOptionj;
    public int position1;
    public int num;
    public SingleQuestion msingleQuestion;

    public static SingleChoiceFragment newInstance(SingleQuestion info,int postion,int num) {
        SingleChoiceFragment fragment = new SingleChoiceFragment();
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
        initview();

        msingleQuestion = (SingleQuestion) getArguments().getSerializable("info");
        position1=getArguments().getInt("position");
        num=getArguments().getInt("num");
        title.setText(msingleQuestion.title);
        puestionPosition.setText(position1+"");
        puestionNum.setText("/"+num);

        try {
            Investigation.jsonObject.put(position1+".","");
        } catch (JSONException e) {
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

    public void initview() {
        title = (TextView) view.findViewById(R.id.singgile_title);
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
            Log.d("test","sssssssssssssssssssssssssssssssssssssssssss");
        switch (checkedId) {
            case R.id.optiona:if(msingleQuestion.A_next!=0) { display(msingleQuestion.A_next-1);msingleQuestion.A_next=0; }else {display(Investigation.mViewPager.getCurrentItem()+1); } Investigation.jsonObject.put(position1+".","A");   mOptiona.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optionb:if(msingleQuestion.B_next!=0) { display(msingleQuestion.B_next-1);msingleQuestion.B_next=0; }else {display(Investigation.mViewPager.getCurrentItem()+1); } Investigation.jsonObject.put(position1+".","B");  mOptionb.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optionc:if(msingleQuestion.C_next!=0) { display(msingleQuestion.C_next-1);msingleQuestion.C_next=0; }else {display(Investigation.mViewPager.getCurrentItem()+1); } Investigation.jsonObject.put(position1+".","C");  mOptionc.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optiond:if(msingleQuestion.D_next!=0) { display(msingleQuestion.D_next-1);msingleQuestion.D_next=0; }else {display(Investigation.mViewPager.getCurrentItem()+1); } Investigation.jsonObject.put(position1+".","D");  mOptiond.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optione:if(msingleQuestion.E_next!=0) { display(msingleQuestion.E_next-1);msingleQuestion.E_next=0; }else {display(Investigation.mViewPager.getCurrentItem()+1); } Investigation.jsonObject.put(position1+".","E");  mOptione.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optionf:if(msingleQuestion.F_next!=0) { display(msingleQuestion.F_next-1);msingleQuestion.F_next=0; }else {display(Investigation.mViewPager.getCurrentItem()+1); } Investigation.jsonObject.put(position1+".","F");  mOptionf.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optiong:if(msingleQuestion.G_next!=0) { display(msingleQuestion.G_next-1);msingleQuestion.G_next=0; }else {display(Investigation.mViewPager.getCurrentItem()+1); } Investigation.jsonObject.put(position1+".","G");  mOptiong.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optionh:if(msingleQuestion.H_next!=0) { display(msingleQuestion.H_next-1);msingleQuestion.H_next=0; }else {display(Investigation.mViewPager.getCurrentItem()+1); } Investigation.jsonObject.put(position1+".","H");  mOptionh.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optioni:if(msingleQuestion.I_next!=0) { display(msingleQuestion.I_next-1);msingleQuestion.I_next=0; }else {display(Investigation.mViewPager.getCurrentItem()+1); } Investigation.jsonObject.put(position1+".","I");  mOptioni.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
            case R.id.optionj:if(msingleQuestion.J_next!=0) { display(msingleQuestion.J_next-1);msingleQuestion.J_next=0; }else {display(Investigation.mViewPager.getCurrentItem()+1); } Investigation.jsonObject.put(position1+".","J");  mOptionj.setBackground(getResources().getDrawable(R.drawable.selected_corner_textview));break;
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void display(final int position)
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Investigation.mViewPager.setCurrentItem(position);
            }
        },500);

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
