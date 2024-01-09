package com.spacecodee.sprpsqlsec.service;

import com.spacecodee.sprpsqlsec.data.dto.CategoryDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveCategoryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {
    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto findOneById(long categoryId);

    void createOne(SaveCategoryVo saveCategoryVo);
    
    void updateOneById(long categoryId, SaveCategoryVo saveCategoryVo);

    void disableOneById(long categoryId);
}
