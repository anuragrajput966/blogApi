package com.blogapi.service.Impl;

import com.blogapi.entity.Comment;
import com.blogapi.entity.Post;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payload.CommentDto;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepo;
    private CommentRepository commentRepo;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post =  postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException(postId)
        );
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        comment.setPost(post);

        Comment saveComment = commentRepo.save(comment);

       return mapToDto(saveComment);
    }

    @Override
    public List<CommentDto> findCommentByPostId(long postId) {
        Post post =postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(postId)
        );

        List<Comment> comments = commentRepo.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post =postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(postId)
        );

        Comment comment =commentRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(commentId)
        );

        commentRepo.deleteById(commentId);
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post =postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(postId)
        );

        Comment comment =commentRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(commentId)
        );

        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public CommentDto updatedComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(postId)
        );
        Comment comment =commentRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(commentId)
        );

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepo.save(comment);
        return mapToDto(updatedComment);
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }

}
