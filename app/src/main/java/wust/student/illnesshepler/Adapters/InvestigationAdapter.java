package wust.student.illnesshepler.Adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import wust.student.illnesshepler.Bean.BaseQuestion;
import wust.student.illnesshepler.Bean.ManualQuestion;
import wust.student.illnesshepler.Bean.MutipleQuestion;
import wust.student.illnesshepler.Bean.SingleQuestion;
import wust.student.illnesshepler.Fragments.ManualQuestionFragment;
import wust.student.illnesshepler.Fragments.MutipleQuestionFragment;
import wust.student.illnesshepler.Fragments.SingleChoiceFragment;

public class InvestigationAdapter  extends FragmentStatePagerAdapter {
public List<BaseQuestion> mlist;
        BaseQuestion B=new BaseQuestion();
        SingleQuestion S=new SingleQuestion();
        MutipleQuestion M=new MutipleQuestion();
public InvestigationAdapter(@NonNull FragmentManager fm,List<BaseQuestion> mlist) {
        super(fm);
        this.mlist=mlist;
        }

@NonNull
@Override
public Fragment getItem(int position) {

//        Log.d("test",(mlist.get(position) instanceof MutipleQuestion)+"多选");
//        Log.d("test",(mlist.get(position) instanceof SingleQuestion)+"单选");
//        Log.d("test",(mlist.get(position) instanceof ManualQuestion)+"填空");
        if(mlist.get(position) instanceof SingleQuestion) {
                Log.d("test",(mlist.get(position) instanceof SingleQuestion)+"单选");
                return SingleChoiceFragment.newInstance((SingleQuestion) mlist.get(position),position+1,getCount());
        }
        else if(mlist.get(position) instanceof MutipleQuestion)
        {

                Log.d("test",(mlist.get(position) instanceof MutipleQuestion)+"多选");
                return MutipleQuestionFragment.newInstance((MutipleQuestion) mlist.get(position),position+1,getCount());
        }
        else if(mlist.get(position) instanceof ManualQuestion) {
                Log.d("test", (mlist.get(position) instanceof ManualQuestion) + "填空");
                return ManualQuestionFragment.newInstance((ManualQuestion) mlist.get(position), position + 1, getCount());
        }
        return null;
}


@Override
public int getCount() {
        return this.mlist.size();
        }

}
