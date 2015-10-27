package ret.novelly.novelly;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter extends ArrayAdapter<String> {
    int layoutID;
    ArrayList<String> titles;
    HashMap<String,String> IDs;
    public Adapter(Context context, int layout, ArrayList<String> titles, HashMap<String,String> ids) {
        super(context,layout,titles);
        this.titles=titles;
        this.layoutID=layout;
        this.IDs=ids;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;

       View rowView =view;
        // Get the data item for this position
        //TODO:error here
        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.mainlisttextbox, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) rowView.findViewById(R.id.listview_bookimage);
            holder.text = (TextView) rowView.findViewById(R.id.listview_texview);
            holder.id = IDs.get(Integer.toString(position));
            holder.text.setText(titles.get(position));
            holder.image.setImageResource(R.drawable.icon);
        }
        else {
           holder = (ViewHolder) rowView.getTag();
        }
            rowView.setTag(holder.id);


        // Return the completed view to render on screen
        return rowView;
    }


    static class ViewHolder {
        ImageView image;
        TextView text;
        String id;
    }
}