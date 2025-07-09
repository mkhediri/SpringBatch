package com.batchtraining.mk.springbatch.listener;

import com.batchtraining.mk.springbatch.model.StudentCsv;
import com.batchtraining.mk.springbatch.model.StudentJson;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;

@Component
public class MySkipListener implements SkipListener<StudentCsv, StudentJson> {

    @Override
    public void onSkipInRead(Throwable th) {
        if(th instanceof FlatFileParseException) {
            createFile("Second Job/Xml CHunk Step/reader/skipInRead.txt", ((FlatFileParseException) th).getInput());
        }
    }

    @Override
    public void onSkipInProcess(StudentCsv item, Throwable th) {
            createFile("Second Job/Xml CHunk Step/processor/skipInProcess.txt", item.toString());
    }

    @Override
    public void onSkipInWrite(StudentJson item, Throwable th) {
        createFile("Second Job/Xml CHunk Step/writer/skipInWrite.txt", item.toString());
    }

    public void createFile(String filePath, String data) {
        try(FileWriter writer = new FileWriter(new File(filePath), true)) {
            writer.write(data + "\n");
        } catch(Exception e) {

        }
    }
}
