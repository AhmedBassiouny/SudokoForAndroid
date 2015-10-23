package com.example.ahmad.simplesudoko;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class board extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        BoardFragment fragment = new BoardFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.board_container,
                fragment, "Tag").commit();
    }
}
