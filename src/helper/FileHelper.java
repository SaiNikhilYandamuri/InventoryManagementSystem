package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileHelper {
    Path filePath;

    private ArrayList<String> contentFile = new ArrayList<>();

    public FileHelper(String pathToFile) {
        this.filePath = Paths.get(pathToFile);
    }

    public void fileReader() throws Exception{
        if(Files.exists(filePath)){
            BufferedReader reader = new BufferedReader((new FileReader(filePath.toFile())));
            String line = "";

            while((line=reader.readLine())!=null){
                contentFile.add(line);
            }
        }else{
            throw new Exception();
        }
    }
    public ArrayList<String> getContentFile() {
        return contentFile;
    }


}
