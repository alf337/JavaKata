package alf.exercises.reddit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Challenge366WordFunnel {

    private Collection<String> wordList = null;

    public boolean funnel(String first, String second) {

        return IntStream.range(0, first.length())
                .mapToObj( i -> removeChar(first, i))
                .anyMatch( w -> w.equals(second));
    }

    private String removeChar(String input, int index) {

        char[] charsIn = input.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (i != index) {
                sb.append(charsIn[i]);
            }
        }
        return sb.toString();
    }

    public Set<String> bonus(String input) {

        return IntStream.range(0, input.length())
                .mapToObj( i -> removeChar(input, i))
                .filter( w -> getWordList().contains(w))
//                .peek(System.out::println)
                .collect(Collectors.toSet());
    }

    public List<Set<String>> bonus2() {

        return getWordList().parallelStream()
                .map( word -> {
                    Set<String> bonusSet = bonus(word);
                    bonusSet.add(word);
                    return bonusSet;
                })
                .filter( set -> set.size() > 5)
                .collect(Collectors.toList());
    }

    private Collection<String> getWordList() {

        if (wordList == null) {
            try {
                long start = System.currentTimeMillis();
                Path path = Paths.get(getClass().getClassLoader().getResource("word-list.txt").toURI());

                wordList = Files.lines(path).collect(Collectors.toCollection(TreeSet::new));
                System.out.println("Loaded word list, size = " + this.wordList.size() + ", time = " + (System.currentTimeMillis() - start));

            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
        return wordList;
    }
}
