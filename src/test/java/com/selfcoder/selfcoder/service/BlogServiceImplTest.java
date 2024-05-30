package com.selfcoder.selfcoder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import com.selfcoder.dto.BlogDTO;
import com.selfcoder.entity.Blog;
import com.selfcoder.repository.BlogRepository;
import com.selfcoder.request.CreateBlogRequest;
import com.selfcoder.request.UpdateBlogRequest;
import com.selfcoder.serviceimpl.BlogServiceImpl;
import com.selfcoder.util.APIResponseDTO;

@SpringBootTest
class BlogServiceImplTest {

    @Mock
     private  BlogRepository blogRepository;
    
    @Mock
	private  ModelMapper modelMapper;

    @InjectMocks
    private BlogServiceImpl blogService;

  

    @Test
    void testAddBlog() {
        CreateBlogRequest createBlogForm = new CreateBlogRequest();
        Blog blog = new Blog();
        when(blogRepository.save(blog)).thenReturn(blog);

        APIResponseDTO response = blogService.addBlog(createBlogForm);

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertTrue(response.isSuccess());
        assertNotNull(response.getTimeStamp());
        assertEquals("Set Data", response.getMessage());
    }

    @Test
    void testGetBlogs() {
        Integer page = 0;
        Integer size = 10;
        List<Blog> blogs = new ArrayList<>();
        blogs.add(new Blog());
        Page<Blog> pageOfBlogs = new PageImpl<>(blogs);
        when(blogRepository.findAll(any(PageRequest.class))).thenReturn(pageOfBlogs);

        APIResponseDTO response = blogService.getBlogs(page, size);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.isSuccess());
        assertNotNull(response.getTimeStamp());
        assertEquals("data found", response.getMessage());
        assertNotNull(response.getData());
        verify(blogRepository).findAll(any(PageRequest.class));
    }

    @Test
    void testDeleteBlogByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        doNothing().when(blogRepository).deleteAllById(ids);

        APIResponseDTO response = blogService.deleteBlogByIds(ids);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.isSuccess());
        assertNotNull(response.getTimeStamp());
        assertEquals("Blog deleted", response.getMessage());
        verify(blogRepository).deleteAllById(ids);
    }

    @Test
    void testUpdateBlog() {
        Long id = 1L;
        UpdateBlogRequest updateBlogForm = new UpdateBlogRequest();
        Blog blog = new Blog();
        when(blogRepository.findById(id)).thenReturn(Optional.of(blog));
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);

        APIResponseDTO response = blogService.updateBlog(id, updateBlogForm);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.isSuccess());
        assertNotNull(response.getTimeStamp());
        assertEquals("Set Data", response.getMessage());
        verify(blogRepository).findById(id);
        verify(blogRepository).save(any(Blog.class));
    }

    @Test
    void testGetBlogsById() {
        Long id = 1L;
        Blog blog = new Blog();
        blog.setId(id);
        BlogDTO obj = new BlogDTO();
        when(blogRepository.findById(id)).thenReturn(Optional.of(blog));
        when(modelMapper.map(blog, BlogDTO.class)).thenReturn(obj);
        APIResponseDTO response = blogService.getBlogsById(id);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.isSuccess());
        assertNotNull(response.getTimeStamp());
        assertEquals("data found", response.getMessage());
        assertNotNull(response.getData());
        verify(blogRepository).findById(id);
    }
}

