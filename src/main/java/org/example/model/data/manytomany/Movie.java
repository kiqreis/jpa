package org.example.model.data.manytomany;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Double filmNote;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "actors_movies",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id")
  )
  List<Actor> actors = new ArrayList<>();

  public Movie() {
  }

  public Movie(String name, Double filmNote) {
    this.name = name;
    this.filmNote = filmNote;
  }

  public void addActor(Actor actor) {

    if (actor != null && !getActors().contains(actor)) {
      this.getActors().add(actor);

      if (!actor.getMovies().contains(this)) {
        actor.getMovies().add(this);
      }
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getFilmNote() {
    return this.filmNote;
  }

  public void setFilmNote(Double filmNote) {
    this.filmNote = filmNote;
  }

  public List<Actor> getActors() {
    return actors;
  }
}
