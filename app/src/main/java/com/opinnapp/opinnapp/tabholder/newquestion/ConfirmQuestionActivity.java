package com.opinnapp.opinnapp.tabholder.newquestion;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.opinnapp.opinnapp.R;

/**
 * Created by vzaffalon on 03/06/17.
 */

public class ConfirmQuestionActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_question);
        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
        setUpCancelButton();
    }

    private void setUpCancelButton() {
        ImageButton cancel_button = (ImageButton) findViewById(R.id.cell_cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
