package com.harbourspace.lesson06;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class OptionalTask {

    public static void main(String[] args){

        String word = "words";
        String text = "text is in word";
        Optional<String> option = getWord(text, word);

        if (option.isPresent()){
            System.out.println("Word found: " + word);
        } else {
            System.out.println("Word not found: " + word);
        }



    }

    public static Optional<String> getWord(String text, String word) {
        if (text.contains(word)){
            return Optional.of(word);
        } else {
            return Optional.empty();
        }
    }


}

