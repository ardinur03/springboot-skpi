package com.sertifikasi.learn.controller.bookrecipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sertifikasi.learn.dto.request.recipes.CreateRecipeRequest;
import com.sertifikasi.learn.dto.response.ApiResponse;
import com.sertifikasi.learn.service.RecipeService;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/book-recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PostMapping(path = "/book-recipes", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createRecipe(
            @RequestPart("request") CreateRecipeRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        int userId = request.getUserId();
        ApiResponse response = recipeService.create(request, file, userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
