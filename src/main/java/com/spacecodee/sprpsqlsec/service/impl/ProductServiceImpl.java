package com.spacecodee.sprpsqlsec.service.impl;

import com.spacecodee.sprpsqlsec.data.dto.ProductDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveProductVo;
import com.spacecodee.sprpsqlsec.exceptions.NoCreatedException;
import com.spacecodee.sprpsqlsec.exceptions.NoUpdatedException;
import com.spacecodee.sprpsqlsec.exceptions.ObjectNotFoundException;
import com.spacecodee.sprpsqlsec.mapper.IProductMapper;
import com.spacecodee.sprpsqlsec.persistence.entity.ProductEntity;
import com.spacecodee.sprpsqlsec.persistence.repository.IProductRepository;
import com.spacecodee.sprpsqlsec.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final IProductMapper productMapper;
    private static final String PRODUCT_NOT_FOUND = "The Product with this ID wasn't found: ";

    public Page<ProductDto> findAll(Pageable pageable) {
        return this.productRepository.findAll(pageable).map(this.productMapper::toDto);
    }

    @Override
    public ProductDto findOneById(long productId) {
        return this.productRepository.findById(productId).map(this.productMapper::toDto).orElseThrow(() -> new ObjectNotFoundException(ProductServiceImpl.PRODUCT_NOT_FOUND + productId));
    }

    @Override
    public void createOne(SaveProductVo saveProductVo) {
        try {
            this.productRepository.save(this.productMapper.toEntity(saveProductVo));
        } catch (Exception e) {
            throw new NoCreatedException("The Product wasn't created", e.getCause());
        }
    }

    @Override
    public void updateOneById(long productId, SaveProductVo saveProductVo) {
        ProductEntity productEntityFromDb = this.productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(ProductServiceImpl.PRODUCT_NOT_FOUND + productId));

        try {
            this.productRepository.save(this.productMapper.partialUpdate(saveProductVo, productEntityFromDb));
        } catch (Exception e) {
            throw new NoUpdatedException("The Product wasn't updated", e.getCause());
        }
    }

    @Override
    public void disableOneById(long productId) {
        ProductEntity productEntityFromDb = this.productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(ProductServiceImpl.PRODUCT_NOT_FOUND + productId));

        try {
            this.productRepository.save(this.productMapper.disableStatus(productEntityFromDb));
        } catch (Exception e) {
            throw new NoUpdatedException("The Product wasn't disabled", e.getCause());
        }
    }
}
