package cm.deone.corp.egservices;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cm.deone.corp.egservices.outils.SaveAppPreferences;
import cm.deone.corp.egservices.outils.Xuser;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText edtvAdresseMail, edtvMotdepasse;
    private SaveAppPreferences saveAppPreferences;
    private String lang;
    private Xuser xuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveAppPreferences = new SaveAppPreferences(this);
        xuser = new Xuser(this);

        saveAppPreferences.setAppMode(saveAppPreferences.getModeState());
        /*getDelegate().setLocalNightMode( saveAppPreferences.getModeState() ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);*/

        lang = saveAppPreferences.getLanguageState();
        saveAppPreferences.setAppLocale(lang);
        setContentView(R.layout.activity_main);
        checkUserStatus();
    }

    @Override
    protected void onResume() {
        getDelegate().setLocalNightMode( saveAppPreferences.getModeState() ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        if (!saveAppPreferences.getLanguageState().equals(lang)){
            saveAppPreferences.setAppLocale(saveAppPreferences.getLanguageState());
            recreate();
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fabConnexion){
            verifSaisie();
        } else if(v.getId() == R.id.tvForgetPassword){
            xuser.showRecoveryPasswordDialog();
        } else if(v.getId() == R.id.tvNouveauCompte){
            startActivity(new Intent(MainActivity.this,
                    AccountActivity.class));
            finish();
        }
    }

    private void verifSaisie() {
        String mail = edtvAdresseMail.getText().toString().trim();
        String motdepasse = edtvMotdepasse.getText().toString().trim();

        if (TextUtils.isEmpty(mail)){
            Toast.makeText(this, getString(R.string.error_saisie_email),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(motdepasse)){
            Toast.makeText(this, getString(R.string.error_saisie_password),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        xuser.signInApplication(
                ""+mail,
                ""+motdepasse
        );
    }

    private void checkUserStatus(){
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser != null){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }else{
            initUI();
        }
    }

    private void initUI(){
        edtvAdresseMail = findViewById(R.id.edtvAdresseMail);
        edtvMotdepasse = findViewById(R.id.edtvMotdepasse);

        findViewById(R.id.tvForgetPassword).setOnClickListener(this);
        findViewById(R.id.tvNouveauCompte).setOnClickListener(this);
        findViewById(R.id.fabConnexion).setOnClickListener(this);
    }
}