package org.example.backend

import org.example.backend.git.GitApiClient
import org.example.backend.git.GitRepository
import org.example.backend.git.GitResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class BackendService(
    @Autowired
    val client: GitApiClient
) {

    val token_ = ""
    val link_ = ""
    fun getRepositories(link: String = link_, token: String = token_): GitResponse {
        val response = GitResponse(HttpStatus.OK.value(), "good")
        val body = client.getRepos()
        response.setBody(body)
        return response
    }

    private fun getAllRepositories(): List<String> {
        return listOf()
    }

    private fun checkRepository(name: String): Boolean {
        return true
    }

}
