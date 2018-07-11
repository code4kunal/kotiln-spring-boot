package com.example.controller

import com.example.dao.ArticleRepository
import com.example.persistence.ArticleEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ArticleController(private val articleRepository: ArticleRepository) {

    @GetMapping("/articles")
    fun getAll(): List<ArticleEntity> =
            articleRepository.findAll()


    @PostMapping("/articles")
    fun newArticle(@Valid @RequestBody article: ArticleEntity): ArticleEntity =
            articleRepository.save(article)


    @GetMapping("/articles/{id}")
    fun getArticle(@PathVariable(value = "id") articleId: Long): ResponseEntity<ArticleEntity> {
        return articleRepository.findById(articleId).map { article ->
            ResponseEntity.ok(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/articles/{id}")
    fun updateArticle(@PathVariable(value = "id") articleId: Long,
                          @Valid @RequestBody newArticle: ArticleEntity): ResponseEntity<ArticleEntity> {

        return articleRepository.findById(articleId).map { existingArticle ->
            val updatedArticle: ArticleEntity = existingArticle
                    .copy(title = newArticle.title, content = newArticle.content, asin = newArticle.asin, author = newArticle.author)
            ResponseEntity.ok().body(articleRepository.save(updatedArticle))
        }.orElse(ResponseEntity.notFound().build())

    }

    @DeleteMapping("/articles/{id}")
    fun deleteArticle(@PathVariable(value = "id") articleId: Long): ResponseEntity<Void> {

        return articleRepository.findById(articleId).map { article  ->
            articleRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    }
}