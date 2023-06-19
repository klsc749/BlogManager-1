package com.example.blogmanager.service.visitor;

import com.example.blogmanager.dao.visitor.VisitorBlogCollectionDao;
import com.example.blogmanager.model.DTO.visitor.VisitorBlogCollectionDTO;
import com.example.blogmanager.util.PageQueryUtil;
import com.example.blogmanager.util.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorBlogCollectionServiceI implements  VisitorBlogCollectionService{
    private final VisitorBlogCollectionDao visitorBlogCollectionDao;
    @Override
    public PageResult<VisitorBlogCollectionDTO> getBlogCollectionByPage(PageQueryUtil pageQueryUtil) {
        PageResult<VisitorBlogCollectionDTO> pageResult = new PageResult<>();
        List<VisitorBlogCollectionDTO> blogCollectionByPage = visitorBlogCollectionDao.getBlogCollectionByPage(pageQueryUtil);
        pageResult.setData(blogCollectionByPage);
        pageResult.setCurrentPage(pageQueryUtil.getPage());
        pageResult.setTotalElements(visitorBlogCollectionDao.countBlogCollection());
        pageResult.setPageSize(pageQueryUtil.getLimit());
        pageResult.setTotalPages((int) Math.ceil((double) pageResult.getTotalElements() / pageResult.getPageSize()));
        return pageResult;
    }
}
