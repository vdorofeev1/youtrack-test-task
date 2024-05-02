package org.example.backend

class GitResponse(val statusCode: Int, val message: String) {
    private var responseBody = listOf<GitRepository>()

    fun setBody(body: List<GitRepository>) {
        this.responseBody = body
    }
    fun getBody() = responseBody

}