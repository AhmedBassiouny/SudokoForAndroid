package com.example.ahmad.simplesudoko;

import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Board3 extends AppCompatActivity implements Button.OnClickListener, Button.OnLongClickListener {
    int easy[][] = new int[][]{{0, 0, 0, 2, 6, 0, 7, 0, 1},
            {6, 8, 0, 0, 7, 0, 0, 9, 0}, {1, 9, 0, 0, 0, 4, 5, 0, 0},
            {8, 2, 0, 1, 0, 0, 0, 4, 0}, {0, 0, 4, 6, 0, 2, 9, 0, 0},
            {0, 5, 0, 0, 0, 3, 0, 2, 8}, {0, 0, 9, 3, 0, 0, 0, 7, 4},
            {0, 4, 0, 0, 5, 0, 0, 3, 6}, {7, 0, 3, 0, 1, 8, 0, 0, 0},};
    Button temp;
    Button wrong;
    List<String> wrongTags = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board3);
        LinearLayout parent = (LinearLayout) findViewById(R.id.board3);
        LinearLayout boardLayout = new LinearLayout(this);
        boardLayout.setTag("boardLayout");
        boardLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2f));
        boardLayout.setOrientation(LinearLayout.VERTICAL);
        parent.addView(boardLayout);
        for (int i = 0; i < 9; i++) {
            LinearLayout rowBoardLayout = new LinearLayout(this);
            rowBoardLayout.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rowBoardLayout.setOrientation(LinearLayout.HORIZONTAL);
            boardLayout.addView(rowBoardLayout);
            if (i == 2 || i == 5 || i == 8) {
                View view = new View(this);
                view.setLayoutParams(new LinearLayout.
                        LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 6));
                view.setBackgroundColor(getResources().getColor(R.color.white));
                boardLayout.addView(view);
            }
            for (int j = 0; j < 9; j++) {
                Button button = new Button(this);
                button.setTextSize(20);
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                if (easy[i][j] != 0) {
                    //button.setClickable(false);
                    button.setTag("solidB," + String.valueOf(i) + "," + String.valueOf(j));
                    button.setText(String.valueOf(easy[i][j]));
                } else {
                    button.setText(" ");
                    button.setOnLongClickListener(this);
                    button.setTag("softB," + String.valueOf(i) + "," + String.valueOf(j));
                }
                button.setOnClickListener(this);
                rowBoardLayout.addView(button);
                if (j == 2 || j == 5) {
                    View view = new View(this);
                    view.setLayoutParams(new LinearLayout.
                            LayoutParams(6, LinearLayout.LayoutParams.MATCH_PARENT));
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                    rowBoardLayout.addView(view);
                }
            }
        }

        LinearLayout numbersButtonsLayout = new LinearLayout(this);
        numbersButtonsLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));
        numbersButtonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        numbersButtonsLayout.setPadding(0, 40, 0, 0);
        boardLayout.addView(numbersButtonsLayout);
        for (int i = 1; i < 10; i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(i));
            button.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            button.setTag("control");
            button.setTextColor(getResources().getColor(R.color.colorPrimary));
            button.setOnClickListener(this);
            numbersButtonsLayout.addView(button);
        }
    }

    @Override
    public void onClick(View v) {
        if (temp != null) {
            temp.getBackground().setColorFilter(null);
        }
        if (wrongTags.size() > 0)
            unhighlightWrong();
        Button button = (Button) v;
        String tag = (String) button.getTag();
        String tagParts[] = tag.split(",");

        if (tagParts[0].equals("control")) {
            if (temp != null) {
                String str = (String) temp.getTag();
                String[] sa = str.split(",");
                String is = sa[1];
                String js = sa[2];
                int i = Integer.valueOf(Integer.valueOf(is));
                int j = Integer.valueOf(Integer.valueOf(js));
                String value = (String) button.getText();
                if (check(i, j, Integer.valueOf(value))) {
                    temp.setText(button.getText());
                    temp.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                temp.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
            } else {
                allNumbers(Integer.valueOf(button.getText().toString()));
            }
        } else {
            temp = null;
            if (tagParts[0].equals("solidB")) {
                allNumbers(Integer.valueOf(button.getText().toString()));
            } else {
                button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
                temp = button;
                if (!button.getText().equals(" ")) {
                    allNumbers(Integer.valueOf(button.getText().toString()));
                }
            }
        }

        if (endGame())
            Toast.makeText(getApplication(), "Hmmmm, not Bad at all, You WIN !!!", Toast.LENGTH_LONG).show();
    }

    boolean check(int x, int y, int v) {
        if (x >= 0 && x < 9 && y >= 0 && y < 9 && v > 0 && v < 10) {
            boolean row = checkRow(x, y, v);
            boolean col = checkCol(x, y, v);
            boolean squ = checkSqu(x, y, v);
            if (row && col && squ) {
                easy[x][y] = v;
            }
            return row && col && squ;
        }
        return false;
    }

    private boolean checkSqu(int x, int y, int v) {
        int row = (x / 3) * 3;
        int col = (y / 3) * 3;
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                if (x != i || y != j)
                    if (easy[i][j] == v) {
                        highlightWrong(i, j);
                        return false;
                    }
            }
        }
        return true;
    }

    private boolean checkCol(int x, int y, int v) {
        for (int i = 0; i < 9; i++) {
            if (i != x)
                if (easy[i][y] == v) {
                    highlightWrong(i, y);
                    return false;
                }
        }
        return true;
    }

    private boolean checkRow(int x, int y, int v) {
        for (int i = 0; i < 9; i++) {
            if (i != y)
                if (easy[x][i] == v) {
                    highlightWrong(x, i);
                    return false;
                }
        }
        return true;
    }

    public void highlightWrong(int x, int i) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.board3);
        String s = String.valueOf(x) + "," + String.valueOf(i);
        wrongTags.add(s);
        Button button = (Button) linearLayout.findViewWithTag("solidB," + s);
        if (button == null)
            button = (Button) linearLayout.findViewWithTag("softB," + s);
        button.getBackground().setColorFilter(new LightingColorFilter(0xFF00FFFF, 0xFFAA0000));
    }

    public void unhighlightWrong() {
        for (int i = 0; i < wrongTags.size(); i++) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.board3);
            Button button = (Button) linearLayout.findViewWithTag("solidB," + wrongTags.get(i));
            if (button == null)
                button = (Button) linearLayout.findViewWithTag("softB," + wrongTags.get(i));
            button.getBackground().setColorFilter(null);
        }
        wrongTags.clear();
    }

    public boolean endGame() {
        for (int i = 0; i < easy.length; i++) {
            for (int j = 0; j < easy.length; j++) {
                if (easy[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void allNumbers(int v) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (easy[i][j] == v) {
                    highlightWrong(i, j);
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Button button = (Button) v;
        String str = (String) button.getTag();
        String[] sa = str.split(",");
        String is = sa[1];
        String js = sa[2];
        int i = Integer.valueOf(Integer.valueOf(is));
        int j = Integer.valueOf(Integer.valueOf(js));
        easy[i][j] = 0;
        button.setText(" ");
        return true;
    }
}
