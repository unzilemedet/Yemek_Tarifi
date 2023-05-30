package com.unzilemedet.service;


import com.unzilemedet.repository.ICommentRepository;
import com.unzilemedet.repository.entity.Comment;
import com.unzilemedet.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ServiceManager<Comment, String> {

    private final ICommentRepository commentRepository;

    public CommentService(ICommentRepository commentRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
    }
}
