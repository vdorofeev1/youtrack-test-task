package org.example.backend.git

import com.google.gson.JsonParser
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class GitApiClient {
    private val client = HttpClient.newBuilder().build()
    private var token: String = ""
    private var orgName: String = ""
    private var orgLink: String = ""
    fun setToken(token: String): GitApiClient {
        this.token = token
        return this
    }
    fun setLink(link: String): GitApiClient {
        this.orgLink = link
        this.orgName = extractName()
        return this
    }

    fun extractName(): String {
        return orgLink.split("/").last()
    }

    fun getReposResponse(): GitResponse {
        val response = sendRepositoriesRequest()
        return when (val code = response.statusCode()) {
            200 -> GitResponse(code).message("good").body(getRepositories(response))
            401 -> GitResponse(code).message("Bad token!")
            404 -> GitResponse(code).message("Organization not found!")
            else -> GitResponse(code).message("Internal error")
        }
    }

    fun getReposResponseFaster(): GitResponse {
        val response = sendRepositoriesRequest()
        return when (val code = response.statusCode()) {
            200 -> GitResponse(code).message("good").body(getRepositoriesFaster(response))
            401 -> GitResponse(code).message("Bad token!")
            404 -> GitResponse(code).message("Organization not found!")
            else -> GitResponse(code).message("Internal error")
        }
    }

    fun sendRepositoriesRequest(): HttpResponse<String> {
        val link = "https://api.github.com/orgs/${orgName}/repos"
        val request = HttpRequest.newBuilder()
            .header("Accept", "application/vnd.github+json")
            .header("Authorization", "Bearer $token")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .uri(URI.create(link))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response
    }
    fun getRepositories(response: HttpResponse<String>): List<GitRepository> {
        val gitRepos = mutableListOf<GitRepository>()
        val responseBody = response.body()

        val repos = JsonParser.parseString(responseBody).asJsonArray

        for (repo in repos) {
            val repoName = repo.asJsonObject["name"].asString
            gitRepos.add(createGitRepo(repoName))
        }
        return gitRepos
    }

    fun getRepositoriesFaster(response: HttpResponse<String>): List<GitRepository> = runBlocking {
        val responseBody = response.body()

        val repos = JsonParser.parseString(responseBody).asJsonArray

        val gitRepos = repos.map {
            async {
                createGitRepo(it.asJsonObject["name"].asString)
            }
        }
        return@runBlocking gitRepos.awaitAll()
    }

    fun hasHello(repoName: String): Boolean {
        val link = "https://api.github.com/repos/${orgName}/${repoName}/readme"
        val request = HttpRequest.newBuilder()
            .header("Accept", "application/vnd.github.raw+json")
            .header("Authorization", "Bearer $token")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .uri(URI.create(link))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val responseBody = response.body()
        return "hello" in responseBody
    }

    fun createGitRepo(repoName: String): GitRepository {
        return GitRepository(repoName, hasHello(repoName))
    }

    fun getBadLinkResponse() = GitResponse(405).message("Bad link!")

}