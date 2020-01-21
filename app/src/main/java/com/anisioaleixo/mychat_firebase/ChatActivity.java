package com.anisioaleixo.mychat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

public class ChatActivity extends AppCompatActivity {


    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat);

        RecyclerView rv = findViewById(R.id.recycler_chat);
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(false));
        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(true));
        adapter.add(new MessageItem(false));

    }

    private class MessageItem extends Item <GroupieViewHolder>{

        private final boolean isLeft;

        private MessageItem(boolean isLeft) {
            this.isLeft = isLeft;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
        }

        @Override
        public int getLayout() {
            return isLeft ? R.layout.item_from_nessager : R.layout.item_to_nessager;
        }
    }


}
