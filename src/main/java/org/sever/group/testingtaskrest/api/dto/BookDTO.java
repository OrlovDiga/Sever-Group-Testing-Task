package org.sever.group.testingtaskrest.api.dto;

import com.sun.istack.NotNull;
import org.sever.group.testingtaskrest.domain.util.LevelNumber;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * @author Orlov Diga
 */
public class BookDTO {

    private Long id;
    @NotNull
    @Size(min=2, max=60, message
            = "name must be between 2 and 60 characters")
    private String name;
    @NotNull
    @Size(min=2, max=60, message = "author must be between 2 and 60 characters")
    private String author;
    @NotNull
    private Long shelvingId;
    @NotNull
    private LevelNumber levelNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getShelvingId() {
        return shelvingId;
    }

    public void setShelvingId(Long shelvingId) {
        this.shelvingId = shelvingId;
    }

    public LevelNumber getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(LevelNumber levelNumber) {
        this.levelNumber = levelNumber;
    }
}
