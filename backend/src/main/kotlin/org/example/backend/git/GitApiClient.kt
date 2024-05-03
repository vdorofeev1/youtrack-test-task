package org.example.backend.git

import com.google.gson.JsonParser
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
            200 -> GitResponse(code).setMessage("good").setBody(getRepositories(response))
            401 -> GitResponse(code).setMessage("Bad token!")
            404 -> GitResponse(code).setMessage("Organization not found!")
            else -> {
                println(response.body())
                GitResponse(code).setMessage("Internal error")
            }
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
        val gitRepos = ArrayList<GitRepository>()
        val responseBody = response.body()

        val repos = JsonParser.parseString(responseBody).asJsonArray

        for (repo in repos) {
            val repoName = repo.asJsonObject["name"].asString
            gitRepos.add(GitRepository(repoName, hasHello(repoName)))
        }
        return gitRepos

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

    fun getBadLinkResponse() = GitResponse(405).setMessage("Bad link!")


}