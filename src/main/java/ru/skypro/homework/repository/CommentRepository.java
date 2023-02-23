package ru.skypro.homework.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long> {


    @NotNull List<Comment> findAll();
}