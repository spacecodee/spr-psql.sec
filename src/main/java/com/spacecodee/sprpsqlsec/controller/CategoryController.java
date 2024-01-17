package com.spacecodee.sprpsqlsec.controller;

import com.spacecodee.sprpsqlsec.data.dto.CategoryDto;
import com.spacecodee.sprpsqlsec.data.pojo.ApiResponseDataPojo;
import com.spacecodee.sprpsqlsec.data.pojo.ApiResponsePojo;
import com.spacecodee.sprpsqlsec.data.vo.SaveCategoryVo;
import com.spacecodee.sprpsqlsec.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR')")
    @GetMapping()
    public ResponseEntity<ApiResponseDataPojo<Page<CategoryDto>>> findAll(Pageable pageable) {
        var categoryPage = this.categoryService.findAll(pageable);
        var apiResponseDataPojo = new ApiResponseDataPojo<Page<CategoryDto>>();

        apiResponseDataPojo.setData(categoryPage);
        apiResponseDataPojo.setHttpStatus(HttpStatus.BAD_REQUEST);

        if (categoryPage.hasContent()) {
            apiResponseDataPojo.setHttpStatus(HttpStatus.OK);
            apiResponseDataPojo.setMessage("Category list");
            return new ResponseEntity<>(apiResponseDataPojo, HttpStatusCode.valueOf(apiResponseDataPojo.getStatusCode()));
        }

        apiResponseDataPojo.setMessage("Category list isn't found");
        return new ResponseEntity<>(apiResponseDataPojo, HttpStatusCode.valueOf(apiResponseDataPojo.getStatusCode()));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDataPojo<CategoryDto>> findOneById(@PathVariable long categoryId) {
        var apiResponseDataPojo = new ApiResponseDataPojo<CategoryDto>();
        var categoryDto = this.categoryService.findOneById(categoryId);

        apiResponseDataPojo.setMessage("Product found");
        apiResponseDataPojo.setData(categoryDto);
        apiResponseDataPojo.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponseDataPojo);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping()
    public ResponseEntity<ApiResponsePojo> createOne(@RequestBody @Valid SaveCategoryVo saveCategoryVo) {
        var apiResponseDataPojo = new ApiResponsePojo();

        this.categoryService.createOne(saveCategoryVo);
        apiResponseDataPojo.setMessage("Product created");
        apiResponseDataPojo.setHttpStatus(HttpStatus.CREATED);
        return new ResponseEntity<>(apiResponseDataPojo, HttpStatusCode.valueOf(apiResponseDataPojo.getStatusCode()));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponsePojo> updateOneById(@PathVariable long categoryId, @RequestBody @Valid SaveCategoryVo saveCategoryVo) {
        var apiResponseDataPojo = new ApiResponsePojo();

        this.categoryService.updateOneById(categoryId, saveCategoryVo);
        apiResponseDataPojo.setMessage("Product updated");
        apiResponseDataPojo.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(apiResponseDataPojo, HttpStatusCode.valueOf(apiResponseDataPojo.getStatusCode()));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{categoryId}/disabled")
    public ResponseEntity<ApiResponsePojo> disableOneById(@PathVariable long categoryId) {
        var apiResponseDataPojo = new ApiResponsePojo();

        this.categoryService.disableOneById(categoryId);
        apiResponseDataPojo.setMessage("Product disabled");
        apiResponseDataPojo.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(apiResponseDataPojo, HttpStatusCode.valueOf(apiResponseDataPojo.getStatusCode()));
    }
}
