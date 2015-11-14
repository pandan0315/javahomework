/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author danpan
 */
public class WordReader {
    
    public static synchronized String getWord() {
        //StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
            String line = reader.readLine();
            List<String> lines = new ArrayList<>();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            while(true) {
                Random r = new Random(System.currentTimeMillis());
                String randomLine = lines.get(r.nextInt(lines.size()));
                if(WordReader.isWordValid(randomLine)) {
                    return randomLine;
                }
            }

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * 
     * @param word
     * @return
     */
    private static boolean isWordValid(String word) {
        boolean isValid = true;
        int length = word.length();
        // the length of word can not larger than 10
        if (length > 10) return false;
        // Check if the word contains invalid characters.
        for (int i = 0; i < length; i++) {
            char c = word.charAt(i);
            if (c < 'a' || c > 'z') {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
    
}
