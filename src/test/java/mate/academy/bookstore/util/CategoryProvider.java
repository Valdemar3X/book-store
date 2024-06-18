package mate.academy.bookstore.util;

import mate.academy.bookstore.dto.category.CategoryDto;

public class CategoryProvider {
    public static final String TEST_CREATE_CATEGORY_NAME = "test";
    public static final String TEST_UPDATE_CATEGORY_NAME = "Updated Category";

    public static CategoryDto createCategoryDto() {
        return new CategoryDto().setName(TEST_CREATE_CATEGORY_NAME);
    }

    public static CategoryDto updateCategoryDto() {
        return new CategoryDto().setName(TEST_UPDATE_CATEGORY_NAME);
    }
}
