package cm.deone.corp.egservices.outils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import cm.deone.corp.egservices.HomeActivity;
import cm.deone.corp.egservices.MainActivity;
import cm.deone.corp.egservices.R;
import cm.deone.corp.egservices.models.Employer;
import cm.deone.corp.egservices.models.Tache;

import static cm.deone.corp.egservices.outils.Xconstants.EGTACHE;
import static cm.deone.corp.egservices.outils.Xconstants.XEGSERVICES;

public class Xtache {
    private Context context;

    public Xtache(Context context) {
        this.context = context;
    }

    public void quitter(){
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public void prepareData(
            String myUID,
            String titre,
            String duree,
            String idEmployer,
            String nomEmployer,
            String description
    ){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.create_tache));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String timestamp = String.valueOf(System
                .currentTimeMillis());
        String taId = FirebaseDatabase
                .getInstance()
                .getReference()
                .push()
                .getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(idEmployer, new Employer(idEmployer, nomEmployer));
        Tache tache = new Tache(
                ""+taId,
                ""+titre,
                ""+timestamp,
                ""+duree,
                ""+description,
                "En cours",
                hashMap
        );
        saveTache(progressDialog, tache);
    }

    private void saveTache(final ProgressDialog progressDialog, Tache tache) {
        FirebaseDatabase.getInstance()
                .getReference(XEGSERVICES)
                .child(EGTACHE).setValue(tache)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(context, context.getString(R.string.create_new_task_success),
                                Toast.LENGTH_SHORT).show();
                        ((Activity) context).finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, context.getString(R.string.save_task_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
