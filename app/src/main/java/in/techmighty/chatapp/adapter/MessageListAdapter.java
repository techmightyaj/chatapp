package in.techmighty.chatapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.techmighty.chatapp.R;
import in.techmighty.chatapp.application.ApplicationClass;
import in.techmighty.chatapp.interfaces.RecycleOnLongClickListner;
import in.techmighty.chatapp.model.MessageModel;

/**
 * Created by ankit varia on 30/05/16.
 */
public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = MessageListAdapter.class.getSimpleName();

    private List<MessageModel> messageArrayList;
    private Context mContext;
    private static String today;
    private int ME = 100;
    private final RecycleOnLongClickListner listener;
    private ImageLoader imageLoader = ApplicationClass.getInstance().getImageLoader();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message, timestamp;
        NetworkImageView thumbNail;

        public ViewHolder(View view) {
            super(view);

            message = (TextView) itemView.findViewById(R.id.message);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);

            if (imageLoader == null)
                imageLoader = ApplicationClass.getInstance().getImageLoader();
            thumbNail = (NetworkImageView) view
                    .findViewById(R.id.networkImageView);
        }

        public void bind(final MessageModel item, final RecycleOnLongClickListner listener, final long position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item, position);
                }
            });
        }
    }

    public MessageListAdapter(Context mContext, List<MessageModel> messageArrayList, RecycleOnLongClickListner listener) {

        this.mContext = mContext;
        this.messageArrayList = messageArrayList;
        this.listener = listener;

        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        Log.e(TAG, "In Adapter");

    }

    @Override
    public int getItemViewType(int position) {
        MessageModel message = messageArrayList.get(position);
        Log.e(TAG, "fav" + message.isFavMsg());
        if (message.isFavMsg()) {
            return ME;
        }

        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        // view type is to identify where to render the chat message
        // left or right
        if (viewType == ME) {
            // self message
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_fav, parent, false);
        } else {
            // others message
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_other, parent, false);
        }


        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.e(TAG, "View Holder:- ");
        MessageModel message = messageArrayList.get(position);
        ((ViewHolder) holder).message.setText(message.getBody());

        String timestamp = getTimeStamp(message.getMsgTime());

        if (message.getName() != null)
            timestamp = message.getName() + ", " + timestamp;

        ((ViewHolder) holder).timestamp.setText(timestamp);

        ((ViewHolder) holder).thumbNail.setImageUrl(message.getImgUrl(), imageLoader);
        ((ViewHolder) holder).bind(messageArrayList.get(position), listener, position);
    }



    public static String getTimeStamp(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String timestamp = "";

        today = today.length() < 2 ? "0" + today : today;

        try {
            Date date = format.parse(dateStr);
            SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
            String dateToday = todayFormat.format(date);
            format = dateToday.equals(today) ? new SimpleDateFormat("hh:mm a") : new SimpleDateFormat("dd LLL, hh:mm a");
            String date1 = format.format(date);
            timestamp = date1.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }
}
