package com.spacecodee.sprpsqlsec.controller;

import com.spacecodee.sprpsqlsec.data.dto.ProductDto;
import com.spacecodee.sprpsqlsec.data.pojo.ApiResponseDataPojo;
import com.spacecodee.sprpsqlsec.data.pojo.ApiResponsePojo;
import com.spacecodee.sprpsqlsec.data.vo.SaveProductVo;
import com.spacecodee.sprpsqlsec.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDataPojo<Page<ProductDto>>> findAll(Pageable pageable) {
        var productPage = this.productService.findAll(pageable);
        var apiResponseDataPojo = new ApiResponseDataPojo<Page<ProductDto>>();

        apiResponseDataPojo.setData(productPage);
        apiResponseDataPojo.setHttpStatus(HttpStatus.BAD_REQUEST);

        if (productPage.hasContent()) {
            apiResponseDataPojo.setHttpStatus(HttpStatus.OK);
            apiResponseDataPojo.setMessage("Product list");
            return new ResponseEntity<>(apiResponseDataPojo, HttpStatusCode.valueOf(apiResponseDataPojo.getStatusCode()));
        }

        apiResponseDataPojo.setMessage("Product list isn't found");
        return new ResponseEntity<>(apiResponseDataPojo, HttpStatusCode.valueOf(apiResponseDataPojo.getStatusCode()));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseDataPojo<ProductDto>> findOneById(@PathVariable long productId) {
        var apiResponseDataPojo = new ApiResponseDataPojo<ProductDto>();
        var productPage = this.productService.findOneById(productId);

        apiResponseDataPojo.setMessage("Product found");
        apiResponseDataPojo.setData(productPage);
        apiResponseDataPojo.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponseDataPojo);
    }

    @PostMapping()
    public ResponseEntity<ApiResponsePojo> createOne(@RequestBody @Valid SaveProductVo saveProductVo) {
        var apiResponseDataPojo = new ApiResponsePojo();

        this.productService.createOne(saveProductVo);
        apiResponseDataPojo.setMessage("Product created");
        apiResponseDataPojo.setHttpStatus(HttpStatus.CREATED);
        return new ResponseEntity<>(apiResponseDataPojo, HttpStatusCode.valueOf(apiResponseDataPojo.getStatusCode()));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponsePojo> updateOneById(@PathVariable long productId, @RequestBody @Valid SaveProductVo updateVo) {
        var apiResponseDataPojo = new ApiResponsePojo();

        this.productService.updateOneById(productId, updateVo);
        apiResponseDataPojo.setMessage("Product updated");
        apiResponseDataPojo.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(apiResponseDataPojo, HttpStatusCode.valueOf(apiResponseDataPojo.getStatusCode()));
    }

    @PutMapping("/{productId}/disabled")
    public ResponseEntity<ApiResponsePojo> disableOneById(@PathVariable long productId) {
        var apiResponseDataPojo = new ApiResponsePojo();

        this.productService.disableOneById(productId);
        apiResponseDataPojo.setMessage("Product disabled");
        apiResponseDataPojo.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(apiResponseDataPojo, HttpStatusCode.valueOf(apiResponseDataPojo.getStatusCode()));
    }
}
