package io.accelerate.solutions.CHK;


import java.util.*;

public class CheckoutSolution {
    public Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) return 0;

        // Prices
        Map<Character, Integer> priceMap = new HashMap<>();
        priceMap.put('A', 50);
        priceMap.put('B', 30);
        priceMap.put('C', 20);
        priceMap.put('D', 15);
        priceMap.put('E', 40);
        priceMap.put('F', 10);



        // Offers: Map<Item, List of [quantity, price]>
        Map<Character, List<int[]>> offerMap = new HashMap<>();
        offerMap.put('A', Arrays.asList(new int[]{5, 200}, new int[]{3, 130}));
        offerMap.put('B', Collections.singletonList(new int[]{2, 45}));
        offerMap.put('F', Collections.singletonList(new int[]{3, 20}));

        // Count items
        Map<Character, Integer> countMap = new HashMap<>();
        for (char ch : skus.toCharArray()) {
            if (!priceMap.containsKey(ch)) return -1; // illegal item
            countMap.put(ch, countMap.getOrDefault(ch, 0) + 1);
        }

        // Apply E → free B offer
        int eCount = countMap.getOrDefault('E', 0);
        int freeBCount = eCount / 2;
        int bCount = countMap.getOrDefault('B', 0);
        countMap.put('B', Math.max(0, bCount - freeBCount));

        // Calculate total cost
        int totalCost = 0;

        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            char item = entry.getKey();
            int count = entry.getValue();
            int price = priceMap.get(item);

            if (offerMap.containsKey(item)) {
                List<int[]> offers = offerMap.get(item);
                // Sort offers by quantity descending to prioritize better offers
                offers.sort((a, b) -> Integer.compare(b[0], a[0]));

                for (int[] offer : offers) {
                    int offerQty = offer[0];
                    int offerPrice = offer[1];

                    int numOffers = count / offerQty;
                    totalCost += numOffers * offerPrice;
                    count %= offerQty;
                }
            }

            // Add remaining items at regular price
            totalCost += count * price;
        }

        return totalCost;
    }
}
