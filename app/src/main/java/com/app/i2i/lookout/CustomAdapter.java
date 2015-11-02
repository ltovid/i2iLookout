package com.app.i2i.lookout;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {
    private final Activity _context;
    private final String[] _text;
    private final Integer[] _imageId;

    public CustomAdapter(Activity context, String[] text, Integer[] imageId) {
        super(context, R.layout.list_view, text);
        this._context = context;
        this._text = text;
        this._imageId = imageId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = _context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_view_notices, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        txtTitle.setText(_text[position]);
        imageView.setImageResource(_imageId[position]);

        return rowView;
    }

}
