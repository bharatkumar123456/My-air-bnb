package com.blogapp12.service;

import com.blogapp12.entity.Post;
import com.blogapp12.exceptions.ResourceNotFound;
import com.blogapp12.payload.ListPostDto;
import com.blogapp12.payload.PostDto;
import com.blogapp12.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPostDto(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savePost = postRepository.save(post);
        PostDto dto = mapToDto(post);

        return dto;
    }
    
      Post mapToEntity(PostDto postDto){
         /*Post post =new Post();
         post.setContent(postDto.getContent());
         post.setDescription(postDto.getDescription());
         post.setTitle(postDto.getTitle());*/
          Post post = modelMapper.map(postDto, Post.class);
          return post;
    }
       
    PostDto mapToDto(Post post){
        /*PostDto dto =new PostDto();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());
        dto.setTitle(post.getTitle());*/
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }
    @Override
    public void deletePost(long id) {
       postRepository.deleteById(id);
    }

    @Override
    public Post getPost(long id) {
        Optional<Post> byId = postRepository.findById(id);
        Post post = byId.get();
        return post;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> allPost = postRepository.findAll();
        return allPost;
    }
    @Override
    public PostDto getPostById(long id){

        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post not found with id :"+id)
        );
        return mapToDto(post);
    }

    @Override
    public ListPostDto fetchAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                     : Sort.by(sortBy).descending();
        PageRequest pagable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> all = postRepository.findAll(pagable);
        List<Post> post = all.getContent();
        List<PostDto> postDto = post.stream().map(e -> mapToDto(e)).collect(Collectors.toList());

        ListPostDto listPostDto =new ListPostDto();
        listPostDto.setPostDto(postDto);
        listPostDto.setTotalPages(all.getTotalPages());
        listPostDto.setTotalElements((int)all.getTotalElements());
        listPostDto.setFirstPage(all.isFirst());
        listPostDto.setLastPage(all.isLast());
        listPostDto.setPageNumber(all.getNumber());

        return listPostDto;
    }
    
}
