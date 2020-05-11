import org.junit.Test;

import java.io.*;

public class DatasetTest {
    @Test
    public void testTrainSize() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("train.csv")));
        int counter = 0;
        while (reader.readLine() != null) {
            counter++;
        }
        if (counter - 1 != (int) (Params.EXAMPLES * Params.TRAIN_RATIO))
            assert (false);
        else
            assert (true);
    }

    @Test
    public void testValidSize() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("validation.csv")));
        int counter = 0;
        while (reader.readLine() != null) {
            counter++;
        }
        if (counter - 1 != (int) (Params.EXAMPLES * Params.VALID_RATIO)) {
            System.out.println(counter + " " + Params.EXAMPLES * Params.VALID_RATIO);
            assert (false);
        }
        else
            assert (true);
    }

    @Test
    public void testTestSize() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("test.csv")));
        int counter = 0;
        while (reader.readLine() != null) {
            counter++;
        }
        if (counter - 1 != (int) (Params.EXAMPLES * Params.TEST_RATIO))
            assert (false);
        else
            assert (true);
    }

    @Test
    public void testTrainValidChars()  throws IOException  {
        BufferedReader reader = new BufferedReader(new FileReader(new File("train.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            for (Character c : line.toCharArray()) {
                if (!checkValidChar(c)) {
                    System.out.println(c);
                    assert false;
                    return;
                }
            }
        }
        assert  true;
    }
    
    public boolean checkValidChar(Character c) {
        char[] validChars = new char[]{'?', '.', ' ', '\n', ',', '"', 'G', '(', ')', '_', '=', '\'', '-'};
        for (Character valid : validChars) 
            if (c.equals(valid))
                return true;
        if (c >= 'a' && c <= 'z')
            return true;
        if (c >= '0' && c <= '9')
            return true;
        return false;
    }

    @Test
    public void testValidValidChars()  throws IOException  {
        BufferedReader reader = new BufferedReader(new FileReader(new File("validation.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            for (Character c : line.toCharArray()) {
                if (!checkValidChar(c)) {
                    assert false;
                    return;
                }
            }
        }
        assert  true;
    }

    @Test
    public void testTestValidChars()  throws IOException  {
        BufferedReader reader = new BufferedReader(new FileReader(new File("test.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            for (Character c : line.toCharArray()) {
                if (!checkValidChar(c)) {
                    System.out.println (c);
                    assert false;
                    return;
                }
            }
        }
        assert  true;
    }


    @Test
    public void testTrainCorrectSpaces() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("train.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("  ")) {
                assert false;
                return;
            }
        }
        assert true;
    }


    @Test
    public void testValidCorrectSpaces()  throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("validation.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("  ")) {
                assert false;
                return;
            }
        }
        assert true;
    }


    @Test
    public void testTestCorrectSpaces()  throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("test.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("  ")) {
                assert false;
                return;
            }
        }
        assert true;
    }


    @Test
    public void testTrainNumberOfQuestions() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("train.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            long count = line.chars().filter(c -> isQuestion(c)).count();
            if (count > Params.MAX_QUESTIONS) {
                assert false;
                return;
            }
        }
        assert true;
    }

    private boolean isQuestion(Integer c) {
        if (c.equals('?'))
            return true;
        return false;
    }

    @Test
    public void testValidNumberOfQuestions() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("validation.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            long count = line.chars().filter(c -> isQuestion(c)).count();
            if (count > Params.MAX_QUESTIONS) {
                assert false;
                return;
            }
        }
        assert true;
    }

    @Test
    public void testTestNumberOfQuestions() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("test.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            long count = line.chars().filter(c -> isQuestion(c)).count();
            if (count > Params.MAX_QUESTIONS) {
                assert false;
                return;
            }
        }
        assert true;
    }

    @Test
    public void testTrainEmptyLine() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("train.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                assert false;
                return;
            }
        }
        assert true;
    }

    @Test
    public void testValidEmptyLine() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("validation.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                assert false;
                return;
            }
        }
        assert true;
    }

    @Test
    public void testTestEmptyLine() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("test.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                assert false;
                return;
            }
        }
        assert true;
    }
}
