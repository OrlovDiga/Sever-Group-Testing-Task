package org.sever.group.testingtaskrest.api.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.Size;

/**
 * @author Orlov Diga
 */
public class BookNameDTO {

    @NotNull
    @Size(min=2, max=60, message
            = "name must be between 2 and 60 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
