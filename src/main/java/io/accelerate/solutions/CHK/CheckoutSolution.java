package io.accelerate.solutions.CHK;


import java.util.*;

public class CheckoutSolution {
    public Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) return 0;

        Map<Character, Integer> priceMap = new HashMap<>();
        priceMap.put('A', 50); priceMap.put('B', 30); priceMap.put('C', 20);
        priceMap.put('D', 15); priceMap.put('E', 40); priceMap.put('F', 10);
        priceMap.put('G', 20); priceMap.put('H', 10); priceMap.put('I', 35);
        priceMap.put('J', 60); priceMap.put('K', 70); priceMap.put('L', 90);
        priceMap.put('M', 15); priceMap.put('N', 40); priceMap.put('O', 10);
        priceMap.put('P', 50); priceMap.put('Q', 30); priceMap.put('R', 50);
        priceMap.put('S', 20); priceMap.put('T', 20); priceMap.put('U', 40);
        priceMap.put('V', 50); priceMap.put('W', 20); priceMap.put('X', 17);
        priceMap.put('Y', 20); priceMap.put('Z', 21);

        // Offers: Map<Item, List of [quantity, price]>
        Map<Character, List<int[]>> offerMap = new HashMap<>();
        offerMap.put('A', Arrays.asList(new int[]{5, 200}, new int[]{3, 130}));
        offerMap.put('B', Collections.singletonList(new int[]{2, 45}));
        offerMap.put('E', Collections.singletonList(new int[]{2, 40}));
        offerMap.put('F', Collections.singletonList(new int[]{3, 20}));
        offerMap.put('H', Arrays.asList(new int[]{10, 80}, new int[]{5, 45}));
        offerMap.put('I', Collections.singletonList(new int[]{3, 80}));
        offerMap.put('K', Collections.singletonList(new int[]{2, 120}));
        offerMap.put('M', Collections.singletonList(new int[]{3, 15}));
        offerMap.put('P', Collections.singletonList(new int[]{5, 200}));
        offerMap.put('Q', Collections.singletonList(new int[]{3, 80}));
        offerMap.put('R', Collections.singletonList(new int[]{3, 50}));

        offerMap.put('S', Arrays.asList(new int[]{3, 45}));
        offerMap.put('T', Arrays.asList(new int[]{3, 45}));
        offerMap.put('X', Arrays.asList(new int[]{3, 45}));
        offerMap.put('Z', Arrays.asList(new int[]{3, 45}));
        offerMap.put('U', Collections.singletonList(new int[]{3, 40}));
        offerMap.put('V', Arrays.asList(new int[]{3, 130}, new int[]{2, 90}));
        offerMap.put('W', Collections.singletonList(new int[]{3, 45}));

        // Count items
        Map<Character, Integer> countMap = new HashMap<>();
        for (char ch : skus.toCharArray()) {
            if (!priceMap.containsKey(ch)) return -1;
            countMap.put(ch, countMap.getOrDefault(ch, 0) + 1);
        }

//        // "2E get one B free"
//        int eCount = countMap.getOrDefault('E', 0);
//        int freeBCount = eCount / 2;
//        if (freeBCount > 0) {
//            int bCount = countMap.getOrDefault('B', 0);
//            countMap.put('B', Math.max(0, bCount - freeBCount));
//        }
//
//        // "3N get one M free"
//        int nCount = countMap.getOrDefault('N', 0);
//        int freeMCount = nCount / 3;
//        if (freeMCount > 0) {
//            int mCount = countMap.getOrDefault('M', 0);
//            countMap.put('M', Math.max(0, mCount - freeMCount));
//        }
//
//        // "3R get one Q free"
//        int rCount = countMap.getOrDefault('R', 0);
//        int freeQCount = rCount / 3;
//        if (freeQCount > 0) {
//            int qCount = countMap.getOrDefault('Q', 0);
//            countMap.put('Q', Math.max(0, qCount - freeQCount));
//        }
//
//        // "3U get one U free"
//        int uCount = countMap.getOrDefault('U', 0);
//        int uCharged = uCount - (uCount / 4); // Every 4 U -> 3 charged
//        countMap.put('U', uCharged);
//
//        // "2F get one F free" = Every 3 Fs -> only pay for 2
//        int fCount = countMap.getOrDefault('F', 0);
//        int fCharged = fCount - (fCount / 3);
//        countMap.put('F', fCharged);

        // Compute total
        int totalCost = 0;
        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            char item = entry.getKey();
            int count = entry.getValue();
            int price = priceMap.get(item);

            if (offerMap.containsKey(item)) {
                List<int[]> offers = offerMap.get(item);
                offers.sort((a, b) -> Integer.compare(b[0], a[0]));
                for (int[] offer : offers) {
                    int qty = offer[0], offerPrice = offer[1];
                    int numOffers = count / qty;
                    totalCost += numOffers * offerPrice;
                    count %= qty;
                }
            }

            totalCost += count * price;
        }

        return totalCost;
    }
}
