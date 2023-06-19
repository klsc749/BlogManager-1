package com.example.blogmanager.dao.visitor;

import com.example.blogmanager.util.PageQueryUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VisitorBlogTest {

    private final VisitorBlogDao visitorBlogDao;

    @Test
    public void testGetBlogItemsByPage() {
        PageQueryUtil pageUtil = new PageQueryUtil(1, 10);
        System.out.println(visitorBlogDao.getBlogItemsByPage(pageUtil));
    }
}
