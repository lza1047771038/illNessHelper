package wust.student.illnesshepler.me_edit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class MyDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("title");
        builder.setMessage("是否修改");
        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyInterface myInterface = (MyInterface)getActivity();
                myInterface.buttonYesClicked();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyInterface myInterface = (MyInterface)getActivity();
                myInterface.buttonNoClicked();
            }
        });

        return builder.create();
    }
}
