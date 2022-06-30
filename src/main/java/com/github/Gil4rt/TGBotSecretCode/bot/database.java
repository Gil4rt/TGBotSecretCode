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
        return adminId;
    }

    public int getStatsAnyWord() {
        return statsAnyWord;
    }

    public int getStatsSayedSecretWord() {
        return statsSayedSecretWord;
    }

    public int getStatsJoinedGroup() {
        return statsJoinedGroup;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void callDatabase() throws IOException {

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

                adminId = Long.parseLong(linesArr[0]);
                statsAnyWord = Integer.parseInt(linesArr[1]);
                statsSayedSecretWord = Integer.parseInt(linesArr[2]);
                statsJoinedGroup = Integer.parseInt(linesArr[3]);
                secretWord = linesArr[4];

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateDatabase() {

        try {
            allData = String.valueOf(adminId) + "\r\n"
                    + String.valueOf(statsAnyWord) + "\r\n"
                    + String.valueOf(statsSayedSecretWord) + "\r\n"
                    + String.valueOf(statsJoinedGroup) + "\r\n" + secretWord;

            adminId = Long.parseLong(linesArr[0]);
            statsAnyWord = Integer.parseInt(linesArr[1]);
            statsSayedSecretWord = Integer.parseInt(linesArr[2]);
            statsJoinedGroup = Integer.parseInt(linesArr[3]);
            secretWord = linesArr[4];

            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);

            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();

            br.write(allData);
            br.close();
            fr.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}