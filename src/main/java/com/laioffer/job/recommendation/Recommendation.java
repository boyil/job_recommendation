package com.laioffer.job.recommendation;

import com.laioffer.job.db.MySQLConnection;
import com.laioffer.job.entity.Item;
import com.laioffer.job.external.GithubClient;

import java.util.*;

public class Recommendation {
    public List<Item> recommendItems(String userId, double lat, double lon){
        MySQLConnection conn = new MySQLConnection();
        Set<String> favoritedItemIds = conn.getFavoriteItemIds(userId);

        Map<String, Integer> allKeywords = new HashMap<>();
        for (String itemId: favoritedItemIds){
            Set<String> keywords = conn.getKeywords(itemId);
            for (String keyword: keywords){
                allKeywords.put(keyword, allKeywords.getOrDefault(keyword, 0) + 1);
            }
        }
        conn.close();

        List<Map.Entry<String, Integer>> keywordList = new ArrayList<>(allKeywords.entrySet());
        keywordList.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        if (keywordList.size() > 3){
            keywordList = keywordList.subList(0, 3);
        }

        List<Item> recommendedItems = new ArrayList<>();
        Set<String> visitedItemIds = new HashSet<>();
        GithubClient client = new GithubClient();
        for (Map.Entry<String, Integer> keyword: keywordList){
            List<Item> items = client.search(lat, lon, keyword.getKey());
            for (Item item: items){
                if (!favoritedItemIds.contains(item.getId()) && !visitedItemIds.contains(item.getId())){
                    recommendedItems.add(item);
                    visitedItemIds.add(item.getId());
                }
            }
        }
        return recommendedItems;
    }
}
