package com.spacecodee.sprpsqlsec.service;

import com.spacecodee.sprpsqlsec.data.dto.ProductDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveProductVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<ProductDto> findAll(Pageable pageable);

    ProductDto findOneById(long productId);

    void createOne(SaveProductVo saveProductVo);

    void updateOneById(long productId, SaveProductVo saveProductVo);

    void disableOneById(long productId);
}
