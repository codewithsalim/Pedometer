package com.example.saleemshaikh.walk;

/**
 * Created by saleemshaikh on 24/03/19.
 */

public class StepData {
    String date;
    int steps;

    public StepData(){   }
    public StepData( String date, int steps){
        this.date = date;
        this.steps = steps;

    }


    public String getDate(){
        return this.date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public int getSteps(){
        return this.steps;
    }


    public void setSteps(int steps){
        this.steps = steps;
    }


}
