package com.selfcoder.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selfcoder.dto.TagDTO;
import com.selfcoder.entity.Tag;
import com.selfcoder.exception.TagAlreadyExistException;
import com.selfcoder.exception.TagNotFoundException;
import com.selfcoder.repository.TagRepository;
import com.selfcoder.request.CreateTagRequest;
import com.selfcoder.request.UpdateTagRequest;
import com.selfcoder.service.TagService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
  
    private final ModelMapper modelMapper;
    

    @Override
    public APIResponseDTO addTag(CreateTagRequest createTagForm) {
        String tagName = createTagForm.getTagName();
        log.info("Adding tag: {}", tagName);
        
        Optional<Tag> existingTag = tagRepository.findByTagName(tagName);
        if (existingTag.isPresent()) {
            log.warn("Tag already exists: {}", tagName);
            throw new TagAlreadyExistException("Tag already exists: " + tagName);
        }
        
        log.debug("Mapping createTagForm to Tag entity");
        Tag tag = modelMapper.map(createTagForm, Tag.class);
        tagRepository.save(tag);
        log.info("Tag added: {}", tagName);
        
        return buildSuccessResponse("Tag added successfully", HttpStatus.CREATED);
    }

    @Override
    public APIResponseDTO getTags() {
        log.info("Fetching all tags");
        
        List<Tag> listOfTag = tagRepository.findAll();
        if (listOfTag.isEmpty()) {
            log.warn("No tags found");
            throw new TagNotFoundException("Data not found");
        }
        
        List<TagDTO> listDTO = listOfTag.stream()
                .map(tag -> modelMapper.map(tag, TagDTO.class))
                .toList();
        
        log.info("Returning {} tags", listDTO.size());
        return buildSuccessResponse("Data found", HttpStatus.OK, listDTO);
    }

    @Override
    public APIResponseDTO updateTag(UpdateTagRequest updateTagRequest) {
        Long tagId = updateTagRequest.getId();
        log.info("Updating tag with ID: {}", tagId);
        
        Tag existingTag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException("Tag not found with ID: " + tagId));
        
        modelMapper.map(updateTagRequest, existingTag);
        tagRepository.save(existingTag);
        
        log.info("Tag updated successfully: {}", updateTagRequest.getTagName());
        return buildSuccessResponse("Tag updated successfully", HttpStatus.OK);
    }

    private APIResponseDTO buildSuccessResponse(String message, HttpStatus status) {
        return APIResponseDTO.builder()
                .timeStamp(System.currentTimeMillis())
                .message(message)
                .success(true)
                .status(status.value())
                .build();
    }

    private APIResponseDTO buildSuccessResponse(String message, HttpStatus status, Object data) {
        return APIResponseDTO.builder()
                .timeStamp(System.currentTimeMillis())
                .data(data)
                .message(message)
                .success(true)
                .status(status.value())
                .build();
    }
}
