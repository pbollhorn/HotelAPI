package app.dtos;

import app.entities.Poem;

public record PoemDto(Integer id, String name, String author, Integer year, String text)
{

    public PoemDto(Poem poem)
    {
        this(poem.getId(), poem.getName(), poem.getAuthor(), poem.getYear(), poem.getText());
    }

}