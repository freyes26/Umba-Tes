package com.example.umba.repository.network.json

import com.example.umba.repository.database.model.Movie

class PageProperty(
        val page: Int,
        val results: List<Movie>,
        val total_pages: Int,
        val total_results: Int
)