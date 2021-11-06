package com.task.mathongo.ui.marks.models

data class AddTestRequest(
    val email: String,
    val exam: String,
    val scores: Scores,
    val testDate: String,
    val testName: String,
    val testSeries: String
)