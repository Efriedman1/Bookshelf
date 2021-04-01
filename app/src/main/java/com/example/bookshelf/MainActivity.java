package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookSelectedInterface {

    FragmentManager fm;

    boolean twoPane;
    BookDetailsFragment bookDetailsFragment;
    Book selectedBook;
    private final String KEY_SELECTED_BOOK = "selectedBook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fetch selected book if there was one
        if (savedInstanceState != null)
            selectedBook = savedInstanceState.getParcelable(KEY_SELECTED_BOOK);

        twoPane = findViewById(R.id.container2) != null;

        fm = getSupportFragmentManager();

        Fragment fragment1;
        fragment1 = fm.findFragmentById(R.id.container1);


        // At this point, I only want to have BookListFragment be displayed in container_1
        if (fragment1 instanceof BookDetailsFragment) {
            fm.popBackStack();
        } else if (!(fragment1 instanceof BookListFragment))
            fm.beginTransaction()
                    .add(R.id.container1, BookListFragment.newInstance(getTestBooks()))
            .commit();

        /*
        If we have two containers available, load a single instance
        of BookDetailsFragment to display all selected books
         */
        bookDetailsFragment = (selectedBook == null) ? new BookDetailsFragment() : BookDetailsFragment.newInstance(selectedBook);
        if (twoPane) {
            fm.beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();
        } else if (selectedBook != null) {
            /*
            If a book was selected, and we now have a single container, replace
            BookListFragment with BookDetailsFragment, making the transaction reversible
             */
            fm.beginTransaction()
                    .replace(R.id.container1, bookDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    /*
    Generate an arbitrary list of "books" for testing
     */
    private BookList getTestBooks() {
        BookList books = new BookList();
        Book book;

        books.add(book = new Book("How to Win Friends and Influence People", "Dale Carnegie"));
        books.add(book = new Book("The Psychology of Selling", "Brian Tracy"));
        books.add(book = new Book("Rich Dad, Poor Dad", "Robert Kiyosaki"));
        books.add(book = new Book("Rich Woman", "Kim Kiyosaki"));
        books.add(book = new Book("Thinking, Fast and Slow", "Daniel Kahneman"));
        books.add(book = new Book("The 4-Hour Workweek", "Timothy Ferriss"));
        books.add(book = new Book("The $100 Startup", "Chris Gillebeau"));
        books.add(book = new Book("Click Millionaires", "Scott Fox"));
        books.add(book = new Book("The E-Myth Revisited", "Michael E. Gerber"));
        books.add(book = new Book("Crush It!", "Gary Vaynerchuk"));
//        for (int i = 1; i <= 10; i++) {
//            books.add(book = new Book("Book" + i, "Author" + i));
//        }
        return books;
    };

    @Override
    public void bookSelected(int index) {
        //Store the selected book to use later if activity restarts
        selectedBook = getTestBooks().get(index);

        if (twoPane)
            /*
            Display selected book using previously attached fragment
             */
            bookDetailsFragment.displayBook(selectedBook);
        else {
            /*
            Display book using new fragment
             */
            fm.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment.newInstance(selectedBook))
                    // Transaction is reversible
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SELECTED_BOOK, selectedBook);
    }

    @Override
    public void onBackPressed() {
        // If the user hits the back button, clear the selected book
        selectedBook = null;
        super.onBackPressed();
    }
}
