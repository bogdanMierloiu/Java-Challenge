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
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;
    @ManyToMany(mappedBy = "badges")
    @ToString.Exclude
    private List<Wallet> wallets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Badge badge = (Badge) o;
        return getId() != null && Objects.equals(getId(), badge.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
