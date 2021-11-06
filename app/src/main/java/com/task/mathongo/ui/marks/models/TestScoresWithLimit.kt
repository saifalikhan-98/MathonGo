package com.task.mathongo.ui.marks.models

data class TestScoresWithLimit(
    val count: Int,
    val error: Boolean,
    val limit: Int,
    val page: Int,
    val testScores: List<TestScore>,
    val totalCount: Int
)