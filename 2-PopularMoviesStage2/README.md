Project is in the 2-PopularMoviesStage2 folder.

Note that to build the project you'll need to add your API to the gradle.properties file under the name `MyMovieDbApiKey`:

    MyMovieDbApiKey="..."

# Rubric

I've included some annotation on rubric items of interest

##### When a movie poster thumbnail is selected, the movie details screen is launched [Phone] or displayed in a fragment [Tablet].

I decided to make the layout switch based simply on orientation, as that made for easier testing. To allow the UI to change based on the shorted dimension of the devices's screen, I would move the layouts in `layout-land` to `layout-sw600dp`.

##### In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite.

I also added favorite buttons to the grid view. An option question is how to keep the button in the details fragment synced with the one in the grid view...

##### In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.

I found this item confusing. In part one I just grabbed the now playing movies, and sorted them myself.
