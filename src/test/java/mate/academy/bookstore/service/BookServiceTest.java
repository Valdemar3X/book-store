package mate.academy.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.bookstore.dto.book.BookDto;
import mate.academy.bookstore.dto.book.CreateBookRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.BookMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.book.BookRepository;
import mate.academy.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeAll;
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
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final Long BOOK_ID = 1L;
    private static final Long INCORRECT_BOOK_ID = 100L;
    private static Book book;
    private static CreateBookRequestDto requestDto;
    private static BookDto expectedDto;
    private static List<Book> books;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeAll
    static void beforeAll() {
        book = new Book().setId(BOOK_ID)
                .setTitle("TestBook 1")
                .setAuthor("TestAuthor 1")
                .setPrice(BigDecimal.valueOf(20.00))
                .setIsbn("1234");
        requestDto = new CreateBookRequestDto()
                 .setTitle("TestBook 1")
                .setAuthor("TestAuthor 1")
                .setPrice(BigDecimal.valueOf(20.00))
                .setIsbn("1234");
        expectedDto = new BookDto().setId(BOOK_ID)
                .setTitle("TestBook 1")
                .setAuthor("TestAuthor 1")
                .setPrice(BigDecimal.valueOf(20.00))
                .setIsbn("1234");
        books = List.of(book, new Book().setId(BOOK_ID)
                .setTitle("TestBook 2")
                .setAuthor("TestAuthor 2")
                .setPrice(BigDecimal.valueOf(30.00))
                .setIsbn("12345"));
    }

    @Test
    @DisplayName("""
            Verify the book was saved correct
            """)
    public void createBook_WithValidCreateBookRequestDto_ReturnValidBookDto() {
        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        BookDto actualDto = bookService.save(requestDto);

        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);

        verify(bookMapper, times(1)).toModel(requestDto);
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookMapper, times(1)).toDto(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("""
            Verify the all books were return from page
            """)
    public void getAll_WithPageable_ReturnAllBookDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        List<BookDto> expectedBookDtos = books.stream()
                .map(bookMapper::toDto)
                .toList();
        List<BookDto> actualBookDtos = bookService.getAll(pageable);

        assertEquals(expectedBookDtos, actualBookDtos);

        verify(bookRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("""
            Verify the correct book was returned when book exists
            """)
    public void findBook_WithValidBookId_ReturnValidBookDto() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        BookDto actual = bookService.getById(BOOK_ID);

        EqualsBuilder.reflectionEquals(expectedDto, actual);

        verify(bookRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("""
            Verify the exception was return when id of book is incorrect
            """)
    public void findBook_WithNoExistingBookId_ShouldThrowException() {
        when(bookRepository.findById(INCORRECT_BOOK_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.getById(INCORRECT_BOOK_ID)
        );

        String expected = "Can't find book by id: " + INCORRECT_BOOK_ID;
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(bookRepository, times(1)).findById(INCORRECT_BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("""
            Verify the book was updated when book exists
            """)
    public void updateBook_WithValidBookId_ReturnValidBookDto() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        when(bookMapper.toModel(requestDto)).thenReturn(new Book());
        when(bookMapper.toDto(any())).thenReturn(new BookDto());

        BookDto actual = bookService.updateBookById(BOOK_ID, requestDto);

        assertNotNull(actual);
    }

}
