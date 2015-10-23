package com.example.ahmad.simplesudoko;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by ahmad on 13/10/2015.
 */
public class BoardAdapter extends BaseAdapter {

    private final Context context;
    private final int[][] board;

    public BoardAdapter(Context c, int[][] board) {
        context = c;
        this.board = board;
    }

    @Override
    public int getCount() {
        return 9 * 9;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        public TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.board_block, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.block_id);

            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        int x = position / 9;
        int y = position - (9 * x);
        holder.name.setText(String.valueOf(board[x][y]));

        return convertView;
    }
}
