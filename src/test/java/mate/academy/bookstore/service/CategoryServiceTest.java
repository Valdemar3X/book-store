package mate.academy.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import mate.academy.bookstore.dto.category.CategoryDto;
import mate.academy.bookstore.dto.category.CreateCategoryRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.CategoryMapper;
import mate.academy.bookstore.model.Category;
import mate.academy.bookstore.repository.category.CategoryRepository;
import mate.academy.bookstore.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    private static final Long CATEGORY_ID = 1L;
    private static final Long INCORRECT_CATEGORY_ID = 100L;
    private static Category category;
    private static CreateCategoryRequestDto requestDto;
    private static CategoryDto expectedDto;
    private static List<Category> categories;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeAll
    static void beforeAll() {
        category = new Category();
        requestDto = new CreateCategoryRequestDto();
        expectedDto = new CategoryDto();
        categories = Arrays.asList(
                new Category()
                        .setId(CATEGORY_ID)
                        .setName("Fantasy"),
                new Category()
                        .setId(2L)
                        .setName("Fiction"));
    }

    @BeforeEach
    void setup() {
        category.setId(CATEGORY_ID)
                .setName("Fantasy");
        requestDto.setName("Fantasy");
        expectedDto.setId(CATEGORY_ID)
                .setName("Fantasy");
    }

    @Test
    @DisplayName("""
            Verify the category was saved correct
            """)
    public void createCategory_WithValidCreateCategoryRequestDto_ReturnValidCategoryDto() {
        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expectedDto);

        CategoryDto actual = categoryService.save(requestDto);

        assertNotNull(actual);

        verify(categoryMapper, times(1)).toModel(requestDto);
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryMapper, times(1)).toDto(category);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("""
            Verify the all categories were return from page
            """)
    public void getAll_WithPageable_ReturnAllCategoryDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        List<CategoryDto> expectedBookDtos = categories.stream()
                .map(categoryMapper::toDto)
                .toList();
        List<CategoryDto> actualBookDtos = categoryService.getAll(pageable);

        assertEquals(expectedBookDtos, actualBookDtos);

        verify(categoryRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("""
            Verify the exception was return when id of category is incorrect
            """)
    public void findCategory_WithNoExistingCategoryId_ShouldThrowException() {
        when(categoryRepository.findById(INCORRECT_CATEGORY_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.getById(INCORRECT_CATEGORY_ID)
        );

        String expected = "Can't find category by id: " + INCORRECT_CATEGORY_ID;
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(categoryRepository, times(1)).findById(INCORRECT_CATEGORY_ID);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("""
            Verify the category was deleted by id
            """)
    public void deleteCategory_WithValidCategoryId_Success() {
        doNothing().when(categoryRepository).deleteById(CATEGORY_ID);

        assertAll(() -> categoryService.deleteById(CATEGORY_ID));

        verify(categoryRepository, times(1)).deleteById(CATEGORY_ID);
        verifyNoMoreInteractions(categoryRepository);
    }

}
