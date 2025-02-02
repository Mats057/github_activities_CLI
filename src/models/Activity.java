package models;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Activity {
    private String id;
    private String type;
    private Actor actor;
    private Repo repo;
    private Payload payload;

    @SerializedName("public")
    private boolean isPublic;

    @SerializedName("created_at")
    private String createdAt;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        String formattedDate;

        if (createdAt == null || createdAt.isEmpty()) {
            formattedDate = "Unknown date";
        } else {
            formattedDate = formatter.format(Instant.parse(createdAt));
        }

        StringBuilder sb = new StringBuilder();
        sb.append("-".repeat(10)).append("\n").append("\n");
        sb.append("\033[1;34mActivity ID:\033[0m ").append(id).append("\n");
        sb.append("\033[1;34mType:\033[0m ").append(type).append("\n");
        sb.append("\033[1;34mActor:\033[0m ").append(actor.getLogin()).append(" (" + actor.getUrl() + ")\n");
        sb.append("\033[1;34mRepository:\033[0m ").append(repo.getName()).append(" (" + repo.getUrl() + ")\n");
        sb.append("\033[1;34mCreated At:\033[0m ").append(formattedDate).append("\n");

        if (payload == null || payload.getCommits() == null || payload.getCommits().isEmpty()) {
            sb.append("\033[1;31mNo commits available.\033[0m\n");
        } else {
            sb.append("\033[1;34mCommits:\033[0m\n");
            for (Commit commit : payload.getCommits()) {
            sb.append("  - " + commit.getMessage()).append(" (by " + commit.getAuthor().getName() + ")\n");
            sb.append("    Link: " + commit.getUrl()).append("\n");
            }
        }

        return sb.toString();
    }
}

class Actor {
    private String login;
    private String url;

    public String getLogin() {
        return login;
    }

    public String getUrl() {
        return url;
    }
}

class Repo {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

class Payload {
    private List<Commit> commits;

    public List<Commit> getCommits() {
        return commits;
    }
}

class Commit {
    private String message;
    private String url;
    private Author author;

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public Author getAuthor() {
        return author;
    }
}

class Author {
    private String name;

    public String getName() {
        return name;
    }
}
