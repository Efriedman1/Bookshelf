package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface{

    ArrayList<Book> bookArrayList;
    BookDetailsFragment bookDetailsFragment;

    boolean container2present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container2present = findViewById(R.id.container_2) != null;//true

        bookArrayList = new ArrayList<>();

        bookArrayList.add(new Book("How to Win Friends and Influence People", "Dale Carnegie"));
        bookArrayList.add(new Book("The Psychology of Selling", "Brian Tracy"));
        bookArrayList.add(new Book("Rich Dad, Poor Dad", "Robert Kiyosaki"));
        bookArrayList.add(new Book("Rich Woman", "Kim Kiyosaki"));
        bookArrayList.add(new Book("Thinking, Fast and Slow", "Daniel Kahneman"));
        bookArrayList.add(new Book("The 4-Hour Workweek", "Timothy Ferriss"));
        bookArrayList.add(new Book("The $100 Startup", "Chris Gillebeau"));
        bookArrayList.add(new Book("Click Millionaires", "Scott Fox"));
        bookArrayList.add(new Book("The E-Myth Revisited", "Michael E. Gerber"));
        bookArrayList.add(new Book("Crush It!", "Gary Vaynerchuk"));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_1, BookListFragment.newInstance(bookArrayList))
                .commit();

        if(container2present){
            bookDetailsFragment = new BookDetailsFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_2, bookDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void bookClicked(int position) {
        if(!container2present) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_1, BookDetailsFragment.newInstance(bookArrayList.get(position)))
                    .commit();
        }
        else{
            bookDetailsFragment.changeBook(bookArrayList.get(position));
        }
    }
}