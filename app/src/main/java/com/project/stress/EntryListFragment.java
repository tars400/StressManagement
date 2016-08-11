package com.project.stress;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.stress.adapters.ElementListAdapter;
import com.project.stress.models.DataHolder;


public class EntryListFragment extends Fragment {

    private TextView tvProgressStart, tvProgressEnd;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ElementListAdapter notifAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.next, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;

            case R.id.menu_next:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new EndScreenFragment())
                        .addToBackStack(getString(R.string.fragment_history))
                        .commit();
                return true;

            case R.id.menu_history:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new HistoryFragment())
                        .addToBackStack(getString(R.string.fragment_history))
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title1));

        notifAdapter.notifyDataSetChanged();

        tvProgressStart.setText(String.valueOf(DataHolder.numMarked));
        tvProgressEnd.setText(String.valueOf(DataHolder.numOfElements));
        progressBar.setProgress(DataHolder.numMarked);
        progressBar.setMax(DataHolder.numOfElements);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvProgressStart = (TextView) view.findViewById(R.id.tvProgressStart);
        tvProgressEnd = (TextView) view.findViewById(R.id.tvProgressEnd);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        notifAdapter = new ElementListAdapter(getActivity(), DataHolder.ratingList, tvProgressStart, progressBar);
        recyclerView.setAdapter(notifAdapter);
    }
}
