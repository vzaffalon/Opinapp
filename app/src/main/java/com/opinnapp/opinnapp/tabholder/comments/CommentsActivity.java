package com.opinnapp.opinnapp.tabholder.comments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Transaction;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OAComment;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OAFirebaseCallback;
import com.opinnapp.opinnapp.models.OAUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzaffalon on 09/06/17.
 */

public class CommentsActivity extends SwipeBackActivity {

    private RecyclerView recyclerView;
    private List<OAComment> comments;
    private String storyId;
    private String userId;
    private OAUser oaUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
        recyclerView = (RecyclerView) findViewById(R.id.fragment_comments_recycler);
        setUpToolBar();
        setUpButton();
        storyId = getIntent().getStringExtra("storyId");
        userId = getIntent().getStringExtra("userId");
        getCommentsFromFirebase(storyId);
    }


    private void setUpButton(){
        Button btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_message = (EditText) findViewById(R.id.et_message);
                final String message = et_message.getText().toString();
                if(!message.isEmpty()) {
                    hideKeyboard();
                    et_message.setText("");


                    if (!message.isEmpty()) {
                        OADatabase.getUserWithID(userId, new OAFirebaseCallback() {
                            @Override
                            public void onSuccess(Object object) {
                                oaUser = (OAUser) object;
                                comments.add(createComment(message, storyId, oaUser));
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Erro ao carregar comentarios", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private void hideKeyboard(){
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static OAComment createComment(String text, String storyID, OAUser owner) {
        OAComment comment = new OAComment(text, storyID, owner);
        if (OADatabase.createComment(comment))
            return comment;
        else
            return null;
    }

    private void setUpToolBar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Comentarios");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(myToolbar);
    }

    private void mountRecycler() {
        if (comments != null) {
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new CommentsAdapter(comments, getApplicationContext(),new CommentsAdapter.OnItemClickListener() {
                @Override public void onItemClick(OAComment item) {
                }
            }));
        }
    }

    private void getCommentsFromFirebase(String storyId){
        OADatabase.getCommentsWithStoryID(storyId, new OAFirebaseCallback() {
            @Override
            public void onSuccess(Object object) {
                comments = (List<OAComment>) object;

                //gambiarra pra setar os users e comments
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        mountRecycler();
                        if(comments != null) {
                            if (comments.isEmpty()){
                                Toast.makeText(getApplicationContext(),"Escreva o primeiro comentario",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, 1000);
            }

            @Override
            public void onFailure(DatabaseError databaseError) {

            }
        });

    }

}
