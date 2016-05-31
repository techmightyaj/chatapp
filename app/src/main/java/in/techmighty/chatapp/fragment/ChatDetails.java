package in.techmighty.chatapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.techmighty.chatapp.R;
import in.techmighty.chatapp.adapter.ChatInfoAdapter;
import in.techmighty.chatapp.database.DatabaseHelper;
import in.techmighty.chatapp.interfaces.UpdateData;
import in.techmighty.chatapp.model.UserModel;

/**
 * Created by ankit varia on 30/05/16.
 */
public class ChatDetails extends Fragment implements UpdateData {

    private String TAG = ChatDetails.class.getSimpleName();
    private DatabaseHelper databaseHelper;
    private List<UserModel> chatInfo;
    private RecyclerView msgRecylerView;
    private ChatInfoAdapter chatInfoAdapter;

    public ChatDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void init() {
        databaseHelper = DatabaseHelper.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_detail, container, false);

        msgRecylerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        chatInfo = new ArrayList<>();
        chatInfoAdapter = new ChatInfoAdapter(getContext(), chatInfo);

        Log.e(TAG, chatInfo.toString());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        msgRecylerView.setLayoutManager(layoutManager);
        msgRecylerView.setItemAnimator(new DefaultItemAnimator());
        msgRecylerView.setAdapter(chatInfoAdapter);

        return view;
    }

    public void update() {
        Log.e(TAG, "Update Called");
        chatInfo.clear();
        chatInfo.addAll(databaseHelper.getUserInfo());
        chatInfoAdapter.notifyDataSetChanged();
    }
}
