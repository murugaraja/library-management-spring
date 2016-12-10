/**
 *
 */
package edu.sjsu.cmpe275.lms.service;

import edu.sjsu.cmpe275.lms.dao.BookDao;
import edu.sjsu.cmpe275.lms.entity.Book;
import edu.sjsu.cmpe275.lms.entity.LibUserBook;
import edu.sjsu.cmpe275.lms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * @author dhanyaramesh
 */

@Service
public class BookServiceImpl implements BookService {

	/* (non-Javadoc)
     * @see edu.sjsu.cmpe275.lms.service.BookService#listBooks()
	 */

    @Autowired
    BookDao bookDao;

    @Autowired
    UserBookService userBookService;

    @Override
    public List<Book> listBooks() {
        return bookDao.findAll();
    }

    /* (non-Javadoc)
     * @see edu.sjsu.cmpe275.lms.service.BookService#findBook()
     */
    @Override
    public Book findBook(String isbn) {
        // TODO Auto-generated method stub
        Book book = bookDao.getBookByISBN(isbn);
        return book;
    }

    /* (non-Javadoc)
     * @see edu.sjsu.cmpe275.lms.service.BookService#requestBook(java.lang.Integer)
     */
    @Override
    public String requestBook(Integer bookId, Integer userId) throws ParseException {
        // TODO Auto-generated method stub
        return bookDao.setBookRequest(bookId, userId);

    }

    @Override
    public Book findBookById(Integer bookId) {
        return bookDao.getBookbyId(bookId);
    }

    @Override
    public List<Book> listBooksOfUser(Integer userId) {

        List<Book> books = bookDao.getBookByUserId(userId);


        return books;

    }

    @Override
    public String returnBook(Integer bookId, Integer userId) {
        return bookDao.setBookReturn(bookId, userId);

    }

    @Override
    public List<Book> searchBookbyUser(Book book) {

        return bookDao.searchBook(book);
    }

    @Override
    public boolean deleteBookByID(Integer id) {
        /*A book cannot be deleted if it’s checked out by a patron.*/
        if (!userBookService.checkIfEsxists(id)) {
            System.out.println("**************** Book does not exist, can delete safely. \n\r Now removing the waitlist entry for the same");

            return bookDao.deleteBookByID(id);
        } else {
            return false;
        }
    }

    @Override
    public String getAvailableBookCount() {
        return bookDao.findCountAvailable();
    }

    @Override
    public Book updateBooks(Book updatedbook, HttpServletRequest request) {
        return bookDao.updateBooks(updatedbook, request);
    }

    @Override
    public Book getBookByISBN(String isbn) {
        return bookDao.getBookByISBN(isbn);
    }

    /**
     * Add a book to database
     *
     * @param isbn                10 or 13 digit ISBN code, must be uniqur
     * @param author              Author of the book
     * @param title               Title of the book, must be unique
     * @param callnumber          Call Number
     * @param publisher           Publisher of the book
     * @param year_of_publication Year of publication
     * @param location            Location of the book in library
     * @param num_of_copies       Number of copies
     * @param current_status      Current Status
     * @param keywords            Keywords
     * @param image               Bytes as image
     * @param user
     * @return true if add successful, false if failed
     */
    @Override
    public boolean addBook(String isbn, String author, String title, String callnumber, String publisher, String year_of_publication, String location, int num_of_copies, String current_status, String keywords, byte[] image, User user) {
        return bookDao.addBook(isbn, author, title, callnumber, publisher, year_of_publication, location, num_of_copies, current_status, keywords, image, user);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<LibUserBook> getAllLibUserBook() {
        return bookDao.getAllLibUserBook();
    }
}
