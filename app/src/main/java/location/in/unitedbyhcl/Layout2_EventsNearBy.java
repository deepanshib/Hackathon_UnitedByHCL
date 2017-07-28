package location.in.unitedbyhcl;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Dell on 7/20/2017.
 *
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class Layout2_EventsNearBy extends android.support.v4.app.Fragment {
    View myview;
    static ArrayList<events> eventses = new ArrayList<>();
    //events e = new events("Google Android","Workshop","Auditorium","15-06-2017","13:00","@mipmap/ic_launcher_round","www.google.com","Hcl",100,3);
    EventAdapter eventAdapter = new EventAdapter();

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.eventsnearby_layout2, container, false);
        RecyclerView mrecyclerview = myview.findViewById(R.id.rl_layout);
        LinearLayoutManager mLayout = new LinearLayoutManager(getActivity());

        for (int i=0;i<eventses.size();i++) {
            mrecyclerview.setHasFixedSize(true);
            mrecyclerview.setAdapter(this.eventAdapter);
            mrecyclerview.setLayoutManager(mLayout);
        }
        return myview;

    }


    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

        public events getItem(int i) {
            return eventses.get(i);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View eventlayoutview = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, null);
            RecyclerView.ViewHolder viewholder;
            viewholder = new ViewHolder(eventlayoutview);
            return (ViewHolder) viewholder;
        }

        @RequiresApi(api = N)

        public void onBindViewHolder(ViewHolder holder, int position) {
            events thisevent = (events) getItem(position);

            holder.txtname.setText(thisevent.getName());
            holder.txtinfo.setText(thisevent.getinfo());
            holder.txtdate.setText((CharSequence) thisevent.getDate());
            holder.img.setImageDrawable(Drawable.createFromPath(thisevent.getImg()));
        }

        @Override
        public int getItemCount() {
            return eventses.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txtname;
            public TextView txtinfo;
            public TextView txtdate;
            public ImageButton img;

            public ViewHolder(View eventlayoutview) {
                super(eventlayoutview);
                txtname = (TextView) eventlayoutview.findViewById(R.id.event_name);
                txtdate = (TextView) eventlayoutview.findViewById(R.id.date);
                txtinfo = (TextView) eventlayoutview.findViewById(R.id.event_info);
                img = (ImageButton) eventlayoutview.findViewById(R.id.imageButton);
            }
        }
    }
}

