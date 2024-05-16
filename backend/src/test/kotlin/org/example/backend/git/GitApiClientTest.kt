package org.example.backend.git

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class GitApiClientTest {
    private val client = GitApiClient()
    private val TOKEN = System.getenv("GIT_TOKEN")
    private val LINK = "https://github.com/youtrack-test-org"

    @Test
    fun getRepositoriesTest() {
        client.setToken(TOKEN)
        client.setLink(LINK)
        val response = client.sendRepositoriesRequest()
        val repositories = client.getRepositories(response)
        assertEquals(repositories.size, 4)
        assertEquals(repositories, listOf(
            GitRepository(name="repo1", hasHello=true),
            GitRepository(name="repo2", hasHello=false),
            GitRepository(name="repo3", hasHello=false),
            GitRepository(name="repo4", hasHello=true))
        )
    }

    @Test
    fun getRepositoriesResponseTest200() {
        client.setToken(TOKEN)
        client.setLink(LINK)
        var response = client.getReposResponse()
        assertEquals(GitApiClient.SUCCESS_CODE, response.statusCode)

        client.setLink("https://github.com/bibabibabiba")
        response = client.getReposResponse()
        assertEquals(GitApiClient.ORGANIZATION_FOT_FOUND_CODE, response.statusCode)

        client.setLink(LINK)
        client.setToken("bibabibabiba")
        response = client.getReposResponse()
        assertEquals(GitApiClient.BAD_TOKEN_CODE, response.statusCode)
    }

    @Test
    fun hasHello() {
        client.setToken(TOKEN)
        client.setLink(LINK)
        assertTrue(client.hasHello("repo1"))
        assertFalse(client.hasHello("repo2"))
    }

    @Test
    fun setLinkTest() {
        client.setLink(LINK)
        assertEquals("youtrack-test-org", client.extractName())

        client.setLink("https://github.com/neapolis-spring2023-cpp")
        assertEquals("neapolis-spring2023-cpp", client.extractName())
    }

    @Test
    fun calculateTime() {
        client.setToken(TOKEN)
        client.setLink(LINK)

        var startTime = System.currentTimeMillis()
        var response = client.getReposResponse()
        var endTime = System.currentTimeMillis()
        println("TIME: " + (endTime - startTime))

        startTime = System.currentTimeMillis()
        response = client.getReposResponseFaster()
        endTime = System.currentTimeMillis()
        println("TIME: " + (endTime - startTime))



    }
}