package cm.deone.corp.egservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cm.deone.corp.egservices.fragments.DevisFragment;
import cm.deone.corp.egservices.fragments.PlanningFragment;
import cm.deone.corp.egservices.outils.SaveAppPreferences;

public class HomeActivity extends AppCompatActivity {

    private SaveAppPreferences saveAppPreferences;
    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saveAppPreferences = new SaveAppPreferences(this);
        saveAppPreferences.setAppMode(saveAppPreferences.getModeState());
        lang = saveAppPreferences.getLanguageState();
        saveAppPreferences.setAppLocale(lang);

        setContentView(R.layout.activity_home);

        checkUserStatus();
    }

    @Override
    protected void onResume() {
        saveAppPreferences.setAppMode(saveAppPreferences.getModeState());
        if ( !saveAppPreferences.getLanguageState().equals(lang) ){
            saveAppPreferences.setAppLocale(saveAppPreferences.getLanguageState());
            this.recreate();
        }
        super.onResume();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.menu_planning:
                    showFragment(new PlanningFragment());
                    return true;
                case R.id.menu_devis:
                    showFragment(new DevisFragment());
                    return true;
            }
            return false;
        }
    };

    private void showFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.contentFl, fragment, "");
        fragmentTransaction2.commit();
    }

    private void checkUserStatus() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null){
            initUI();
        }else{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void initUI(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigateBnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);
        showFragment(new PlanningFragment());
    }

}