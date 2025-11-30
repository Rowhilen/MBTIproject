package com.example.mbtiproject;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public final class FirestoreService {
    private FirestoreService() {}
    public static FirebaseFirestore db() { return FirebaseFirestore.getInstance(); }

    public static Task<DocumentReference> createUser(String phone, String pwHash, String nickname) {
        Map<String, Object> data = new HashMap<>();
        data.put("phone", phone);
        data.put("passwordHash", pwHash);
        data.put("nickname", nickname);
        data.put("org", null);
        data.put("klass", null);
        data.put("mbti", null);
        data.put("partnerMbti", null);
        data.put("createdAt", FieldValue.serverTimestamp());
        return db().collection("users").add(data);
    }

    public static Task<QuerySnapshot> getUsersByPhone(String phone) {
        return db().collection("users").whereEqualTo("phone", phone).get();
    }

    public static Task<Void> updateOrgKlass(String docId, String org, String klass) {
        Map<String,Object> up = new HashMap<>();
        up.put("org", org);
        up.put("klass", klass);
        return db().collection("users").document(docId).update(up);
    }

    public static Task<Void> updateMbtiAndPartner(String docId, String mbti, String partner) {
        Map<String,Object> up = new HashMap<>();
        up.put("mbti", mbti);
        up.put("partnerMbti", partner);
        return db().collection("users").document(docId).update(up);
    }

    public static Task<DocumentSnapshot> getUserDoc(String docId) {
        return db().collection("users").document(docId).get();
    }
}
