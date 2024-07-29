package co.axelrod.webserver.blog.project;

import co.axelrod.webserver.blog.project.model.ProjectDescription;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectService {
    public static List<ProjectDescription> parseProjects(String path) {
        List<ProjectDescription> projectDescriptions = new ArrayList<>();

        File projectsFile = new File(path + "/project/projects.md");
        String line = "default";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(projectsFile))) {
            while (line != null) {
                String name = bufferedReader.readLine().replace("name: ", "");
                String link = bufferedReader.readLine().replace("link: ", "");
                String description = bufferedReader.readLine().replace("description: ", "");
                List<String> tags = Arrays.asList(bufferedReader.readLine().replace("tags: ", "").split(","));
                projectDescriptions.add(new ProjectDescription(name, link, description, tags));

                line = bufferedReader.readLine();
                line = bufferedReader.readLine();
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read file" + "/project/projects.md");
        }

        return projectDescriptions;
    }
}
