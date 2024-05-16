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
    fun getRepositories(@RequestParam("token") token: String, @RequestParam("link") link: String): GitResponse {
        return service.getRepositories(token, link)
    }

}