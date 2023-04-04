package com.bogdanmierloiu.Java_Challenge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Reputation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "reputation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Player> players;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reputation reputation = (Reputation) o;
        return getId() != null && Objects.equals(getId(), reputation.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
