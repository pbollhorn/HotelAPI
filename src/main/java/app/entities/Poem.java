package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import app.dtos.PoemDto;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Poem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String author;
    private Integer year;

    @Column(nullable = false, length = 10000)
    private String text;

    // This constructor creates a new Poem entity from PoemDto
    // Completely ignores id in the PoemDto
    public Poem(PoemDto poemDto) {
        this.name = poemDto.name();
        this.author = poemDto.author();
        this.year = poemDto.year();
        this.text = poemDto.text();
    }

    public Poem(int id, PoemDto poemDto) {
        this.id = id;
        this.name = poemDto.name();
        this.author = poemDto.author();
        this.year = poemDto.year();
        this.text = poemDto.text();
    }

}