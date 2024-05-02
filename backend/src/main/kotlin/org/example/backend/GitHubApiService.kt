package org.example.backend

import org.springframework.http.HttpStatus

class GitHubApiService {

    companion object {
        val token_ = ""
        val link_ = ""
        fun getRepositories(link: String = link_, token: String = token_): GitResponse {
            val response = GitResponse(HttpStatus.OK.value(), "good")
            val body = listOf(GitRepository("repo1", true), GitRepository("repo2",false))
            response.setBody(body)
            return response
        }

        private fun getAllRepositories(): List<String> {
            return listOf()
        }

        private fun checkRepository(): Boolean {
            return true
        }
    }
}
