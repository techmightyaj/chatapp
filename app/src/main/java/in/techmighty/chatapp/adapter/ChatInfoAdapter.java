package in.techmighty.chatapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.techmighty.chatapp.R;
import in.techmighty.chatapp.interfaces.RecycleOnLongClickListner;
import in.techmighty.chatapp.model.MessageModel;
import in.techmighty.chatapp.model.UserModel;

/**
 * Created by loginextuser14 on 31/05/16.
 */
public class ChatInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String TAG = ChatInfoAdapter.class.getSimpleName();

    private List<UserModel> userArrayList;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, totalMsg, favMsg;

        public ViewHolder(View view) {
            super(view);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            totalMsg = (TextView) itemView.findViewById(R.id.user_total_msg);
            favMsg = (TextView) itemView.findViewById(R.id.uset_fav_msg);
        }
    }

    public ChatInfoAdapter(Context mContext, List<UserModel> userArrayList) {

        this.mContext = mContext;
        this.userArrayList = userArrayList;


        Log.e(TAG, "In Adapter");

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_info, parent, false);


        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.e(TAG, "View Holder:- ");
        if((position-1) != -1) {
        UserModel userModel = userArrayList.get(position-1);
        Log.e(TAG, "User Name:- " + userModel.toString());

            ((ViewHolder) holder).userName.setText(userModel.getUserName());
            ((ViewHolder) holder).totalMsg.setText(userModel.getTotalMessages());
            ((ViewHolder) holder).favMsg.setText(userModel.getFavMessages());
        }else{
            ((ViewHolder) holder).userName.setText("User");
            ((ViewHolder) holder).totalMsg.setText("Total");
            ((ViewHolder) holder).favMsg.setText("Fav");
        }
    }


    @Override
    public int getItemCount() {
        return userArrayList.size()+1;
    }
}
