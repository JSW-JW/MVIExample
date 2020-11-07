# Today I Learned

* MVI(Intent based Architecture based on MVVM to prevent viewmodel from being clattered by multiple fragments.)
* ViewState Class that wraps around all of the data model for our views.
* To ensure that any Activity implements interface given from its Fragments, cast the context inside onAttach method as the Interface class. if it's not implemented, pass it to catch block. This will prevent app from crashing.


# Confusing things

* how is the blogPosts constructed inside dataState?

# Confusing concept that are gonna be removed.

* Why the flow of data is 'setEventState' -> 'receive dataState' -> 'set viewState' -> 'observe viewState' ? Why not directly set the viewState and detect the change?
    This is because we use wrapper class for the dataState. We can deal with various situation and give better user experience using that kind of wrapper class.
    
    DataState class has 3 cases : DataState.data(success case), DataState.loading, DataState.error