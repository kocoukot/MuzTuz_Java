package com.artline.muztus.commonFuncs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.artline.muztus.R;

public class GridAdapter extends BaseAdapter {


    private Integer[] images;
    private Integer[] levelsSolvedList;

    private Integer premiaID;
    private Context context;
    private ImageView lvlSolved;

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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater != null ? inflater.inflate(R.layout.relative_premia, null) : null;

        }
        ImageView image = gridView.findViewById(R.id.lvlImage);
        lvlSolved = gridView.findViewById(R.id.lvlSolved);

        image.setMaxHeight(100);
        image.setMaxWidth(50);

        image.setImageResource(images[position]);
        if (levelsSolvedList[position] == 1) {
            lvlSolved.setVisibility(View.VISIBLE);
        } else {
            lvlSolved.setVisibility(View.INVISIBLE);
        }
        System.out.println(position);

        System.out.println("test");
        return gridView;
    }


}
