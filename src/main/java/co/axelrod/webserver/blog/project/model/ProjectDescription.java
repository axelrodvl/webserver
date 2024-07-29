package co.axelrod.webserver.blog.project.model;

import java.util.List;

public class ProjectDescription {
    private final String name;
    private final String link;
    private final String description;
    private final List<String> tags;

    public ProjectDescription(String name, String link, String description, List<String> tags) {
        this.name = name;
        this.link = link;
        this.description = description;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }
}
