package cm.deone.corp.egservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import cm.deone.corp.egservices.outils.SaveAppPreferences;
import cm.deone.corp.egservices.outils.Xuser;

import static cm.deone.corp.egservices.outils.Xconstants.EGEMPLOYER;
import static cm.deone.corp.egservices.outils.Xconstants.EN;
import static cm.deone.corp.egservices.outils.Xconstants.FR;
import static cm.deone.corp.egservices.outils.Xconstants.XEGSERVICES;

public class SettingsActivity extends AppCompatActivity
        implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private TextView tvUserPhone,
            tvUserEmail, tvUserPoste,
            tvUserNom, tvUserCni,
            tvUserQuartier;
    private SwitchCompat swChangeLanguage;
    private SwitchCompat swChangeTheme;
    private SaveAppPreferences saveAppPreferences;
    private Xuser xuser;
    private String myUID, myEMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initThemeLanguage();
        setContentView(R.layout.activity_settings);
        checkUserStatus();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvDeconnexion){
            xuser.seDeconnecter();
        }else if (v.getId() == R.id.tvDelete){
            //xuser.showEditProfileDialog(myUID);
        }else if (v.getId() == R.id.fabSave){
            xuser.showEditProfileDialog(myUID);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        if (buttonView.getId() == R.id.swChangeTheme){
            saveAppPreferences.setModeState(isChecked);
            saveAppPreferences.setAppMode(isChecked);
            swChangeTheme.setText(isChecked ?
                    getString(R.string.dark_mode) :
                    getString(R.string.ligth_mode));
        } else if (buttonView.getId() == R.id.swChangeLanguage){
            saveAppPreferences.setLanguageState(isChecked ? FR : EN);
            swChangeLanguage.setText(isChecked ?
                    getString(R.string.francais) :
                    getString(R.string.anglais));
            recreate();
        }
    }

    private void initThemeLanguage() {
        saveAppPreferences = new SaveAppPreferences(this);
        xuser = new Xuser(this);
        saveAppPreferences.setAppMode(saveAppPreferences.getModeState());
        saveAppPreferences.setAppLocale(saveAppPreferences.getLanguageState());
    }

    private void checkUserStatus() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null){
            myUID = mUser.getUid();
            myEMAIL = mUser.getEmail();
            initUI();
            userInformation();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void initUI() {
        swChangeLanguage = findViewById(R.id.swChangeLanguage);
        swChangeTheme = findViewById(R.id.swChangeTheme);
        tvUserPhone = findViewById(R.id.tvUserPhone);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserPoste = findViewById(R.id.tvUserPoste);
        tvUserNom = findViewById(R.id.tvUserNom);
        tvUserCni = findViewById(R.id.tvUserCni);
        tvUserQuartier = findViewById(R.id.tvUserQuartier);

        swChangeLanguage.setChecked(saveAppPreferences.getLanguageState()
                .equals(FR));
        swChangeLanguage.setText(saveAppPreferences.getLanguageState()
                .equals(FR) ? getString(R.string.francais) :
                getString(R.string.anglais));

        swChangeTheme.setChecked(saveAppPreferences.getModeState());
        swChangeTheme.setText(saveAppPreferences.getModeState() ?
                getString(R.string.dark_mode) : getString(R.string.ligth_mode));

        findViewById(R.id.tvDeconnexion).setOnClickListener(this);
        findViewById(R.id.tvDelete).setOnClickListener(this);
        findViewById(R.id.fabSave).setOnClickListener(this);
        swChangeLanguage.setOnCheckedChangeListener(this);
        swChangeTheme.setOnCheckedChangeListener(this);
    }

    private void userInformation() {
        Query query = FirebaseDatabase.getInstance()
                .getReference(XEGSERVICES)
                .child(EGEMPLOYER)
                .orderByKey()
                .equalTo(myUID);
        query.addValueEventListener(valUserInformations);
    }

    private final ValueEventListener valUserInformations = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    updateUI(ds);
                }
            }else{
                xuser.updateEmployee(""+myUID);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(SettingsActivity.this, ""+ error.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void updateUI(DataSnapshot ds) {
        String nom = ""+ds.child("emNom").getValue();
        String telephone = ""+ds.child("emTelephone").getValue();
        String avatar = ""+ds.child("emAvatar").getValue();
        String mail = ""+ds.child("emEmail").getValue();
        String cni = ""+ds.child("emCni").getValue();
        String poste = ""+ds.child("emPoste").getValue();
        String quartier = ""+ds.child("emQuartier").getValue();

        tvUserNom.setText(ds.child("emNom").exists() ? nom :
                getString(R.string.nom_de_l_utilisateur));
        tvUserPhone.setText(ds.child("emTelephone").exists() ? telephone :
                getString(R.string.t_l_phone_de_l_utilisateur));
        tvUserEmail.setText(ds.child("emEmail").exists() ? mail :
                myEMAIL);
        tvUserCni.setText(ds.child("emCni").exists() ? cni :
                getString(R.string.cni_de_l_utilisateur));
        tvUserQuartier.setText(ds.child("emQuartier").exists() ? quartier :
                getString(R.string.quartier_de_l_utilisateur));
        tvUserPoste.setText(ds.child("emPoste").exists() ? poste :
                getString(R.string.poste_de_l_utilisateur));
    }

}