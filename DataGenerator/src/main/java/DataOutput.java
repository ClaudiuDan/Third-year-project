import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

public class DataOutput {
    private BufferedWriter inputDataWriter, targetDataWriter;

    DataOutput() {
        try {
            inputDataWriter = new BufferedWriter(new FileWriter("input"));
            targetDataWriter = new BufferedWriter(new FileWriter("targetText"));
            trainWriter = new BufferedWriter(new FileWriter("train.csv"));
            validationWriter = new BufferedWriter(new FileWriter("validation.csv"));
            testWriter = new BufferedWriter(new FileWriter("test.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeInput(Generator.MyStringBuilder data) {
        try {
            inputDataWriter.write(data.toString());
            inputDataWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTarget(Generator.MyStringBuilder data) {
        try {
            targetDataWriter.write(data.toString());
            targetDataWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final float TRAIN_RATIO = 3/5f, VALID_RATIO = 1 / 5f, TEST_RATIO = 1 / 5f;
    BufferedWriter trainWriter, validationWriter, testWriter;
    public void buildDatasets (Generator.MyStringBuilder input, Generator.MyStringBuilder target) {
        try {
            String[] inputLines = input.data.toString().split("\n");
            String[] targetLines = target.data.toString().split("\n");
            int counter = 0;
            trainWriter.write("src,trg\n");
            while (counter < inputLines.length && counter < Generator.SENTENCES * TRAIN_RATIO) {
                trainWriter.write("\"" + inputLines[counter] + "\"," + "\"" + targetLines[counter] + "\"\n");
                counter++;
            }
            trainWriter.close();

            int saved = counter;
            validationWriter.write("src,trg\n");
            while (counter < inputLines.length && counter < saved + Generator.SENTENCES * VALID_RATIO) {
                validationWriter.write("\"" + inputLines[counter] + "\"," + "\"" + targetLines[counter] + "\"\n");
                counter++;
            }
            validationWriter.close();

            saved = counter;
            testWriter.write("src,trg\n");
                while (counter < inputLines.length && counter < saved + Generator.SENTENCES * TEST_RATIO) {
                    testWriter.write("\"" + inputLines[counter] + "\"," + "\"" + targetLines[counter] + "\"\n");
                    counter++;
                }
            testWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
