package org.example.backend

import org.example.backend.git.GitResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["*"])
@RestController
class BackendController(
    @Autowired
    val service: BackendService
) {

    @GetMapping("/get_repos")
    fun getRepositories(): GitResponse {
        val token = System.getenv("GIT_TOKEN")
        val link = "https://github.com/youtrack-test-org"
        return service.getRepositories(token, link)
    }

    @GetMapping("/get_repos_with_params")
    fun getRepositoriesWithParams(@RequestParam("token") token: String, @RequestParam("link") link: String): GitResponse {
        return service.getRepositories(token, link)
    }

}