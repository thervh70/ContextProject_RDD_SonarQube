package org.sonarsource.plugins.example.entities;

import java.util.ArrayList;

/**
 * Created by Robin on 22-6-2016.
 */
public class PullRequest {
    private int id;
    private ArrayList<File> fileList;

    public PullRequest(int id, ArrayList<File> fileList) {
        this.id = id;
        this.fileList = fileList;
    }

    public int getId() {
        return id;
    }

    public ArrayList<File> getFileList() {
        return fileList;
    }
}
