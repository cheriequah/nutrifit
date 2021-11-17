package com.example.nutrifit;

public class BMI {
    public static final BMI instance = new BMI();

    private static final int centi_to_meter = 100;
    private static final int inches_to_foot = 12;
    private static final int bmi_weight_scalar = 703;


    public static final String bmi_underweight = "Underweight";
    public static final String bmi_healthy = "Healthy";
    public static final String bmi_overweight = "Overweight";
    public static final String bmi_obese = "Obese";

    public static BMI getInstance() {
        return instance;
    }

    public double calcBMIMetric(double heightCm, double weightKg) {
        return (weightKg / ((heightCm / centi_to_meter) * (heightCm / centi_to_meter)));

    }

    public double calcBMIImperial(double heightFeet, double heightInches, double weightLbs) {
        double totalHeightInches = (heightFeet * inches_to_foot) + heightInches;
        return (bmi_weight_scalar * weightLbs) / (totalHeightInches * totalHeightInches);
    }

    public String classifyBMI(double bmi) {
        if (bmi < 18.5) {
            return bmi_underweight;
        } else if (bmi >= 18.5 && bmi < 25) {
            return bmi_healthy;
        } else if (bmi >= 25 && bmi < 30) {
            return bmi_overweight;
        } else {
            return bmi_obese;
        }
    }
}

