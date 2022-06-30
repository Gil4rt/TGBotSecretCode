package com.github.Gil4rt.TGBotSecretCode.bot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class database {

    final public String databaseName = "src/main/java/com/github/Gil4rt/TGBotSecretCode/bot/database/database.txt";
    private int statsAnyWord, statsSayedSecretWord, statsJoinedGroup;

    public long adminId;
    private String secretWord;

    File file = new File(databaseName);

    public String allData = Long.toString(adminId) + "\r\n"
            + String.valueOf(statsAnyWord) + "\r\n"
            + String.valueOf(statsSayedSecretWord) + "\r\n"
            + String.valueOf(statsJoinedGroup) + "\r\n" + secretWord;

    public String[] linesArr;


    public long getAdminId() {
        adminId = Long.parseLong(linesArr[0]);
        return adminId;
    }

    public int getStatsAnyWord() {
        statsAnyWord = Integer.parseInt(linesArr[1]);
        return statsAnyWord;
    }

    public int getStatsSayedSecretWord() {
        statsSayedSecretWord = Integer.parseInt(linesArr[2]);
        return statsSayedSecretWord;
    }

    public int getStatsJoinedGroup() {
        statsJoinedGroup = Integer.parseInt(linesArr[3]);
        return statsJoinedGroup;
    }

    public String getSecretWord() {
        secretWord = linesArr[4];
        return secretWord;
    }

    public void createDatabaseAndParse() throws IOException {

        boolean result;

        try {
            result = file.createNewFile();
            if (result)
            {
                FileWriter fr = new FileWriter(file, true);
                BufferedWriter br = new BufferedWriter(fr);
                br.write(allData);

                br.close();
                fr.close();

            } else {

                Path filePath = Path.of(databaseName);

                String content = Files.readString(filePath);

                String inputStr = content;
                linesArr = inputStr.lines().toArray(String[]::new);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateDatabase() throws IOException {

        allData = String.valueOf(adminId) + "\r\n"
                + String.valueOf(statsAnyWord) + "\r\n"
                + String.valueOf(statsSayedSecretWord) + "\r\n"
                + String.valueOf(statsJoinedGroup) + "\r\n" + secretWord;

        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);

        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();

        br.write(allData);
        br.close();
        fr.close();
    }
}