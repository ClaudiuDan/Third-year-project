import java.io.*;
import java.util.*;

public class WordGroupings {
    private Map<Group, Integer> groupsCheck = new HashMap<>();
    private Map<Integer, Map<Word, List<Group>>> sortedGroups = new HashMap<>();
    private static final int GROUP_SIZE = 3;

    WordGroupings () {
        try {
            buildGroups();
            groupLists();
            buildFullSortedGroups();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void buildFullSortedGroups() {
        for (Group group : groupsCheck.keySet()) {
            fullGroups.add(new Group(groupsCheck.get(group), group.words));
        }
        Collections.sort(fullGroups);
    }

    private void buildGroups () throws IOException {
        File raw_text = new File("text");
        File taggedText = new File("taggedText");
        BufferedReader reader = new BufferedReader(new FileReader(raw_text));
        BufferedReader taggedReader = new BufferedReader(new FileReader(taggedText));
        String line;
        while ((line = taggedReader.readLine()) != null) {
            line = line.toLowerCase();
            String[] splitLine = line.split(" ");
            Word[] tempGroup = new Word[GROUP_SIZE];
            for (int j = 0; j < GROUP_SIZE; j++) {
                String[] splitPair = splitLine[j].split("_");
                tempGroup[j] = new Word(splitPair[0], Dictionary.getGeneralType(splitPair[1]));
            }
            addGroup(new Group(tempGroup));

            Word[] tempGroup2;
            for (int j = GROUP_SIZE; j < splitLine.length; j++) {
                tempGroup2 = new Word[GROUP_SIZE];
                for (int i = 1; i < GROUP_SIZE; i++) {
                    tempGroup2[i - 1] = tempGroup[i];
                }
                String[] splitPair = splitLine[j].split("_");
                tempGroup2[GROUP_SIZE - 1] = new Word(splitPair[0], Dictionary.getGeneralType(splitPair[1]));
                addGroup(new Group(tempGroup2));
                tempGroup = tempGroup2;
            }
        }
    }

    private void addGroup (Group group) {
        groupsCheck.computeIfPresent(group, (k, v) -> v + 1);
        groupsCheck.putIfAbsent(group, 1);
    }

    private void groupLists() {
        for (int i = 0; i < GROUP_SIZE; i++) {
            sortedGroups.put(i, new HashMap<>());
            fillGroupMap(sortedGroups.get(i), i);
        }
    }

    private void fillGroupMap(Map<Word, List<Group>> groupMap, int index) {
        for (Group group : groupsCheck.keySet()) {
            groupMap.putIfAbsent(group.words[index], new ArrayList<>());
            groupMap.get(group.words[index]).add(new Group(groupsCheck.get(group), group.words));
        }
    }

    private ArrayList<Group> fullGroups = new ArrayList<>();
    public Group getFullGroup (List<String> types) {
        List<Group> filteredGroups = new ArrayList<>();
        List<String> nulledFilled = new ArrayList<>();
        for (String s : types)
            nulledFilled.add(null);

        String key = "";

        for (String type : types) {
            key += type;
        }

        if (sortedFullGroups.get(key) == null) {
            int sum = 0;
            for (Group group : fullGroups) {
                if (checkGroupMatch(nulledFilled, types, group.words)) {
                    sum += group.occurrences;
                    group.occurrences = sum;
                    filteredGroups.add(group);
                    sums.put(key, sum);

                }
            }
            sortedFullGroups.put(key, filteredGroups);
        }
        return binarySearch(sortedFullGroups.get(key), key);
    }

    Map<String, List<Group>> sortedFullGroups = new HashMap<>();
    Map<String, Integer> sums = new HashMap<>();
    public Group getPartGroup(List<String> values, List<String> types, int index) {
        Word word = new Word(values.get(index), types.get(index));
        List<Group> filteredGroups = new ArrayList<>();
        if (sortedGroups.get(index).get(word) == null) {
            return null;
        }

        for (Group group : sortedGroups.get(index).get(word)) {
            if (checkGroupMatch(values, types, group.words)) {
                filteredGroups.add(group);
            }
        }
        return rouletteWheel(filteredGroups);
    }

    private Group binarySearch(List<Group> groups, String key) {
        Group group = new Group((int)(Math.random() * sums.get(key)), null);
        int index = Collections.binarySearch(groups, group);
        if (index < 0) {
            index += 1;
        }
        return groups.get(Math.abs(index));
    }

    private boolean checkGroupMatch(List<String> values, List<String> types, Word[] matchAgainst) {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) != null && !values.get(i).equals(matchAgainst[i].value)) {
                return false;
            }
            if (types.get(i) != null && !types.get(i).equals(matchAgainst[i].type)) {
                return false;
            }
        }
        return true;
    }

    //TODO: make it log ;)
    private Group rouletteWheel (List<Group> groups) {
        if (groups.size() == 0)
            return null;
        double sum =  getSum(groups);
        Double pick = new Random().nextDouble() * sum;
        double current = 0;

        int index = 0;
        while (current < pick) {
            current += groups.get(index).occurrences;
            index++;
        }
        return groups.get(index - 1);
    }

    private double getSum (List<Group> groups) {
        double s = 0;
        for (Group g : groups) {
            s += g.occurrences;
        }
        return s;
    }

    class Group implements Comparable<Group>{
        public Word[] words;
        int occurrences = 1;
        Group (Word... words) {
            this.words = words;
        }
        Group (int occurrences, Word... words) {
            this.occurrences = occurrences;
            this.words = words;
        }
        @Override
        public boolean equals(Object obj) {
            Group group = (Group) obj;
            for (int i = 0; i < this.words.length; i++) {
                if (!this.words[i].equals(group.words[i])) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            StringBuilder builder = new StringBuilder();
            for (Word word : words) {
                builder.append(word.value + word.type);
            }
            return builder.toString().hashCode();
        }

        @Override
        public int compareTo(Group o) {
            if (this.occurrences > o.occurrences)
                return 1;
            if (this.occurrences == o.occurrences)
                return 0;
            return -1;
        }
    }

    static class Word {
        String value, type;
        Word (String value, String type) {
            this.value = value;
            this.type = type;
        }
        @Override
        public boolean equals(Object obj) {
            Word word = (Word) obj;
            if (this.value.equals(word.value) && this.type.equals(word.type)) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return (value + type).hashCode();
        }
    }
}
