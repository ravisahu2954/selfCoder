package com.selfcoder.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selfcoder.dto.BlogDTO;
import com.selfcoder.entity.Blog;
import com.selfcoder.entity.User;
import com.selfcoder.exception.BlogNotFoundException;
import com.selfcoder.exception.CustomBadRequestException;
import com.selfcoder.repository.BlogRepository;
import com.selfcoder.repository.UserRepository;
import com.selfcoder.request.CreateBlogRequest;
import com.selfcoder.request.UpdateBlogRequest;
import com.selfcoder.service.BlogService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

	private final BlogRepository blogRepository;
 
	private final ModelMapper modelMapper;
	
	private final UserRepository userRepository;
	
	@Override
	public APIResponseDTO addBlog(CreateBlogRequest createBlogForm) {

		log.info("addBlog");

		Blog blog = modelMapper.map(createBlogForm, Blog.class);
		blogRepository.save(blog);
		Optional<User> findById = userRepository.findById(createBlogForm.getUserId());
		Set<Blog> blogSet = new HashSet<>();
		blogSet.add(blog);
		User user = findById.get();
		user.setBlogs(blogSet);
		userRepository.save(user);
		log.debug("Blog is added");
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("Set Data").success(true)
				.status(HttpStatus.CREATED.value()).build();
	}

	@Override
	public APIResponseDTO getBlogs(Integer page, Integer size) {

		Sort sort = Sort.by(Sort.Direction.DESC, "updateAt");
		Page<Blog> listOfBlog = blogRepository.findAll(PageRequest.of(page, size, sort));
		List<BlogDTO> listDTO = null;
		if (listOfBlog.isEmpty())
			throw new BlogNotFoundException("Data not found");
		else {
			listDTO = listOfBlog.stream().map(u -> modelMapper.map(u, BlogDTO.class)).collect(Collectors.toList());
		}
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).data(listDTO).message("data found")
				.success(true).status(HttpStatus.OK.value()).build();

	}

	@Override
	public APIResponseDTO deleteBlogByIds(List<Long> ids) {

		log.info("## Deleting Blog ## {}", ids);
		if (ids == null) {
			throw new CustomBadRequestException("ids cannot be null!");
		}
		try {
			blogRepository.deleteAllById(ids);
		} catch (EmptyResultDataAccessException e) {
			throw new BlogNotFoundException("data not found");
		}
		log.info("## Blog deleted ##");
		return APIResponseDTO.builder().message("Blog deleted").timeStamp(System.currentTimeMillis()).success(true)
				.status(HttpStatus.OK.value()).build();

	}

	@Override
	public APIResponseDTO updateBlog(Long id, UpdateBlogRequest updateBlogForm) {

		Optional<Blog> opt = blogRepository.findById(id);
		if (!opt.isPresent())
			throw new BlogNotFoundException("Data not found");
		Blog blog = Blog.convertCreateBlogFormToBlog(id, updateBlogForm);
		blogRepository.save(blog);
		log.debug("Blog is added"); // log can be added in classes and enums
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("Set Data").success(true)
				.status(HttpStatus.OK.value()).build();

	}

	@Override
	public APIResponseDTO getBlogsById(Long id) {

		Optional<Blog> b = blogRepository.findById(id);
		if (b.isPresent()) {
			return APIResponseDTO.builder().timeStamp(System.currentTimeMillis())
					.data(modelMapper.map(b.get(), BlogDTO.class)).message("data found").success(true)
					.status(HttpStatus.OK.value()).build();
		} else
			throw new BlogNotFoundException("Data not found");

	}

}
