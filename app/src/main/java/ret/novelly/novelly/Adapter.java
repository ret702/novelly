package ret.novelly.novelly;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Adapter extends ArrayAdapter<String> {

    HashMap<String,String> IDs;
    public Adapter(Context context, int layout, ArrayList<String> titles) {

        super(context,layout,titles);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        // Get the data item for this position
        //TODO:error here
        if (view == null) {
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.listview_bookimage);
            holder.text = (TextView) view.findViewById(R.id.listview_texview);
            holder.id = IDs.get(position);
            view.setTag(holder);

        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        // Return the completed view to render on screen
        return view;
    }


    protected  void setViewIDs(HashMap ids)
    {
        this.IDs=ids;
    }
    static class ViewHolder {
        ImageView image;
        TextView text;
        String id;
    }
}