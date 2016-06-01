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
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.techmighty.chatapp.EndPoints;
import in.techmighty.chatapp.R;
import in.techmighty.chatapp.adapter.MessageListAdapter;
import in.techmighty.chatapp.application.ApplicationClass;
import in.techmighty.chatapp.custom.CustomJSONVolleyRequest;
import in.techmighty.chatapp.database.DatabaseHelper;
import in.techmighty.chatapp.interfaces.RecycleOnLongClickListner;
import in.techmighty.chatapp.model.ChatModel;
import in.techmighty.chatapp.model.MessageModel;
import in.techmighty.chatapp.model.UserModel;


/**
 * Created by ankit varia on 30/05/16.
 */
public class Chat extends Fragment implements Response.ErrorListener, Response.Listener, RecycleOnLongClickListner {

    private String TAG = Chat.class.getSimpleName();
    private DatabaseHelper databaseHelper;
    private RecyclerView msgRecylerView;
    private List<MessageModel> msgArrayList;
    private EditText inputMessage;
    private Button btnSend;
    private MessageListAdapter msgListAdapter;

    public Chat() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    //Declare All The Elements
    public void init(){
        databaseHelper = DatabaseHelper.getInstance(getContext());
        loadData();
    }

    //Service Call To Load Data
    public void loadData(){
        ApplicationClass.getInstance().addToRequestQueue(generateCustomRequest(null, EndPoints.URL));
    }

    public CustomJSONVolleyRequest generateCustomRequest(JSONObject jsonObject, String url) {

        CustomJSONVolleyRequest ustomVolleyJSONObjectRequest = new CustomJSONVolleyRequest(Request.Method
                .GET, url,
                jsonObject, this, this);

        return ustomVolleyJSONObjectRequest;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        msgRecylerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        msgArrayList = new ArrayList<MessageModel>();
        inputMessage = (EditText) view.findViewById(R.id.message);
        btnSend = (Button) view.findViewById(R.id.btn_send);
        msgListAdapter = new MessageListAdapter(getContext(), msgArrayList, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        msgRecylerView.setLayoutManager(layoutManager);
        msgRecylerView.setItemAnimator(new DefaultItemAnimator());
        msgRecylerView.setAdapter(msgListAdapter);

        return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onResponse(Object response) {
        JSONObject jsonResponse = (JSONObject) response;

        try{
            Log.e(TAG, "Response:- " + jsonResponse.toString(1));

            Gson gson = new Gson();
            ChatModel chatModel = gson.fromJson(jsonResponse.toString(), ChatModel.class);

            databaseHelper.deleteChatHistory(getContext());
            for(MessageModel msgModel : chatModel.getMessageModelList()){
                databaseHelper.insertChatHistory(msgModel);
            }

            ChatModel chatModel1 = databaseHelper.getChatHistory();
            msgArrayList.addAll(chatModel1.getMessageModelList());
            Log.e(TAG, "GET CHAT HISTORY:- " + msgArrayList.size());

            msgListAdapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(MessageModel item, long position) {
        Log.e(TAG, "Msg:- " + item.getName());
        if(item.isFavMsg()) {
            item.setFavMsg(false);
            databaseHelper.updateChatFavStatus(position+1, false);
        }
        else {
            item.setFavMsg(true);
            databaseHelper.updateChatFavStatus(position+1, true);
        }

        msgListAdapter.notifyDataSetChanged();
    }
}
