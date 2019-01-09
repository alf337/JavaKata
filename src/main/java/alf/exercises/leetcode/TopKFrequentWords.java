package alf.exercises.leetcode;

import java.util.*;

/**
 * Given a non-empty list of words, return the k most frequent elements.
 *
 * Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, then the word with the lower alphabetical order comes first.
 *
 * Example 1:
 *
 * Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
 * Output: ["i", "love"]
 * Explanation: "i" and "love" are the two most frequent words.
 *     Note that "i" comes before "love" due to a lower alphabetical order.
 *
 * Example 2:
 *
 * Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
 * Output: ["the", "is", "sunny", "day"]
 * Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
 *     with the number of occurrence being 4, 3, 2 and 1 respectively.
 *
 * Note:
 *
 *     You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 *     Input words contain only lowercase letters.
 *
 * Follow up:
 *
 *     Try to solve it in O(n log k) time and O(n) extra space.
 */
public class TopKFrequentWords {
    public List<String> topKFrequent(String[] words, int k) {

        Map<String, Integer> countMap = countWords(words);
        SortedMap<Integer, List<String>> sortedMap = sortByCount(countMap);
        List<String> result = new ArrayList<>();

        int i = 0;
        for (List<String> wordList : sortedMap.values()) {
            Collections.sort(wordList);
            for (String word : wordList) {
                result.add(word);
                i++;
                if (i == k) {
                    return result;
                }
            }
        }

        return result;
    }

    protected Map<String, Integer> countWords(String[] words) {
        Map<String, Integer> countMap = new HashMap<>();
        for (String word : words) {
            if (countMap.containsKey(word)) {
                countMap.put(word, countMap.get(word) + 1);
            } else {
                countMap.put(word, 1);
            }
        }
        return countMap;
    }

    protected SortedMap<Integer, List<String>> sortByCount(Map<String, Integer> countMap) {
        SortedMap<Integer, List<String>> sortedMap = new TreeMap<>((o1, o2) -> o2 - o1);

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            String word = entry.getKey();
            Integer count = entry.getValue();
            if (sortedMap.containsKey(count)) {
                List<String> wordList = sortedMap.get(count);
                wordList.add(word);
            } else {
                List<String> wordList = new ArrayList<>();
                wordList.add(word);
                sortedMap.put(count, wordList);
            }
        }
        return sortedMap;
    }
}
