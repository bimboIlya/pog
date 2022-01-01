package com.bimboilya.yacr.feature.authorization

object AuthConfig {

    const val BASE_URL = "https://www.reddit.com/"
    const val AUTH_URL = "api/v1/authorize.compact"
    const val REDIRECT_URL = "http://localhost:8080"
    const val CLIENT_ID = "db59HsSRwJG0uw"

    const val STATE_STRING = "should-be-random-lole"

    val everyScope = listOf(
        "identity",
        "edit",
        "flair",
        "history",
        "modconfig",
        "modflair",
        "modlog",
        "modposts",
        "modwiki",
        "mysubreddits",
        "privatemessages",
        "read",
        "report",
        "save",
        "submit",
        "subscribe",
        "vote",
        "wikiedit",
        "wikiread",
    )
        .joinToString(separator = ",")

    val fullUrl: String
        get() = "$BASE_URL$AUTH_URL?client_id=$CLIENT_ID&response_type=code&state=$STATE_STRING&redirect_uri=$REDIRECT_URL&duration=permanent&scope=$everyScope"
}