package wust.student.illnesshepler.Adapters;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import wust.student.illnesshepler.Bean.BaseQuestion;
import wust.student.illnesshepler.Bean.ManualQuestion;
import wust.student.illnesshepler.Bean.MutipleQuestion;
import wust.student.illnesshepler.Bean.Problem;
import wust.student.illnesshepler.Bean.SingleQuestion;
import wust.student.illnesshepler.Fragments.ManualQuestionFragment;
import wust.student.illnesshepler.Fragments.MutipleQuestionFragment;
import wust.student.illnesshepler.Fragments.ScoolInfo;
import wust.student.illnesshepler.Fragments.SingleChoiceFragment;
import wust.student.illnesshepler.Investigation;

public class InvestigationAdapter extends FragmentStatePagerAdapter {
    public List<BaseQuestion> mlist;
    BaseQuestion B = new BaseQuestion();
    SingleQuestion S = new SingleQuestion();
    MutipleQuestion M = new MutipleQuestion();

    public InvestigationAdapter(@NonNull FragmentManager fm, List<BaseQuestion> mlist) {
        super(fm);
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (mlist.get(position) instanceof SingleQuestion) {
            Log.d("test", (mlist.get(position) instanceof SingleQuestion) + "单选" + position);
            return SingleChoiceFragment.newInstance((SingleQuestion) mlist.get(position), position + 1- Investigation.problemnum, getCount()-Investigation.problemnum);
        } else if (mlist.get(position) instanceof MutipleQuestion) {

            Log.d("test", (mlist.get(position) instanceof MutipleQuestion) + "多选" + position);
            return MutipleQuestionFragment.newInstance((MutipleQuestion) mlist.get(position), position + 1- Investigation.problemnum ,getCount()-Investigation.problemnum);
        } else if (mlist.get(position) instanceof ManualQuestion) {
            Log.d("test", (mlist.get(position) instanceof ManualQuestion) + "填空" + position);
            return ManualQuestionFragment.newInstance((ManualQuestion) mlist.get(position), position + 1- Investigation.problemnum, getCount()-Investigation.problemnum);
        }
        return ScoolInfo.newInstance((Problem) mlist.get(position));

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    @Override
    public int getCount() {
        return this.mlist.size();
    }

}
