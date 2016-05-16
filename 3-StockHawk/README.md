# Stock Hawk
Project 3 of the Android Nanodegree at Udacity. This document contains an overview of the app, and how I satisfied each of the rubric requirements.

I was having a lot of trouble getting the provided starter code to build cleanly, and was also finding it pretty tough to work with, so I decided to reimplement the app from a fresh project, incorporating tons of layouts and other functionality from the starter code, as well as from Sunshine.

#### Each stock quote on the main screen is clickable and leads to a new screen which graphs the stock's value over time.

Each stock's historical closing prices (weekly for the last two years) are stored as a giant text blob in a column of my content provider. Probably not the best way to do it, but it's a pretty small amount of data, all told.

An `onClickListener` is set in `StockViewHolder`, which delegates its click to `MainActivity`, which then gets a URI for the stock in question, attaches it to an explicit intent to launch the `GraphActivity`, and fires the intent. The `GraphActivity` uses the MPAndroidChart library to plot the data.

#### Stock Hawk does not crash when a user searches for a non-existent stock.

The user's stocks of interest are stored as a set of strings in the user's shared preferences (as managed by `PrefUtils`). Invalid symbols are added to that set without complaint, however, in `QuoteSyncJob`, when an invalid symbol is encountered, it is removed from shared preferences, and a toast is displayed to the user, informing them that the symbol was invalid.

#### Stock Hawk Stocks can be displayed in a collection widget.

Done and done, with a nice preview image as well.

#### Stock Hawk app has content descriptions for all buttons.

Well navigating around my app using TalkBack was about the most annoying thing in the universe, but it seems like it works pretty well, except for the plot, which at least describes that it is.

#### Stock Hawk app supports layout mirroring using both the RTL attribute and the start/end tags.

The RTL attribute is set, and start/end are used everywhere they can be. There are a couple things I'm not sure about, like currency and percentage formatting, however those are being handled using the system formatters, so I suppose they must be doing the right thing. I'm also not sure if the X-axes on the plot should be reversed. I don't think so?

#### Strings are all included in the strings.xml file and untranslatable strings have a translatable tag marked to false.

Done and done.


