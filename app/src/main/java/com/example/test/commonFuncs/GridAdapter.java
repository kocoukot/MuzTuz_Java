package com.example.test.commonFuncs;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.test.R;

import static android.content.Context.MODE_PRIVATE;

public class GridAdapter extends BaseAdapter {


    private Integer[] images;
    private Integer[] levelsSolvedList;

    private Integer premiaID;
    private Context context;
    private LayoutInflater inflater;
    ImageView lvlSolved;

    public GridAdapter(Context context, Integer[] images, Integer[] levelsSolvedList) {
        this.context = context;
        this.images = images;
        this.levelsSolvedList = levelsSolvedList;
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView = convertView;
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.relative_premia, null);

        }
        ImageView image = gridView.findViewById(R.id.lvlImage);
        lvlSolved = gridView.findViewById(R.id.lvlSolved);

        image.setMaxHeight(100);
        image.setMaxWidth(50);

        image.setImageResource(images[position]);
        if (levelsSolvedList[position] == 1) {
            lvlSolved.setVisibility(View.VISIBLE);
        }
        return gridView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
