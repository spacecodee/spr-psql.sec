package com.spacecodee.sprpsqlsec.service.impl;

import com.spacecodee.sprpsqlsec.data.dto.CategoryDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveCategoryVo;
import com.spacecodee.sprpsqlsec.exceptions.NoCreatedException;
import com.spacecodee.sprpsqlsec.exceptions.NoUpdatedException;
import com.spacecodee.sprpsqlsec.exceptions.ObjectNotFoundException;
import com.spacecodee.sprpsqlsec.mapper.ICategoryMapper;
import com.spacecodee.sprpsqlsec.persistence.entity.CategoryEntity;
import com.spacecodee.sprpsqlsec.persistence.repository.ICategoryRepository;
import com.spacecodee.sprpsqlsec.service.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final ICategoryMapper categoryMapper;
    private static final String CATEGORY_NOT_FOUND = "The Category with this ID wasn't found: ";


    public CategoryServiceImpl(ICategoryRepository categoryRepository, ICategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        return this.categoryRepository.findAll(pageable).map(this.categoryMapper::toDto);
    }

    @Override
    public CategoryDto findOneById(long productId) {
        return this.categoryRepository.findById(productId).map(this.categoryMapper::toDto).orElseThrow(() -> new ObjectNotFoundException(CategoryServiceImpl.CATEGORY_NOT_FOUND + productId));
    }

    @Override
    public void createOne(SaveCategoryVo saveCategoryVo) {
        try {
            this.categoryRepository.save(this.categoryMapper.toEntity(saveCategoryVo));
        } catch (Exception e) {
            throw new NoCreatedException("The Category wasn't created", e.getCause());
        }
    }

    @Override
    public void updateOneById(long categoryId, SaveCategoryVo saveCategoryVo) {
        CategoryEntity categoryEntityFromDb = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException(CategoryServiceImpl.CATEGORY_NOT_FOUND + categoryId));

        try {
            this.categoryRepository.save(this.categoryMapper.partialUpdate(saveCategoryVo, categoryEntityFromDb));
        } catch (Exception e) {
            throw new NoUpdatedException("The Category wasn't updated", e.getCause());
        }
    }

    @Override
    public void disableOneById(long categoryId) {
        CategoryEntity categoryEntityFromDb = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException(CategoryServiceImpl.CATEGORY_NOT_FOUND + categoryId));

        try {
            this.categoryRepository.save(this.categoryMapper.disableStatus(categoryEntityFromDb));
        } catch (Exception e) {
            throw new NoUpdatedException("The Product wasn't disabled", e.getCause());
        }
    }
}
