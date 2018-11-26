package com.example.rauliyrjana.harjoitus7;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by miksa on 2/21/18.
 */

public class AkuArrayAdapter  extends ArrayAdapter<Aku> {
    private final static String TAG="AkuArrayAdapter";
    public AkuArrayAdapter(Context context, int resource, List<Aku> objects) {
        super(context, resource, objects);
        Log.d("nyt", " ollaan taalla adapterin konstruktorissa");
    }

    /**
     * Rakennetaan yhden rivin nÃ¤kymÃ¤ ListView:iin...
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_aku, parent, false);
        }
        TextView numeroTextView = (TextView) convertView.findViewById(R.id.numeroTextView);
        TextView nimiTextView = (TextView) convertView.findViewById(R.id.nimiTextView);
        TextView hankintaTextView = (TextView) convertView.findViewById(R.id.hankintaTextView);
        TextView painosTextView = (TextView) convertView.findViewById(R.id.painosTextView);

        Aku taskukirja=getItem(position);

        numeroTextView.setVisibility(View.VISIBLE);
        numeroTextView.setText(""+taskukirja.getKirjanNumero());
        nimiTextView.setVisibility(View.VISIBLE);
        nimiTextView.setText("" + taskukirja.getKirjanNimi());
        hankintaTextView.setVisibility(View.VISIBLE);
        hankintaTextView.setText("" + taskukirja.getHankinta());
        painosTextView.setVisibility(View.VISIBLE);
        painosTextView.setText(""+ taskukirja.getPainos());



        return convertView;
    }
}
