package com.github.Gil4rt.TGBotSecretCode.bot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class database {

    private int adminId, statsAnyWord, statsSayedSecretWord, statsJoinedGroup;
    private String secretWord;

    public String allData = String.valueOf(adminId) + " "
            + String.valueOf(statsAnyWord) + " "
            + String.valueOf(statsSayedSecretWord) + " "
            + String.valueOf(statsJoinedGroup) + " " + secretWord;


    public int getAdminId() {
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

    public void createDatabase() throws IOException {

        File file = new File("database.txt");
        boolean result;

        try {
            result = file.createNewFile();
            if (result)
            {
                System.out.println("file created " + file.getCanonicalPath());

                FileWriter fr = new FileWriter(file, true);
                BufferedWriter br = new BufferedWriter(fr);
                br.write(allData);

                br.close();
                fr.close();

            } else {
                System.out.println("File already exist at location: " + file.getCanonicalPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}