# Popular Movies - Stage 1

This project is part of [**Android Developer Nanodegree Scholarship Program**](https://www.udacity.com/google-scholarships) by
**Udacity** and **Google**. The project's purpose is to build an app that retrieves
movie data from The Movie Database API [TMDb](https://www.themoviedb.org/documentation/api).
## Features
The first part of the project presents the users a grid arrangement of movie elements each including the movie poster, title and genres. The user may choose through a _BottomNavigationView_ to retrieve either Popular or Top Rated movies.
Selecting a movie will open a _Movie Info_ screen with more detailed
information like: movie title, overview, release date, genres and user ratings.
## Instructions
After creating a free account you need to request a API KEY from [TMDb](https://www.themoviedb.org/documentation/api). For more instructions see the [API FAQs](https://www.themoviedb.org/faq/api). Then find the _**TODO**_ in:
_**/networking/NetworkUtils.java**_ file and replace the empty string with your API KEY:
```
// TODO Enter your API KEY here
    private static final String API_KEY_STRING = "";
```
## Libraries
The project uses the following libraries:
* [Butter Knife](http://jakewharton.github.io/butterknife/)
* [Picasso](http://square.github.io/picasso/)
