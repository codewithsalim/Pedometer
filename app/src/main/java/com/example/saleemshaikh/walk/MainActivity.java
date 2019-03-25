package com.example.saleemshaikh.walk;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.LoggingPermission;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    //private LocalDate date;
    private String currdate;
    private TextView tv, history;
    //public static Map<String, Integer> datab = new HashMap<String, Integer>();


    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private Button start, stop;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = DatabaseHandler.getInstance(this);
        //db.add(new StepData("Hello my world", 234));


        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        tv = (TextView) findViewById(R.id.tv);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        history = (TextView) findViewById(R.id.history);



        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(MainActivity.this);
                updateHistory();

            }
        });


        history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {

                //Code here
                openActivity2();


            }

        });

    }

    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);

    }

    public void updateHistory() {


        int temp, value;

        currdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //currdate = "2019/03/15";
        Log.d("Date", currdate);

        StepData sdt = db.getStepData(currdate);

        Log.d("Date", "Before func");
        if (sdt == null){
            Log.d("Date", "Inside if of update history");
            db.add(new StepData(currdate, numSteps));

        }
        else{
            Log.d("Date", "Inside else of update history");
            //temp = db.getStepData(currdate).getSteps();
            temp = sdt.getSteps();
            Log.d("Date","temp is "+ String.valueOf(temp));
            value = temp + numSteps;
            Log.d("Date","Value is "+ String.valueOf(value));
            int see = db.updateStepData(new StepData(currdate, value));
            Log.d("Date", "Update flag is "+String.valueOf(see));

        }
/*
        if (datab.containsKey(currdate)){
            temp = datab.get(currdate);
            value = temp + numSteps;
             val = new Integer(value);
            datab.put(currdate, val);
        }
        else{
            val = new Integer(numSteps);
            datab.put(currdate, val);
        }

        */

        numSteps = 0;

        Toast toast = Toast.makeText(getApplicationContext(),
                "Update history called",
                Toast.LENGTH_SHORT);

        toast.show();

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        tv.setText(TEXT_NUM_STEPS + numSteps);
    }

}
