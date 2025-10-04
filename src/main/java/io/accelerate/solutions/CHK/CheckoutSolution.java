package io.accelerate.solutions.CHK;


import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {
    public Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) {
            return -1;
        }

        Map<Character, Integer> priceMap = new HashMap<>();
        priceMap.put('A', 50);
        priceMap.put('B', 30);
        priceMap.put('C', 20);
        priceMap.put('D', 15);

        Map<Character, int[]> offerMap = new HashMap<>();
        offerMap.put('A', new int[]{3, 130});
        offerMap.put('B', new int[]{2, 45});

        Map<Character, Integer> countMap = new HashMap<>();
        for (char item : skus.toCharArray()) {
            if (!priceMap.containsKey(item)) {
                return 0;
            }
            countMap.put(item, countMap.getOrDefault(item, 0) + 1);
        }
        int totalCost = 0;
        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            char item = entry.getKey();
            int count = entry.getValue();

            if (offerMap.containsKey(item)) {
                int[] offer = offerMap.get(item);
                int offerQuantity = offer[0];
                int offerPrice = offer[1];

                int offerCount = count / offerQuantity;
                int remainder = count % offerQuantity;

                totalCost += offerCount * offerPrice;
                totalCost += remainder * priceMap.get(item);

            } else {
                totalCost += count * priceMap.get(item);
            }
        }
        return totalCost;
    }
}



