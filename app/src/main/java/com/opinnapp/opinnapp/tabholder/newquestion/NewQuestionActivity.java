package com.opinnapp.opinnapp.tabholder.newquestion;

import android.os.Bundle;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.opinnapp.opinnapp.R;

/**
 * Created by vzaffalon on 03/06/17.
 */

public class NewQuestionActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
    }

}
