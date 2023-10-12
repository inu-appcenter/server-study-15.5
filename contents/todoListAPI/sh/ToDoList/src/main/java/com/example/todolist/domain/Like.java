package com.example.todolist.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "user_email",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_boardId",nullable = false)
    private Board board;

    @Builder
    public Like(User user,Board board){
        this.user=user;
        this.board=board;
    }

}
