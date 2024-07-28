package co.axelrod.webserver.blog.article;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import co.axelrod.webserver.blog.article.model.ArticleDescription;

public class ArticleService {
    public static List<ArticleDescription> parseArticles(String path) {
        try {
            return Files.list(Path.of(path + "/article"))
                    .filter(articlePath -> !Files.isDirectory(articlePath))
                    .map(articlePath -> new File(String.valueOf(articlePath)))
                    .map(articleFile -> {
                        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(articleFile))) {
                            String title = bufferedReader.readLine();
                            List<String> tags = Arrays.asList(bufferedReader.readLine().split(","));
                            String articlePath = articleFile.getName();
                            return new ArticleDescription(title, tags, articlePath);
                        } catch (IOException e) {
                            throw new RuntimeException("Unable to read file" + articleFile);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
