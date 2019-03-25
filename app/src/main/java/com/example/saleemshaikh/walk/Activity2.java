package com.example.saleemshaikh.walk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Activity2 extends AppCompatActivity {

    private TextView title, data;
    public static List<StepData> dblist;
    DatabaseHandler db;
    TableView<String[]> tableView;
    private static final String[] TABLE_HEADERS = { "Date", "Steps" };

    String DATA_TO_SHOW[][] ;

    ArrayList<ArrayList<String>> outer = new ArrayList<ArrayList<String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        db = DatabaseHandler.getInstance(this);

        title = (TextView) findViewById(R.id.historydatatitle);
        data = (TextView) findViewById(R.id.historydata);

        tableView = (TableView<String[]>) findViewById(R.id.tableView);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));


        dblist = db.getAllStepData();




        int i =0;
        for (StepData s: dblist){

            ArrayList<String> inner = new ArrayList<String>();
            inner.add(s.getDate());
            inner.add(String.valueOf(s.getSteps()));

            outer.add(inner);

            /*
            //data.append(s.getDate()+ "  :  "+ s.getSteps() + " steps\n");
            DATA_TO_SHOW[i][0] = s.getDate();
            DATA_TO_SHOW[i][1] = String.valueOf(s.getSteps());
            i++;
            */
        }

        DATA_TO_SHOW = new String[outer.size()][2];

        for(int j =0; j< outer.size(); j++){
            DATA_TO_SHOW[j][0] = outer.get(j).get(0);
            DATA_TO_SHOW[j][1] = outer.get(j).get(1);
        }


        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));


/*
        for (Iterator i = dblist.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            Integer value = (Integer) dblist.get(key);
            data.append(key + " = " + value.toString() + "\n");
        }
*/

    }
}
