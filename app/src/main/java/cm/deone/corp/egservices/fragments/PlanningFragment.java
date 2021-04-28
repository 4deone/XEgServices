package cm.deone.corp.egservices.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cm.deone.corp.egservices.AddTaskActivity;
import cm.deone.corp.egservices.R;
import cm.deone.corp.egservices.SettingsActivity;

public class PlanningFragment extends Fragment {

    private Toolbar toolbar;

    public PlanningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planning, container, false);
        initUI(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.nav_main, menu);
        menu.findItem(R.id.menu_calendar).setVisible(true);
        menu.findItem(R.id.menu_taches).setVisible(true);
        menu.findItem(R.id.menu_parametres).setVisible(true);
        menu.findItem(R.id.menu_apropos).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_taches:
                startActivity(new Intent(getActivity(), AddTaskActivity.class));
                return true;
            case R.id.menu_parametres:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            case R.id.menu_apropos:
                //startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }
}