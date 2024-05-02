package org.example.backend

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BackendContorller {
    @CrossOrigin(origins = ["http://localhost:63342"])
    @GetMapping("/get_repos")
    fun getRepositories(): GitResponse {
        return GitHubApiService.getRepositories()
    }

}