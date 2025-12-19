package com.example.electricitybillcalculator;

public class BillCalculator {

    private static final double RATE_1 = 0.218;
    private static final double RATE_2 = 0.334;
    private static final double RATE_3 = 0.516;
    private static final double RATE_4 = 0.546;

    public static BillCalculationResult calculateBill(double units, double
            rebatePercentage) {

        double totalCharges = calculateTariff(units);

        double rebateAmount = totalCharges * (rebatePercentage / 100.0);
        double finalCost = totalCharges - rebateAmount;

        return new BillCalculationResult(totalCharges, finalCost, rebateAmount);
    }

    private static double calculateTariff(double units) {

        double remaining = units;
        double charges = 0;

        // Block 1 (0–200)
        if (remaining > 200) {
            charges += 200 * RATE_1;
            remaining -= 200;
        } else {
            return charges + (remaining * RATE_1);
        }

        // Block 2 (201–300)
        if (remaining > 100) {
            charges += 100 * RATE_2;
            remaining -= 100;
        } else {
            return charges + (remaining * RATE_2);
        }

        // Block 3 (301–600)
        if (remaining > 300) {
            charges += 300 * RATE_3;
            remaining -= 300;
        } else {
            return charges + (remaining * RATE_3);
        }

        // Block 4 (>600)
        return charges + (remaining * RATE_4);
    }

    public static class BillCalculationResult {
        public double totalCharges;
        public double finalCost;
        public double rebateAmount;

        public BillCalculationResult(double totalCharges, double finalCost,
                                     double rebateAmount) {
            this.totalCharges = totalCharges;
            this.finalCost = finalCost;
            this.rebateAmount = rebateAmount;
        }
    }
}