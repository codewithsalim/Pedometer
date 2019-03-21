package com.example.saleemshaikh.walk;

/**
 * Created by saleemshaikh on 02/10/17.
 */

public class SensorFilter {

    private SensorFilter(){}

    public static float sum( float[] array){
        float sum = 0 ;
        for (float f : array){
            sum += f;
        }
        return sum;
    }

    public static float dot( float[] a, float[] b){
        float val = a[0]*b[0] + a[1]*b[1] + a[2]*b[2];
        return val;
    }

    public static float[] cross(float[] arrayA, float[] arrayB) {
        float[] retArray = new float[3];
        retArray[0] = arrayA[1] * arrayB[2] - arrayA[2] * arrayB[1];
        retArray[1] = arrayA[2] * arrayB[0] - arrayA[0] * arrayB[2];
        retArray[2] = arrayA[0] * arrayB[1] - arrayA[1] * arrayB[0];
        return retArray;
    }

    public static float norm(float[] array) {
        float val = 0;
        for (int i = 0; i < array.length; i++) {
            val += array[i] * array[i];
        }
        return (float) Math.sqrt(val);
    }

    public static float[] normalize(float[] a) {
        float[] retval = new float[a.length];
        float norm = norm(a);
        for (int i = 0; i < a.length; i++) {
            retval[i] = a[i] / norm;
        }
        return retval;
    }


}
