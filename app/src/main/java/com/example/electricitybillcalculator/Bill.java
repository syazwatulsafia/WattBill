package com.example.electricitybillcalculator;

import java.io.Serializable;

public class Bill implements Serializable {
    private int id;
    private String month;
    private double units;
    private double rebate;
    private double totalCharges;
    private double finalCost;

    public Bill() {}

    public Bill(String month, double units, double rebate, double totalCharges,
                double finalCost) {
        this.month = month;
        this.units = units;
        this.rebate = rebate;
        this.totalCharges = totalCharges;
        this.finalCost = finalCost;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }

    public double getUnits() {
        return units;
    }
    public void setUnits(double units) {
        this.units = units;
    }

    public double getRebate() {
        return rebate;
    }
    public void setRebate(double rebate) {
        this.rebate = rebate;
    }

    public double getTotalCharges() {
        return totalCharges;
    }
    public void setTotalCharges(double totalCharges) {
        this.totalCharges = totalCharges;
    }

    public double getFinalCost() {
        return finalCost;
    }
    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }
}