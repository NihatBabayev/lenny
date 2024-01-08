package com.example.lenny.config;

import com.example.lenny.entity.Category;
import com.example.lenny.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeCategories();
    }

    private void initializeCategories() {
        List<String> categoryNames = Arrays.asList("electronics", "fashion", "action figure", "book", "gaming");

        for (String categoryName : categoryNames) {
            Category category = new Category();
            category.setName(categoryName);
            categoryRepository.save(category);
        }
    }
}