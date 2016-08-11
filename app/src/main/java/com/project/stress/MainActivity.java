package com.project.stress;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.project.stress.models.DataHolder;
import com.project.stress.models.ListElement;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        JSONArray data;
        try {
            JSONObject input = loadJSONFromAsset();

            data = input.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject eleJSON = data.getJSONObject(i);

                ListElement element = new ListElement();
                element.setTitle(eleJSON.getString("title"));
                element.setDescription(eleJSON.getString("description"));


                JSONArray optionsArray = eleJSON.getJSONArray("options");
                String[][] options = new String[optionsArray.length()][2];
                for (int j = 0; j < optionsArray.length(); j++) {
                    JSONObject optionJSON = optionsArray.getJSONObject(j);

                    options[j][0] = optionJSON.getString("opt");
                    options[j][1] = optionJSON.getString("val");
                }

                element.setOptions(options);

                DataHolder.numOfElements++;

                element.setMarked(false);
                element.setSelection(0);

                DataHolder.ratingList.add(element);
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }

        readDataSet();


        EntryListFragment fragment = new EntryListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

    }

    private JSONObject loadJSONFromAsset() {
        JSONObject json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new JSONObject(new String(buffer, "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException je) {
            je.printStackTrace();
        }

        return json;
    }

    private void readDataSet() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("123_dataset.csv")));

            // ignore first line
            reader.readLine();

            DataHolder.dataset = new DefaultDataset();

            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] elements = csvLine.replace("\"", "").split(",");

                double[] arr = new double[60];


                for (int i = 1; i <= 60; i++) {
                    arr[i - 1] = Double.parseDouble(elements[i]);
                }
                Instance instance = new DenseInstance(arr);
                DataHolder.dataset.add(instance);
            }

        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        }
    }

}
