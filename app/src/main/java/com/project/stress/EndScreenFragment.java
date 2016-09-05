package com.project.stress;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.project.stress.models.DataHolder;
import com.project.stress.models.History;
import com.project.stress.util.HistorySQLiteHelper;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import java.util.Date;


public class EndScreenFragment extends Fragment {

    private View view;
    private Button btSubmit;
    private EditText edtName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.next, menu);

        menu.findItem(R.id.menu_next).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;

            case R.id.menu_history:
                HistoryFragment fragment = new HistoryFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(getString(R.string.fragment_history))
                        .commit();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title2));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_end_screen, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        btSubmit = (Button) view.findViewById(R.id.btSubmit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processData();
            }
        });

        edtName = (EditText) view.findViewById(R.id.edtName);
    }

    private void processData() {

        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Processing. Please wait...");
        progress.show();

        Thread thread = new Thread() {
            @Override
            public void run() {
                double[] arr = new double[60];
                for (int i = 0; i < 60; i++) {
                    String[][] options = DataHolder.ratingList.get(i).getOptions();
                    int selection = DataHolder.ratingList.get(i).getSelection();

                    arr[i] = Double.parseDouble(options[selection][1]);

                }
                Instance instance = new DenseInstance(arr);
                DataHolder.dataset.add(instance);

                Clusterer km = new KMeans(3);
                Dataset[] clusters = km.cluster(DataHolder.dataset);

                int cluster = -1;
                for (int i = 0; i < clusters.length; i++) {
                    if (clusters[i].contains(instance)) {
                        cluster = i + 1;
                        break;
                    }
                }
                progress.dismiss();

                HistorySQLiteHelper hsqlh = new HistorySQLiteHelper(getActivity());
                History history = new History();
                history.setName(edtName.getText().toString());
                history.setDate((new Date()).toString());
                history.setCluster(cluster);
                hsqlh.addToHistory(history);
                hsqlh.close();

                showMessage(cluster);
            }
        };
        thread.start();

    }

    private void showMessage(final int cluster) {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                new MaterialDialog.Builder(getActivity())
                        .title("You belong to:")
                        .content("Cluster : " + cluster)
                        .positiveText("Okay")
                        .show();
            }
        });
    }

    private static Snackbar infoSnack(View view, String content) {
        Snackbar snack = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        TextView tv = (TextView) snack.getView().findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);

        return snack;
    }
}
