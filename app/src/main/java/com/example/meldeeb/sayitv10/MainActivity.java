package com.example.meldeeb.sayitv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meldeeb.sayitv10.model.Message;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText messageEditText;
    private MessagesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureToolbar();

        messageEditText = (EditText)findViewById(R.id.message_edit_text);

        adapter = new MessagesRecyclerAdapter();

        final RecyclerView recycler = (RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);

        insertDummyMessages();

        recycler.setAdapter(adapter);
    }

    public void sendMessage(View view){
        String messageText = messageEditText.getText().toString().trim() + " I LOVE YOU";
        Message newMessage = new Message(messageText, new Date());
        adapter.insertMessage(newMessage);
        messageEditText.setText("");
    }

    private void insertDummyMessages(){
        for (int i = 0; i < 10; i++) {
            Message m = new Message("message " + i, new Date());
            adapter.insertMessage(m);
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }

    public static class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessageViewHolder>{

        private ArrayList<Message> messages = new ArrayList<>();

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
            return new MessageViewHolder(linearLayout);
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {
            Message message = messages.get(position);
            holder.messageTextView.setText(message.getText());
            holder.dateTextView.setText(message.getDate().toString());
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public void insertMessage(Message message){
            messages.add(message);
            notifyDataSetChanged();
        }

    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView messageTextView, dateTextView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = (TextView)itemView.findViewById(R.id.message_text_view);
            dateTextView = (TextView)itemView.findViewById(R.id.message_timestamp);
        }
    }
}
