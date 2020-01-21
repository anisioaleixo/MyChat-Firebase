package com.anisioaleixo.mychat_firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "AATJ";
    private GroupAdapter adapter;
    private User otherUser, myUser;
    private EditText edtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat);

        otherUser = getIntent().getExtras().getParcelable("user");
        getSupportActionBar().setTitle(otherUser.getUserName());

        RecyclerView rv = findViewById(R.id.recycler_chat);

        edtMessage = findViewById(R.id.act_chat_edt_message);
        Button btnChat = findViewById(R.id.act_chat_btn_send);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        adapter = new GroupAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("/users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        myUser = documentSnapshot.toObject(User.class);
                        fetchMessager();
                    }
                });
    }

    private void fetchMessager() {
        if (myUser != null) {
            String fromId = myUser.getUuid();
            String toId = otherUser.getUuid();

            FirebaseFirestore.getInstance().collection("/conversations")
                    .document(fromId)
                    .collection(toId)
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();
                            if (documentChanges != null) {
                                for (DocumentChange doc : documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Message message = doc.getDocument().toObject(Message.class);

                                        adapter.add(new MessageItem(message));
                                    }
                                }
                            }

                        }
                    });

        }

    }

    private void sendMessage() {
        String text = edtMessage.getText().toString();
        edtMessage.setText(null);

        final String fromId = FirebaseAuth.getInstance().getUid();
        final String toId = otherUser.getUuid();

        long timestamp = System.currentTimeMillis();

        final Message message = new Message();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setTimestamp(timestamp);
        message.setText(text);

        if (!message.getText().isEmpty() ) {  //colocar no if     || text.trim().equals("")
            FirebaseFirestore.getInstance().collection("/conversations")
                    .document(fromId)
                    .collection(toId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, e.getMessage());
                        }
                    });
            FirebaseFirestore.getInstance().collection("/conversations")
                    .document(toId)
                    .collection(fromId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, e.getMessage());
                        }
                    });
        }
    }

    private class MessageItem extends Item<GroupieViewHolder> {

        private final Message message;

        private MessageItem(Message message) {
            this.message = message;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView txtMessage = viewHolder.itemView.findViewById(R.id.chat_txt_msg);
            ImageView imgMessage = viewHolder.itemView.findViewById(R.id.chat_img_message);

            txtMessage.setText(message.getText());
            Picasso
                    .get()
                    .load(message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                            ? myUser.getProfileUrl()
                            : otherUser.getProfileUrl())
                    .into(imgMessage);
        }

        @Override
        public int getLayout() {
            return message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                    ? R.layout.item_from_message
                    : R.layout.item_to_message;
        }
    }
}
