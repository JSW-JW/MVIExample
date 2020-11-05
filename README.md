# Today I Learned

* MVI(Intent based Architecture based on MVVM to prevent viewmodel from being clattered by multiple fragments.)
* ViewState Class that wraps around all of the data model for our views.


# Confusing things

* how is the blogPosts constructed inside dataState?

# Confusing concept that are gonna be removed.

* Why the flow of data is 'setEventState' -> 'receive dataState' -> 'set viewState' -> 'observe viewState' ? Why not directly set the viewState and detect the change?
    This is because we use wrapper class for the dataState. 