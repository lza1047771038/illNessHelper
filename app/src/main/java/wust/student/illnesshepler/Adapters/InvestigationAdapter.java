package wust.student.illnesshepler.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import wust.student.illnesshepler.Fragments.ToolsFragment;
import wust.student.illnesshepler.SurveyQuestions.BaseQuestion;
import wust.student.illnesshepler.SurveyQuestions.ManualQuestion;
import wust.student.illnesshepler.SurveyQuestions.MutipleQuestion;
import wust.student.illnesshepler.SurveyQuestions.Problem;
import wust.student.illnesshepler.SurveyQuestions.SingleQuestion;
import wust.student.illnesshepler.SurveyQuestions.ManualQuestionFragment;
import wust.student.illnesshepler.SurveyQuestions.MutipleQuestionFragment;
import wust.student.illnesshepler.SurveyQuestions.ScoolInfo;
import wust.student.illnesshepler.SurveyQuestions.SingleChoiceFragment;
import wust.student.illnesshepler.Investigation;

public class InvestigationAdapter extends FragmentStatePagerAdapter {
    private List<BaseQuestion> mlist;
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
        if(mlist!=null) {
            if (mlist.get(position) instanceof SingleQuestion) {
                return SingleChoiceFragment.newInstance((SingleQuestion) mlist.get(position), position + 1 - Investigation.problemnum, getCount() - Investigation.problemnum);
            } else if (mlist.get(position) instanceof MutipleQuestion) {

                return MutipleQuestionFragment.newInstance((MutipleQuestion) mlist.get(position), position + 1 - Investigation.problemnum, getCount() - Investigation.problemnum);
            } else if (mlist.get(position) instanceof ManualQuestion) {
                return ManualQuestionFragment.newInstance((ManualQuestion) mlist.get(position), position + 1 - Investigation.problemnum, getCount() - Investigation.problemnum);
            }
            return ScoolInfo.newInstance((Problem) mlist.get(position));
        }
return ToolsFragment.newInstance();
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    @Override
    public int getCount() {
        return this.mlist.size();
    }

}
