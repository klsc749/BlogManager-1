package com.example.blogmanager.convert;

import com.example.blogmanager.model.DO.Blog;
import com.example.blogmanager.model.DTO.admin.AdminBlogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminBlogDTOConvert {
    AdminBlogDTOConvert INSTANCE = Mappers.getMapper(AdminBlogDTOConvert.class);

    public Blog adminBlogDTOToBlog(AdminBlogDTO adminBlogDTO);
}
