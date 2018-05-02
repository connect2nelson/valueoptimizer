import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueAndWeightOptimizer {


    private static int calculateMaxValue(int[] prices, int[] values, int money, int noOfItems) {

        if (money == 0 || noOfItems == 0)
            return 0;

        if (prices[noOfItems - 1] > money)
            return calculateMaxValue(prices, values, money, noOfItems - 1);

        else {
            return Math.max(calculateMaxValue(prices, values, money, noOfItems - 1),
                    values[noOfItems - 1] + calculateMaxValue(prices, values, money - prices[noOfItems - 1], noOfItems - 1));

        }
    }

    public static int calculateMaxDeviation(Map<Integer, List<Integer>> bagsOfItems) {

        Integer maxWeightsSortedToATruck = bagsOfItems.entrySet().stream().map(e -> {

            List<Integer> items = e.getValue();
            int sum = 0;
            for (Integer item : items) {
                sum += item;
            }
            return sum;
        }).reduce(0, Math::max);


        Integer minWeightsSortedToATruck = bagsOfItems.entrySet().stream().map(e -> {

            List<Integer> items = e.getValue();
            int sum = 0;
            for (Integer item : items) {
                sum += item;
            }
            return sum;
        }).reduce(Integer.MAX_VALUE, Math::min);


        return maxWeightsSortedToATruck - minWeightsSortedToATruck;
    }


    private static int truckLoad(int[] boxes, int boxNo, Map<Integer, List<Integer>> trucks) {

        if (boxNo == -1)
            return calculateMaxDeviation(trucks);

        Integer minMaxDev = null;

        for (Map.Entry entry : trucks.entrySet()) {

            Map<Integer, List<Integer>> tempTrucks = new HashMap<>();

            for (Map.Entry<Integer, List<Integer>> tempTruckEntry : trucks.entrySet()) {

                ArrayList<Integer> tempValues = new ArrayList<>();
                tempValues.addAll(tempTruckEntry.getValue());
                tempTrucks.put(tempTruckEntry.getKey(), tempValues);
            }

            List<Integer> listOfBoxValues = tempTrucks.get(entry.getKey());
            listOfBoxValues.add(boxes[boxNo]);

            int maxDiv = truckLoad(boxes, boxNo - 1, tempTrucks);

            if (minMaxDev == null || minMaxDev > maxDiv) {
                minMaxDev = maxDiv;
            }
        }

        return minMaxDev;
    }

    public static void main(String[] args) {

        int[] prices = {3, 2, 1};
        int[] values = {3, 2, 2};

        int moneyCurrentlyinPosession = 3;

        int[] boxes = {2, 3, 5};

        Map<Integer, List<Integer>> trucks = new HashMap<>();
        trucks.put(0, new ArrayList<>());
        trucks.put(1, new ArrayList<>());

        System.out.println("Min max difference = " + truckLoad(boxes, 2, trucks));

        System.out.println("The max value obtained with the money in hand = " + calculateMaxValue(prices, values, moneyCurrentlyinPosession, prices.length));
        System.out.println("The max value obtained with the money in hand = " + calculateMaxValue(prices, values, moneyCurrentlyinPosession, prices.length));

    }
}
