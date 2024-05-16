package org.example.backend

import org.example.backend.git.GitApiClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BackendServiceTest {
    private val TOKEN = System.getenv("GIT_TOKEN")
    private val LINK = "https://github.com/youtrack-test-org"
    private val serivce = BackendService(GitApiClient())
    @Test
    fun getRepositoriesTest() {
        var response = serivce.getRepositories(TOKEN, LINK)
        assertEquals(GitApiClient.SUCCESS_CODE, response.statusCode)

        response = serivce.getRepositories(TOKEN, "htps:/asd")
        assertEquals(GitApiClient.BAD_LINK_CODE, response.statusCode)
    }

    @Test
    fun isValidLinkTest() {
        assertTrue(serivce.isValidLink(LINK))
        assertFalse(serivce.isValidLink("htps:/weq"))
    }
}