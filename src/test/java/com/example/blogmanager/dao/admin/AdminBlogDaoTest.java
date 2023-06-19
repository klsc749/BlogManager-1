package com.example.blogmanager.dao.admin;

import com.example.blogmanager.model.DO.BlogStatus;
import com.example.blogmanager.model.DO.Visibility;
import com.example.blogmanager.model.DTO.BlogDTO;
import com.example.blogmanager.model.DTO.admin.AdminBlogDTO;
import com.example.blogmanager.model.DTO.admin.AdminBlogItemDTO;
import com.example.blogmanager.util.PageQueryUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class AdminBlogDaoTest {
    @Autowired
    private AdminBlogDao adminBlogDao;

    @Test
    public void testGetAdminBlogItemDTOByPage() {
        PageQueryUtil pageQueryUtil = new PageQueryUtil(1, 10);
        List<AdminBlogItemDTO> adminBlogItemDTOS = adminBlogDao.getAdminBlogItemDTO(pageQueryUtil);
        System.out.println(adminBlogItemDTOS);
    }

    @Test
    public void testCountAllBlogs() {
        System.out.println(adminBlogDao.countAllBlogs());
    }

    @Test
    public void testGetAdminBlogItemDTO(){
        System.out.println(adminBlogDao.getAdminBlogDTOById("50"));
    }

    @Test
    public void testSaveBlog() {
        AdminBlogDTO adminBlogDTO = new AdminBlogDTO();
        adminBlogDTO.setId("52");
        adminBlogDTO.setTitle("test");
        adminBlogDTO.setContent("test");
        adminBlogDTO.setCategory("test");
        adminBlogDTO.setTags(List.of("test"));
        adminBlogDTO.setStatus(BlogStatus.PUBLISHED);
        adminBlogDTO.setVisibility(Visibility.PUBLIC);
        System.out.println(adminBlogDao.getAdminBlogDTOById("52"));
        System.out.println(adminBlogDao.getAdminBlogDTOById("52"));
    }


}
