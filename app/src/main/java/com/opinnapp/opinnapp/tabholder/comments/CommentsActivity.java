package com.opinnapp.opinnapp.tabholder.comments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.opinnapp.opinnapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzaffalon on 09/06/17.
 */

public class CommentsActivity extends SwipeBackActivity {

    private RecyclerView recyclerView;
    private List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
        recyclerView = (RecyclerView) findViewById(R.id.fragment_comments_recycler);
        generateComments();
        mountRecycler();
        setUpToolBar();
        setUpButton();
    }

    private void setUpButton(){
        Button btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_message = (EditText) findViewById(R.id.et_message);
                String message = et_message.getText().toString();
                if(!message.isEmpty()) {
                    comments.add(new Comment(message, "0 min", "https://scontent-gru.xx.fbcdn.net/v/t1.0-9/602740_2839717690184_951506583_n.jpg?oh=9e9cdc3af65826e2db5a4384993a0f60&oe=599FA490"));
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
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
                @Override public void onItemClick(Comment item) {
                }
            }));
        }
    }

    private void generateComments(){
        comments = new ArrayList<>();
        comments.add(new Comment("Gostei muito do tênis 2.","60min","https://scontent-gru.xx.fbcdn.net/v/t1.0-9/602740_2839717690184_951506583_n.jpg?oh=9e9cdc3af65826e2db5a4384993a0f60&oe=599FA490"));
        comments.add(new Comment("Achei o primeiro tênis um pouco feio.","45min","https://scontent-gru.xx.fbcdn.net/v/t1.0-9/17308748_1279495765466119_5902661537556998459_n.jpg?oh=91cd66e6173a2312be22ac33d3ed4445&oe=59A3777D"));
        comments.add(new Comment("Gostei de todos","15min","https://scontent-gru.xx.fbcdn.net/v/t1.0-9/1907867_967496356601123_5817813594957135474_n.jpg?oh=cecab31754466c2419254d03b1933dac&oe=59A63C29"));
        comments.add(new Comment("O vermelho é bom pra jogar basquete.","2min","https://scontent-gru.xx.fbcdn.net/v/t1.0-9/602740_2839717690184_951506583_n.jpg?oh=9e9cdc3af65826e2db5a4384993a0f60&oe=599FA490"));
    }

}
