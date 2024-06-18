package mate.academy.bookstore.util;

import java.math.BigDecimal;
import java.util.Set;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.Category;

public class BookProvider {
    private static final String TEST_CREATE_BOOK_TITLE = "Harry Potter";
    private static final String TEST_CREATE_BOOK_AUTHOR = "Joanne Rowling";
    private static final String TEST_CREATE_BOOK_ISBN = "4321";
    private static final String TEST_UPDATE_BOOK_TITLE = "Updated Book";
    private static final String TEST_UPDATE_BOOK_AUTHOR = "Updated Author";
    private static final String TEST_UPDATE_BOOK_ISBN = "1234";

    public static CreateBookRequestDto createRequestDto(Long id) {
        return new CreateBookRequestDto()
                .setTitle(TEST_CREATE_BOOK_TITLE)
                .setAuthor(TEST_CREATE_BOOK_AUTHOR)
                .setIsbn(TEST_CREATE_BOOK_ISBN)
                .setPrice(new BigDecimal(10))
                .setCategoryIds(Set.of(id));
    }

    public static Book createBook(CreateBookRequestDto requestDto, Category category) {
        return new Book()
                .setTitle(requestDto.getTitle())
                .setAuthor(requestDto.getAuthor())
                .setIsbn(requestDto.getIsbn())
                .setPrice(requestDto.getPrice())
                .setCategories(Set.of(category));
    }

    public static CreateBookRequestDto createRequestDtoForUpdate(Long id) {
        return new CreateBookRequestDto()
                .setTitle(TEST_UPDATE_BOOK_TITLE)
                .setAuthor(TEST_UPDATE_BOOK_AUTHOR)
                .setIsbn(TEST_UPDATE_BOOK_ISBN)
                .setPrice(new BigDecimal(22))
                .setCategoryIds(Set.of(id));
    }

    public static Book updateBook(CreateBookRequestDto requestDto, Category category) {
        return new Book()
                .setTitle(requestDto.getTitle())
                .setAuthor(requestDto.getAuthor())
                .setIsbn(requestDto.getIsbn())
                .setPrice(requestDto.getPrice())
                .setCategories(Set.of(category));
    }
}
