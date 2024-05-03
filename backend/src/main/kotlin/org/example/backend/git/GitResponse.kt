package org.example.backend.git


class GitResponse(val statusCode: Int) {
    private var responseBody = listOf<GitRepository>()
    private var message = ""

    fun setMessage(message: String): GitResponse {
        this.message = message
        return this
    }
    fun getMessage() = message

    fun setBody(body: List<GitRepository>): GitResponse {
        this.responseBody = body
        return this
    }
    fun getBody() = responseBody

}