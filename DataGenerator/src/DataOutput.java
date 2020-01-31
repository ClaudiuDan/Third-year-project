import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataOutput {
    private BufferedWriter inputDataWriter, targetDataWriter;

    DataOutput() {
        try {
            inputDataWriter = new BufferedWriter(new FileWriter("input"));
            targetDataWriter = new BufferedWriter(new FileWriter("target"));
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
}
