package chi_software.citybase.ui.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import chi_software.citybase.R;
import chi_software.citybase.interfaces.SetCommentListener;

/**
 * Created by user on 01.03.2017.
 */

public class CommentDialog extends DialogFragment implements View.OnClickListener {

    SetCommentListener setCommentListener;
    private String comment;

    public void show(Activity activity, SetCommentListener setCommentListener, String comment){
        this.setCommentListener = setCommentListener;
        this.comment = comment;
        show(activity.getFragmentManager(),"Comment");
    }

    private View view;
    private EditText mComment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("");
        view = inflater.inflate(R.layout.comment_dialog_layout, null);

        mComment  = (EditText)view.findViewById(R.id.dialogCommentET);
        mComment.setText(comment);
        mComment.setSelection(comment.length());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        view.findViewById(R.id.cancelDialog).setOnClickListener(this);
        view.findViewById(R.id.dialogSave).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelDialog:
                dismiss();
                break;
            case R.id.dialogSave:
                setCommentListener.getBackComment(mComment.getText().toString());
                dismiss();
                break;
        }
    }
}
