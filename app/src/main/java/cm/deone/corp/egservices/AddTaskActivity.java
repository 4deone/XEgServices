package cm.deone.corp.egservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cm.deone.corp.egservices.outils.SaveAppPreferences;
import cm.deone.corp.egservices.outils.Xtache;

import static cm.deone.corp.egservices.outils.Tools.listToTable;
import static cm.deone.corp.egservices.outils.Xconstants.EGEMPLOYER;
import static cm.deone.corp.egservices.outils.Xconstants.XEGSERVICES;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private SaveAppPreferences saveAppPreferences;
    private Xtache xtache;
    private String lang, myUID, idEmployer;
    private EditText edtvTitreTache, edtvDureeTache, edtvDescription;
    private AutoCompleteTextView atvEmployer;
    private ArrayList<String> nomEmployerList;
    private ArrayList<String> idEmployerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xtache = new Xtache(this);
        saveAppPreferences = new SaveAppPreferences(this);
        saveAppPreferences.setAppMode(saveAppPreferences.getModeState());
        lang = saveAppPreferences.getLanguageState();
        saveAppPreferences.setAppLocale(lang);
        setContentView(R.layout.activity_add_task);
        checkUserStatus();
    }

    @Override
    protected void onResume() {
        checkUserStatus();
        saveAppPreferences.setAppMode(saveAppPreferences.getModeState());
        if ( !saveAppPreferences.getLanguageState().equals(lang) ){
            saveAppPreferences.setAppLocale(saveAppPreferences.getLanguageState());
            this.recreate();
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabSave){
            verifSaisie();
        }
    }

    private void checkUserStatus(){
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null){
            myUID = mUser.getUid();
            initUI();
        }else{
            xtache.quitter();
        }
    }

    private void initUI(){
        nomEmployerList = new ArrayList<>();
        idEmployerList = new ArrayList<>();
        edtvDescription = findViewById(R.id.edtvDescription);
        atvEmployer = findViewById(R.id.atvEmployer);
        edtvDureeTache = findViewById(R.id.edtvDureeTache);
        edtvTitreTache = findViewById(R.id.edtvTitreTache);
        findViewById(R.id.fabSave).setOnClickListener(this);
        FirebaseDatabase.getInstance()
                .getReference(XEGSERVICES)
                .child(EGEMPLOYER)
                .addValueEventListener(tousLesEmployers);
    }

    private void verifSaisie(){
        String titre = edtvTitreTache.getText().toString().trim();
        String duree = edtvDureeTache.getText().toString().trim();
        String nomEmployer = atvEmployer.getText().toString().trim();
        String description = edtvDescription.getText().toString().trim();

        if (TextUtils.isEmpty(titre)){
            Toast.makeText(this, getString(R.string.error_saisie_titre),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(duree)){
            Toast.makeText(this, getString(R.string.error_saisie_duree),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nomEmployer)){
            Toast.makeText(this, getString(R.string.error_saisie_employer),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(description)){
            Toast.makeText(this, getString(R.string.error_saisie_description),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        xtache.prepareData(
                ""+myUID,
                ""+titre,
                ""+duree,
                ""+ idEmployer,
                ""+nomEmployer,
                ""+description
        );

    }

    private final ValueEventListener tousLesEmployers = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            nomEmployerList.clear();
            idEmployerList.clear();
            for (DataSnapshot ds : snapshot.getChildren()){
                String emId = ""+ds.child("emId").getValue();
                idEmployerList.add(emId);
                String emNom = ""+ds.child("emNom").getValue();
                nomEmployerList.add(emNom);
            }
            ArrayAdapter<String> employerAdapter = new ArrayAdapter<String>(
                    AddTaskActivity.this,
                    android.R.layout.select_dialog_item,
                    listToTable(nomEmployerList));
            atvEmployer.setThreshold(1);
            atvEmployer.setAdapter(employerAdapter);
            atvEmployer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    idEmployer = idEmployerList.get(position);
                }
            });
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}