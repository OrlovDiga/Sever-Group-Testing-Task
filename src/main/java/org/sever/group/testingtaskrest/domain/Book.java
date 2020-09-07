package org.sever.group.testingtaskrest.domain;

import org.sever.group.testingtaskrest.domain.util.LevelNumber;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Orlov Diga
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String author;
    private Long rackId;
    private LevelNumber levelNumber;

    public Long getId() {
        return id;
    }

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
        return rackId;
    }

    public void setShelvingId(Long rackId) {
        this.rackId = rackId;
    }

    public LevelNumber getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(LevelNumber levelNumber) {
        this.levelNumber = levelNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != null ? !id.equals(book.id) : book.id != null) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (rackId != null ? !rackId.equals(book.rackId) : book.rackId != null) return false;
        return levelNumber == book.levelNumber;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (rackId != null ? rackId.hashCode() : 0);
        result = 31 * result + (levelNumber != null ? levelNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", rackId=" + rackId +
                ", levelNumber=" + levelNumber +
                '}';
    }
}
