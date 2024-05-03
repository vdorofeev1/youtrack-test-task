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
    private var token: String = System.getenv("GIT_TOKEN")
    private var orgLink: String = ""
    private var orgName: String = "youtrack-test-org"
    fun setToken(token: String) {
        this.token = token
    }
    fun setLink(link: String) {
        this.orgLink = link
    }

    fun repos() {

    }

    fun checkLink(): Boolean {
        return true;
    }

    fun getRepos(): List<GitRepository> {
        val gitRepos = ArrayList<GitRepository>()
        val link = "https://api.github.com/orgs/${orgName}/repos"
        val request = HttpRequest.newBuilder()
            .header("Accept", "application/vnd.github+json")
            .header("Authorization", "Bearer ${token}")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .uri(URI.create(link))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val responseBody = response.body()

        println(response.statusCode())

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
            .header("Authorization", "Bearer ${token}")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .uri(URI.create(link))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val responseBody = response.body()
        return "hello" in responseBody
    }


}