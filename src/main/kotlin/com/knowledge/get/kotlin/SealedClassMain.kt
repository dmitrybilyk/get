package com.knowledge.get.kotlin

sealed class MediaItem

class Book(title: String, author: String, pages: Int): MediaItem()
class Movie(title: String, director: String, durationMinutes: Int): MediaItem()
class Song(title: String, artist: String, durationSeconds: Int): MediaItem()

fun List<MediaItem>.totalDuration(): Int {
    this.forEach {
        var totalDuration = 0
        if (it is Movie) {
//            totalDuration += it.
        }
    }
    return 4
}