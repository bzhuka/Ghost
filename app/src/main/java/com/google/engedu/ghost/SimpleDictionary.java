/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private Random random = new Random();

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if (prefix.equals("")) {
            return words.get(random.nextInt(words.size() - 1));
        }
        return binarySearch(prefix);
    }

    private String binarySearch(String prefix) {
        int low = 0;
        int high = words.size() - 1;
        int comp;
        while (low < high) {
            int mid = low + (high - low) / 2;
            String stringcomp = words.get(mid);
            if (stringcomp.length() >= prefix.length()) {
                comp = prefix.compareTo(stringcomp.substring(0, prefix.length()));
            } else {
                comp = prefix.compareTo(stringcomp);
            }
            if (comp < 0) { // prefix is smaller
                high = mid - 1;
            } else if (comp > 0) { //prefix is bigger
                low = mid + 1;
            } else { // they are equal
                return words.get(mid);
            }
        }
        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        return selected;
    }
}
