package io.accelerate.solutions.CHK;


import java.util.*;


public class CheckoutSolution {
    public Integer checkout(String skus) {
        if (skus == null) return -1;

        Map<Character, Integer> priceMap = Map.ofEntries(
                Map.entry('A', 50), Map.entry('B', 30), Map.entry('C', 20),
                Map.entry('D', 15), Map.entry('E', 40), Map.entry('F', 10),
                Map.entry('G', 20), Map.entry('H', 10), Map.entry('I', 35),
                Map.entry('J', 60), Map.entry('K', 70), Map.entry('L', 90),
                Map.entry('M', 15), Map.entry('N', 40), Map.entry('O', 10),
                Map.entry('P', 50), Map.entry('Q', 30), Map.entry('R', 50),
                Map.entry('S', 20), Map.entry('T', 20), Map.entry('U', 40),
                Map.entry('V', 50), Map.entry('W', 20), Map.entry('X', 17),
                Map.entry('Y', 20), Map.entry('Z', 21)
        );

        Map<Character, List<int[]>> offerMap = new HashMap<>();
        offerMap.put('A', List.of(new int[]{5, 200}, new int[]{3, 130}));
        offerMap.put('B', List.of(new int[]{2, 45}));
        offerMap.put('H', List.of(new int[]{10, 80}, new int[]{5, 45}));
        offerMap.put('K', List.of(new int[]{2, 120}));
        offerMap.put('P', List.of(new int[]{5, 200}));
        offerMap.put('Q', List.of(new int[]{3, 80}));
        offerMap.put('V', List.of(new int[]{3, 130}, new int[]{2, 90}));

        // Count SKUs
        Map<Character, Integer> countMap = new HashMap<>();
        for (char c : skus.toCharArray()) {
            if (!priceMap.containsKey(c)) return -1;
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }

        // 2E => get 1 B free
        int freeBs = countMap.getOrDefault('E', 0) / 2;
        countMap.put('B', Math.max(0, countMap.getOrDefault('B', 0) - freeBs));

        // 3N => get 1 M free
        int freeMs = countMap.getOrDefault('N', 0) / 3;
        countMap.put('M', Math.max(0, countMap.getOrDefault('M', 0) - freeMs));

        // 3R => get 1 Q free
        int freeQs = countMap.getOrDefault('R', 0) / 3;
        countMap.put('Q', Math.max(0, countMap.getOrDefault('Q', 0) - freeQs));

        // 3U => get 1 U free => every 4 U, only 3 are charged
        int uCount = countMap.getOrDefault('U', 0);
        int chargedU = uCount - (uCount / 4);
        countMap.put('U', chargedU);

        // 2F => get 1 F free => every 3 Fs, only pay for 2
        int fCount = countMap.getOrDefault('F', 0);
        int chargedF = fCount - (fCount / 3);
        countMap.put('F', chargedF);

        // any 3 of (S,T,X,Y,Z) for 45
        List<Character> groupItems = List.of('S', 'T', 'X', 'Y', 'Z');
        List<Character> groupSortedByPrice = new ArrayList<>(groupItems);
        groupSortedByPrice.sort((a, b) -> Integer.compare(priceMap.get(b), priceMap.get(a))); // Desc by price

        int groupCount = 0;
        for (char item : groupItems) groupCount += countMap.getOrDefault(item, 0);
        int groupOffers = groupCount / 3;
        int groupOfferCost = groupOffers * 45;

        int itemsToRemove = groupOffers * 3;
        for (char item : groupSortedByPrice) {
            int available = countMap.getOrDefault(item, 0);
            int used = Math.min(available, itemsToRemove);
            countMap.put(item, available - used);
            itemsToRemove -= used;
            if (itemsToRemove == 0) break;
        }

        int total = groupOfferCost;

        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            char item = entry.getKey();
            int count = entry.getValue();
            int price = priceMap.get(item);

            if (offerMap.containsKey(item)) {
                List<int[]> offers = new ArrayList<>(offerMap.get(item));
                offers.sort((a, b) -> Integer.compare(b[0], a[0]));
                for (int[] offer : offers) {
                    int num = count / offer[0];
                    total += num * offer[1];
                    count %= offer[0];
                }
            }
            total += count * price;
        }

        return total;
    }
}
