package com.khwebgame.core.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WordTable {
    private WordTable() {}
    private WordTable(WordTable ins) {}

    public static synchronized WordTable getInstance() {
        if (instance == null) {
            instance = new WordTable();
        }

        return instance;
    }

    private static WordTable instance;
    private ArrayList<String> words;

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> _words) {
        this.words = _words;

        for (String str:words) {
            System.out.println(str);
        }

        System.out.println("Words have been loaded!");
    }

    public String randomWord() throws Exception {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }
}
