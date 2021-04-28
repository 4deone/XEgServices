package cm.deone.corp.egservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cm.deone.corp.egservices.outils.SaveAppPreferences;
import cm.deone.corp.egservices.outils.Xuser;

public class AccountActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText edtvNom, edtvTelephone,
            edtvCni, edtvAdresseMail,
            edtvMotdepasse, edtvConfirm,
            edtvQuartier;
    private SaveAppPreferences saveAppPreferences;
    private String lang;
    private Xuser xuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        saveAppPreferences = new SaveAppPreferences(this);
        xuser = new Xuser(this);

        saveAppPreferences.setAppMode(saveAppPreferences.getModeState());
        lang = saveAppPreferences.getLanguageState();
        saveAppPreferences.setAppLocale(lang);

        setContentView(R.layout.activity_account);

        checkUserStatus();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvConnexion){
            startActivity(new Intent(AccountActivity.this,
                    MainActivity.class));
            finish();
        }else if (v.getId() == R.id.fabConnexion){
            verifSaisie();
        }
    }

    private void checkUserStatus() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null){
            startActivity(new Intent(AccountActivity.this,
                    MainActivity.class));
            finish();
        }else{
            initUI();
        }
    }

    private void initUI() {
        edtvNom = findViewById(R.id.edtvNom);
        edtvTelephone = findViewById(R.id.edtvTelephone);
        edtvQuartier = findViewById(R.id.edtvQuartier);
        edtvCni = findViewById(R.id.edtvCni);
        edtvAdresseMail = findViewById(R.id.edtvAdresseMail);
        edtvMotdepasse = findViewById(R.id.edtvMotdepasse);
        edtvConfirm = findViewById(R.id.edtvConfirm);

        findViewById(R.id.tvConnexion).setOnClickListener(this);
        findViewById(R.id.fabConnexion).setOnClickListener(this);
    }

    private void verifSaisie() {
        String nom = edtvNom.getText().toString().trim();
        String telephone = edtvTelephone.getText().toString().trim();
        String quartier = edtvQuartier.getText().toString().trim();
        String cni = edtvCni.getText().toString().trim();
        String mail = edtvAdresseMail.getText().toString().trim();
        String motdepasse = edtvMotdepasse.getText().toString().trim();
        String confirm = edtvConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(nom)){
            Toast.makeText(this, getString(R.string.error_saisie_nom),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(telephone)){
            Toast.makeText(this, getString(R.string.error_saisie_telephone),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(quartier)){
            Toast.makeText(this, getString(R.string.error_saisie_quartier),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cni)){
            Toast.makeText(this, getString(R.string.error_saisie_cni),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mail)){
            Toast.makeText(this, getString(R.string.error_saisie_email),
                    Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(motdepasse)){
            Toast.makeText(this, getString(R.string.error_saisie_password),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(confirm) && !confirm.equals(motdepasse)){
            Toast.makeText(this, getString(R.string.error_saisie_confirm),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        xuser.createNewApplicationUser(
                ""+nom,
                ""+telephone,
                ""+quartier,
                ""+cni,
                ""+mail,
                ""+motdepasse
        );
    }
}