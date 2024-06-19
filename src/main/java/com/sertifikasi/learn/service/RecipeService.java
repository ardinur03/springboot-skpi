package com.sertifikasi.learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.context.annotation.Lazy;
import lib.minio.MinioSrvc;

import com.sertifikasi.learn.dto.request.recipes.CreateRecipeRequest;
import com.sertifikasi.learn.dto.response.ApiResponse;
import com.sertifikasi.learn.exception.MinioUploadException;
import com.sertifikasi.learn.model.Category;
import com.sertifikasi.learn.model.Level;
import com.sertifikasi.learn.model.Recipe;
import com.sertifikasi.learn.model.User;
import com.sertifikasi.learn.repository.CategoryRepository;
import com.sertifikasi.learn.repository.LevelRepository;
import com.sertifikasi.learn.repository.RecipeRepository;
import com.sertifikasi.learn.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lib.i18n.utility.MessageUtil;
import java.sql.Timestamp;

@Service
public class RecipeService {
        @Autowired
        private RecipeRepository recipeRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private LevelRepository levelRepository;

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ValidationService validationService;

        // private FavoriteFoodRepository favoriteRepo;

        @Lazy
        @Autowired
        private MinioSrvc minioService;

        @Autowired
        private MessageUtil messageUtil;

        @Transactional
        public ApiResponse create(CreateRecipeRequest request, MultipartFile imageFile, int userId) {
                String iamgeFile;
                validationService.validate(request);

                User createdByUser = userRepository.findById(userId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                messageUtil.get("message.error.user.not-found", userId)));

                Category categories = categoryRepository.findById(request.getCategories().getCategoryId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                messageUtil.get("message.error.category.not-found",
                                                                request.getCategories().getCategoryId())));

                Level levels = levelRepository.findById(request.getLevels().getLevelId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                messageUtil.get("message.error.level.not-found",
                                                                request.getLevels().getLevelId())));

                try {
                        iamgeFile = minioService.uploadImageToMinio(request, imageFile);
                } catch (java.io.IOException e) {
                        String errorMessage = messageUtil.get("message.error.upload.minio");
                        throw new MinioUploadException(errorMessage, e);
                }

                Recipe newRecipe = Recipe.builder()
                                .users(createdByUser)
                                .categories(categories)
                                .levels(levels)
                                .recipeName(request.getRecipeName())
                                .imageFilename(iamgeFile)
                                .timeCook(request.getTimeCook())
                                .ingridient(request.getIngredient())
                                .howToCook(request.getHowToCook())
                                .createdBy(createdByUser.getUsername())
                                .modifiedBy(createdByUser.getUsername())
                                .isDeleted(false)
                                .createdTime(new Timestamp(System.currentTimeMillis()))
                                .modifiedTime(new Timestamp(System.currentTimeMillis()))
                                .build();

                recipeRepository.save(newRecipe);

                String responseMessage = messageUtil.get("message.success.add.resep", request.getRecipeName());
                int statusCode = HttpStatus.OK.value();
                String status = HttpStatus.OK.getReasonPhrase();

                return new ApiResponse(responseMessage, statusCode, status);
        }

}
