package org.example.backend

import org.example.backend.git.GitApiClient
import org.example.backend.git.GitResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL


@Component
class BackendService(
    @Autowired
    val client: GitApiClient
) {

    fun getRepositories(token: String, link: String): GitResponse {
        client.setToken(token)
        client.setLink(link)
        return if (isValidLink(link))
            client.getReposResponse()
        else client.getBadLinkResponse()
    }

    fun isValidLink(link: String): Boolean {
        try {
            URI.create(link)
            return link.startsWith("https://github.com/")
        } catch (e: Exception) {
            return false
        }
    }

}
