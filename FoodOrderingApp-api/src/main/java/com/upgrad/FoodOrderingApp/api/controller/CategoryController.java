package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
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
import java.util.UUID;

@RestController
@RequestMapping

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategoriesOrderedByName() {

        List<CategoryEntity> categoryEntities = categoryService.getAllCategoriesOrderedByName();
        List<CategoryListResponse> categoryListResponses = new ArrayList<>();

        for (CategoryEntity c : categoryEntities) {
            CategoryListResponse temp = new CategoryListResponse();
            temp.setCategoryName(c.getCategoryName());
            temp.setId(UUID.fromString(c.getUuid()));
            categoryListResponses.add(temp);
        }
        CategoriesListResponse categoriesListResponse = new CategoriesListResponse();
        categoriesListResponse.setCategories(categoryListResponses.size() > 0 ? categoryListResponses : null);

        return new ResponseEntity<>(categoriesListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryDetailsResponse> getCategoryById(@PathVariable(name = "category_id") String categoryId) throws CategoryNotFoundException
    {
        if(categoryId.replace(" ", "").equals("")) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryService.getCategoryById(categoryId);

        if(categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

        CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse();
        List<ItemList> itemLists = new ArrayList<>();
        for(ItemEntity i : categoryEntity.getItems()) {
            ItemList temp = new ItemList();
            temp.setId(i.getUuid());
            Integer tempEnumIndex = i.getType().equals('0') ? 0 : 1;
            temp.setItemType(ItemList.ItemTypeEnum.values()[tempEnumIndex]);
            temp.setPrice(i.getPrice());
            temp.setItemName(i.getItemName());
            itemLists.add(temp);
        }
        categoryDetailsResponse.setId(UUID.fromString(categoryEntity.getUuid()));
        categoryDetailsResponse.setCategoryName(categoryEntity.getCategoryName());
        categoryDetailsResponse.setItemList(itemLists);

        return new ResponseEntity<>(categoryDetailsResponse, HttpStatus.OK);
    }
}
