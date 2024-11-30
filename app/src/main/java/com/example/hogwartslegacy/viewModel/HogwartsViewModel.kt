/*
   The viewModel contains the main business logic of our application, it is the class which inherits form
   the viewmodel class. Here the json objects are get converted into the kotlin objects and send it to the
   composable(UI) functions.
 */

package com.example.hogwartslegacy.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hogwartslegacy.data.HogwartsData
import com.example.hogwartslegacy.data.HogwartsDataItem
import com.example.hogwartslegacy.service.ApiService
import com.example.hogwartslegacy.service.hogwartsService
import kotlinx.coroutines.launch

class HogwartsViewModel: ViewModel() {

/*
it creates the mutable state holder for the hogwartsState(), _state represent the private mutable variable.
state create a public read-only version of the state, State<> is a immutable interface that the mutablestate implements
 */

    private val _state = mutableStateOf(HogwartsState())
    val state: State<HogwartsState> = _state

 /*
it is a constructor of the viewModel class.
*/
            init {
             fetchCharacter()
            }

/*
This function fetches the details of the characters from the api and send it to the composables.
1) viewModelScope.launch
This starts a coroutine in the viewModelScope, which is provided by the ViewModel.
This scope is tied to the lifecycle of the ViewModel, meaning the coroutine will be
automatically canceled if the ViewModel is cleared.

The reason to use launch is to perform the network call asynchronously without blocking
the main thread (keeping the UI responsive).

2)val characters
This line makes a network call (using hogwartsService)
to fetch all characters from the Hogwarts API. The function getAllCharacters() is assumed to return a list of characters.
 */
    private fun fetchCharacter() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            try {
                val characters = hogwartsService.getAllCharacters()
                _state.value = _state.value.copy(
                    characters = characters,
                    loading = false
                )
            } catch (e:Exception){
                    _state.value = _state.value.copy(
                        loading = false,
                        error = "${e.message}"
                    )
            }
        }
    }



/*
   this fun get the details of the characters which takes the parameter of index and return the details of the character
   return the state's value of character and get that index, if exists then the let function part get executed, if not then
   it terminates
 */
    fun getCharactersDetails(index: Int): CharacterDetails?{
        return _state.value.characters.getOrNull(index)?.let { character ->
            CharacterDetails(
                name = character.name,
                image = character.image,
                actor = character.actor
            )
        }
    }
}

/*
This data class used to represent the state of Hogwarts characters data
The purpose of this data class is to encapsulate all the information about
the current state of your data in one object. This makes it easier to:
1) Manage state in your ViewModel: You can update the entire state at once by creating a new instance of HogwartsState.
2) Observe changes in your UI: By observing a single State<HogwartsState> in your composables, you can react to any changes in the data, loading status, or errors.
3) Handle different UI states: Your UI can easily check the state and display the appropriate content (loading indicator, error message, or list of characters).
4) Maintain a single source of truth: All state-related information is kept in one place, reducing the chances of inconsistent state.
 */
data class HogwartsState(
    val characters: List<HogwartsDataItem> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)

/*
this data class used to represent the inside details of hogwarts characters, like the fields that
are present in the HogwartsDataItem class.
 */

data class CharacterDetails(
    val name: String,
    val image: String,
    val actor: String
)