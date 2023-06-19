package com.example.blogmanager.convert;

import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DTO.BlogItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BlogItemConvert {
    BlogItemConvert INSTANCE = Mappers.getMapper(BlogItemConvert.class);

    @Mapping(source = "content", target = "contentAbstract")
    public BlogItemDTO blogToBlogItemDTO(Blog blog);

    public List<BlogItemDTO> blogListToBlogItemDTOList(List<Blog> blogs);
}
