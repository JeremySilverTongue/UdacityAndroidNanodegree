Project is in the 1-PopularMovies Folder

Note that to build the project you'll need to add your API key as a string resource called mdbAPIKey

# Change Log

My first submission set the number of grid columns to floor(screenWidth/200), which meant that on sufficiently small phones, it looked like it was just a list. I have fixed this by setting the minimum number of columns to 2. I also fixed a ton of scrolling issues, tightened up the grid, and added landscape support to both activities. I'll address the other issues raised in the first evaluations.

## I think you have a great UI but currently it displays as 1 column and 1 row in the phone mode. A grid layout usually includes multi row and column layout and I encourage you to expand the number of rows and columns to make better use of space in your view.

Fixed. Current scheme is columns = max(2, floor(screenWidth/200)), which mean 2 columns on my phone in portrait, 3 columns on my phone in landscape, 4 on my tablet in portrait mode, and 6 on my tablet in landscape.

## Great job saving and restoring the scroll position.

There was actually a bug here. I was using findFirstCompletelyVisibleItemPosition(), which yeilds -1 if no item is completely visible. Switching to findFirstVisibleItemPosition fixed that issue.

## Consider raising your minSDK between 14-16. This way you use android's newer features as well as target 95% of the android user base.

Keeping minSDK at 10. I am part of the 5% :P Ancient Droid X for the win.

## Want to know a neat tip to improve your app's performance? Check whether your phone has network connectivity before creating an async task.

Fair. Sounds like something that'll be addressed in Advanced Android Development, when we're doing all the productionizing stuff. There's very little error checking as it stands...

## Instead of just having a portrait mode, consider allowing landscape orientation and the ability to use the additional device space to display more posters.

Fixed

# Rubric

## Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails:

Yep.

## UI contains an element (i.e a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated

As a settings screen. The RecyclerView adapter gets added as an observer of the corresponding preference.

## UI contains a screen for displaying the details for a selected movie

Fo sho.

## Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.

Check, check, check, check, check. Been wanting to use RatingBar for forever.

## When a user changes the sort criteria (“most popular and highest rated”) the main view gets updated correctly.

Check. When the preference changes, the adapter sorts its backing list, and yells about a changed dataset.

## When a movie poster thumbnail is selected, the movie details screen is launched

These categories seem a little redundant... Yep, this works too. Getting click listeners working with a recycler view is a little weird though.

## App queries the /discover/movies api in a background thread to retrieve movies for main activity, sorted (from left to right) by criteria selected in the settings menu. This query can also be used to fetch the related metadata needed for the detail view.

Uhh. I mean for sure it's happening in a background thread. I'm using a nice wrapper library: https://github.com/holgerbrandl/themoviedbapi/

It gets a list of nice movieDb objects, which I hold on to and sort as needed. To launch the detail activity, I just pass the movie of interest as a serializable intent extra.

# General Android Requirements

## App does not redefine the expected function of a system icon (such as the Back button).

I didn't touch any of that stuff, so this should be fine.

## App does not redefine or misuse Android UI patterns, such that icons or behaviors could be misleading or confusing to users.

Again, not smart enough to break any of this.

## App supports standard system Back button navigation and does not make use of any custom, on-screen "Back button" prompts.

Why reinvent the wheel, right?

## All dialogs are dismissible using the Back button.

Don't use any dialogs, and if I did, I'd use the built in dialog class :P

## Pressing the Home button at any point navigates to the Home screen of the device.

How do you even catch this event?

## App does not redefine or misuse Android UI patterns, such that icons or behaviors could be misleading or confusing to users.

Didn't I already address this? No problems here.

## App does not request permissions to access sensitive data or services that can cost the user money, unless related to a core capability of the app.

Just needs internet access.

##App correctly preserves and restores user or app state, that is , student uses a bundle to save app state and restores it via onSaveInstanceState/onRestoreInstanceState. For example,

## When a list item is selected, it remains selected on rotation.

N/A, but the scroll position is stored.

## When an activity is displayed, the same activity appears on rotation.

I mean Android handles this bit.

## User text input is preserved on rotation.

N/A

## Maintains list items positions on device rotation.

Not sure what this means, but the scroll position is stored.

##When the app is resumed after the device wakes from sleep (locked) state, the app returns the user to the exact state in which it was last used.

Again, this is Android's problem, not mine.

## When the app is relaunched from Home or All Apps, the app restores the app state as closely as possible to the previous state.

Rgr.

## App does not crash, force close, freeze, or otherwise function abnormally on any targeted device.

I didn't exactly test this in a ton of places, but I think we're good?

## All content is safe for work content.

I just display what The MovieDB supplies.

## App adheres to the Google Play Store App policies.

Ain't nobody got time to read all that. I think I'm good though.

## App’s code follows standard Java/Android Style Guidelines.

Ugh. Kinda. There are plenty of places to ding me here.


