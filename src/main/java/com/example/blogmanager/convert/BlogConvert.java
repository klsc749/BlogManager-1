package com.example.blogmanager.convert;

import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DTO.BlogDTO;
import com.example.blogmanager.model.DTO.admin.AdminBlogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogConvert {
    BlogConvert INSTANCE = Mappers.getMapper(BlogConvert.class);

    public BlogDTO blogToBlogDTO(Blog blog);

    public AdminBlogDTO blogToAdminBlogDTO(Blog blog);

}
