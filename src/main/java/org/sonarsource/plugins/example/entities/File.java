package org.sonarsource.plugins.example.entities;

/**
 * Created by Robin on 22-6-2016.
 */
public class File {
    private String path;
    private String name;

    public File(String path) {
        this.path = path;
        int slashIndex = path.lastIndexOf('/');
        if (slashIndex > 0) {
            this.name = path.substring(path.lastIndexOf('/') + 1);
        } else {
            this.name = path;
        }
    }

    public String getName() {
        return name;
    }
}
