package cm.deone.corp.egservices.outils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import cm.deone.corp.egservices.HomeActivity;
import cm.deone.corp.egservices.MainActivity;
import cm.deone.corp.egservices.R;

import static cm.deone.corp.egservices.outils.Xconstants.EGEMPLOYER;
import static cm.deone.corp.egservices.outils.Xconstants.USERS;
import static cm.deone.corp.egservices.outils.Xconstants.USER_NAME;
import static cm.deone.corp.egservices.outils.Xconstants.USER_PHONE;
import static cm.deone.corp.egservices.outils.Xconstants.XEGSERVICES;

public class Xuser {

    private final Context context;
    private SharedPreferences sharedPreferences;

    public Xuser(
            Context context
    ) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(
                "PREF_APP",
                Context.MODE_PRIVATE
        );
    }

    public void updateUser(
            final String id,
            final String oldName,
            final String quoi
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(quoi.equals(USER_NAME) ?
                ""+context.getResources().getString(R.string.title_update_name) :
                ""+context.getResources().getString(R.string.title_update_phone));
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editText = new EditText(context);
        editText.setText(""+oldName);
        editText.setInputType(quoi.equals(USER_NAME) ?
                InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS :
                InputType.TYPE_CLASS_PHONE);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        builder.setPositiveButton(""+context.getResources().getString(R.string.update),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String value = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)){
                    //progressDialog.show();
                    saveUpdateUserInformations(
                            ""+id,
                            ""+quoi,
                            ""+value
                    );
                }else {
                    Toast.makeText(context, ""+context.getResources().getString(R.string.error_contenu),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton(""+context.getResources().getString(R.string.annuler),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void saveUpdateUserInformations(
            String id,
            String quoi,
            String value
    ) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<String, Object> results = new HashMap<>();
        results.put(quoi.equals(USER_NAME) ? USER_NAME: USER_PHONE, value);
        results.put("uLastUpdate", timestamp);
        FirebaseDatabase.getInstance().getReference(USERS)
                .child(id).updateChildren(results)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //progressDialog.dismiss();
                Toast.makeText(context, ""+context.getResources().getString(R.string.update_ok),
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //progressDialog.dismiss();
                Toast.makeText(context, ""+ e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createNewApplicationUser(
            String nom ,
            String telephone,
            String quartier,
            String cni,
            String email,
            String password
    ){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.create_account));
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    saveNewUserInformations(
                            progressDialog,
                            ""+nom,
                            ""+telephone,
                            ""+cni,
                            ""+quartier
                    );
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context, context.getString(R.string.auth_failed),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, ""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveNewUserInformations(
            final ProgressDialog progressDialog,
            String nom,
            String telephone,
            String cni,
            String quartier
    )  {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            final DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference();
            ref.child(USERS).child(user.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()){
                        String email = user.getEmail();
                        String uid = user.getUid();
                        String timestamp = String.valueOf(System.currentTimeMillis());

                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("uEmail", email);
                        hashMap.put("uId", uid);
                        hashMap.put("uDate", timestamp);
                        hashMap.put("uName", nom);
                        hashMap.put("uPhone", telephone);

                        ref.child(USERS)
                                .child(user.getUid())
                                .setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //String emId = ref.push().getKey();
                                        ref.child(XEGSERVICES)
                                                .child(uid)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                createNewEmployer(
                                                        ref,
                                                        progressDialog,
                                                        ""+uid,
                                                        ""+nom,
                                                        ""+telephone,
                                                        ""+cni,
                                                        ""+quartier,
                                                        ""+uid,
                                                        ""+timestamp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, context.getString(R.string.save_data_error),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(context, context.getString(R.string.save_data_error),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void createNewEmployer(
            final DatabaseReference ref,
            final ProgressDialog progressDialog,
            String emId,
            String nom,
            String telephone,
            String cni,
            String quartier,
            String uid,
            String timestamp
    ) {
        HashMap<Object, String> hashMapEmployee = new HashMap<>();
        hashMapEmployee.put("emId", emId);
        hashMapEmployee.put("emNom", nom);
        hashMapEmployee.put("emTelephone", telephone);
        hashMapEmployee.put("emAvatar", "no avatar");
        hashMapEmployee.put("emCni", cni);
        hashMapEmployee.put("emPoste", "no poste");
        hashMapEmployee.put("emQuartier", quartier);
        hashMapEmployee.put("emUid", uid);
        hashMapEmployee.put("emDate", timestamp);

        ref.child(XEGSERVICES)
                .child(EGEMPLOYER)
                .child(emId)
                .setValue(hashMapEmployee)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(context, context.getString(R.string.create_account_success),
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, context.getString(R.string.save_data_error),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signInApplication(
            String email,
            String password
    ){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.account_connexion));
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(context, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, context.getString(R.string.auth_failed),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showRecoveryPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.recover));

        LinearLayout mLinearLayout = new LinearLayout(context);
        final EditText mEmail = new EditText(context);
        mEmail.setHint(context.getString(R.string.votre_adresse_mail));
        mEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mEmail.setMinEms(16);

        mLinearLayout.addView(mEmail);
        mLinearLayout.setPadding(10,10,10,10);

        builder.setView(mLinearLayout);

        builder.setPositiveButton(context.getString(R.string.valider), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = mEmail.getText().toString().trim();
                beginRecovery(email);
            }
        });

        builder.setNegativeButton(context.getString(R.string.annuler), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void beginRecovery(
            String email
    ) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.envoie_mail));
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            Toast.makeText(context, context.getString(R.string.email_sent),
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, context.getString(R.string.error_email_sent),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, ""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void seDeconnecter(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.logout));
        builder.setPositiveButton(context.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
        builder.setNegativeButton(context.getString(R.string.no),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showEditProfileDialog(String myUID) {
        String[] options = {context.getString(R.string.edit_vatar),
                context.getString(R.string.edit_nom),
                context.getString(R.string.edit_phone),
                context.getString(R.string.edit_cni),
                context.getString(R.string.edit_poste),
                context.getString(R.string.edit_quartier)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.choose_action));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    //showImagePicDialog();
                }else if (which == 1){
                    showUpdateStringDialog("emNom", myUID);
                }else if (which == 2){
                    showUpdateStringDialog("emTelephone", myUID);
                }else if (which == 3){
                    showUpdateStringDialog("emCni", myUID);
                }else if (which == 4){
                    showUpdateStringDialog("emPoste", myUID);
                }else if (which == 5){
                    showUpdateStringDialog("emQuartier", myUID);
                }
            }
        });
        builder.create().show();
    }

    private void showUpdateStringDialog(final String key, String myUID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.update_key, key));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);

        final EditText editText = new EditText(context);
        editText.setHint(context.getString(R.string.enter_key, key));

        switch (key){
            case "emNom":
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "emTelephone":
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "emCni":
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "emPoste":
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "emQuartier":
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            default:
        }

        linearLayout.addView(editText);

        builder.setView(linearLayout);

        builder.setPositiveButton(context.getString(R.string.update),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String value = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)){
                    effectuerLaMiseAjour(
                            ""+key,
                            ""+value,
                            ""+myUID
                    );
                }else {
                    Toast.makeText(context, context.getString(R.string.please_enter_key),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton(context.getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    private void effectuerLaMiseAjour(String key, String value, String myUID) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.update_key, key));
        progressDialog.setCancelable(false);
        progressDialog.show();
        HashMap<String, Object> results = new HashMap<>();
        results.put(key, value);
        FirebaseDatabase.getInstance()
                .getReference(XEGSERVICES)
                .child(EGEMPLOYER)
                .child(myUID)
                .updateChildren(results)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(context, context.getString(R.string.updated), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //effectuerAutresUpdate();
    }

    /*private void effectuerAutresUpdate() {
        if (key.equals("name")){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts");
            Query query = ref.orderByChild("uid").equalTo(uid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        String child = ds.getKey();
                        assert child != null;
                        dataSnapshot.getRef().child(child).child("uName").setValue(value);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //Update name in current users comment on post
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        String child = ds.getKey();
                        if (dataSnapshot.child(child).hasChild("Comments")){
                            String child1 = ""+dataSnapshot.child(child).getKey();
                            Query child2 = FirebaseDatabase.getInstance().getReference("Posts").child(child1).child("Comments").orderByChild("uid").equalTo(uid);
                            child2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds:dataSnapshot.getChildren()){
                                        String child = ds.getKey();
                                        dataSnapshot.getRef().child(child).child("uName").setValue(value);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }*/

    public void updateEmployee(String myUID){
        Query query = FirebaseDatabase.getInstance().getReference(USERS)
                .orderByKey().equalTo(myUID);
        query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            String nom = ""+ds.child("uName").getValue();
                            String avatar = ""+ds.child("uAvatar").getValue();
                            String mail = ""+ds.child("uEmail").getValue();
                            String telephone = ""+ds.child("uPhone").getValue();
                            String timestamp = String.valueOf(System.currentTimeMillis());
                            HashMap<String, Object> results = new HashMap<>();
                            results.put("emId", myUID);
                            results.put("emNom", nom);
                            results.put("emAvatar", "no avatar");
                            results.put("emEmail", mail);
                            results.put("emTelephone", telephone);
                            results.put("emStatus", "no status");
                            results.put("emDate", timestamp);
                            FirebaseDatabase.getInstance()
                                    .getReference(XEGSERVICES)
                                    .child(EGEMPLOYER)
                                    .child(myUID).setValue(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(context, context.getString(R.string.update_id_employee),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, ""+ e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, ""+ error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
