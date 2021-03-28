package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CategoriesListResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryList;
import com.upgrad.FoodOrderingApp.api.model.CategoryListResponse;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategories() {

        List<CategoryEntity> categoryEntities = categoryService.getAllCategories();
        List<CategoryListResponse> categoryListResponses = new ArrayList<>();

        for (CategoryEntity c : categoryEntities) {
            CategoryListResponse temp = new CategoryListResponse();
            temp.setCategoryName(c.getCategoryName());
            temp.setId(c.getUuid());
            categoryListResponses.add(temp);
        }
        CategoriesListResponse categoriesListResponse = new CategoriesListResponse();
        categoriesListResponse.setCategories(categoryListResponses);

        return new ResponseEntity<>(categoriesListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getCategoryById(@PathVariable(name = "category_id") String categoryId) throws CategoryNotFoundException
    {
        if(categoryId.replace(" ", "").equals("")) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryService.getCategoryById(categoryId);

        if(categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

        List<CategoryListResponse> categoryListResponses = new ArrayList<>();

        CategoryListResponse temp = new CategoryListResponse();
        temp.setCategoryName(categoryEntity.getCategoryName());
        temp.setId(categoryEntity.getUuid());
        categoryListResponses.add(temp);
        CategoriesListResponse categoriesListResponse = new CategoriesListResponse();
        categoriesListResponse.setCategories(categoryListResponses);

        return new ResponseEntity<>(categoriesListResponse, HttpStatus.OK);
    }
}
