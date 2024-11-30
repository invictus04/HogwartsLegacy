package com.example.hogwartslegacy.data

/*
  We use @Seralized annotation for variable correction, like
  in this api we get alternate_actor as a object but to change in kotlin object we will
  rename it AlternateActor then this annotation comes into play.
*/


/*
   We have made the HogwartsDataItem data class which accept the data from api
   also we can edit it accordingly which data we want.
 */
data class HogwartsDataItem(
    val actor: String,
    val image: String,
    val name: String
)