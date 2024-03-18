package com.giannism13.movieflix.ktorClient

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
	private const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmODI5MTJlNWMyN2IxODRlOTAz" +
			"MjY4MzFiNTE3MzRiYyIsInN1YiI6IjY1ZjMzNjEyYzQ5MDQ4MDE4NjFhM" +
			"zNkYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.HjSFLyc" +
			"6jX_YYfwuayXrsjJe_Wodyd-JIjlxnLpJX2g"

	val client: HttpClient = HttpClient(OkHttp) {
		this.expectSuccess = true
		install(Logging) {
			logger = object : Logger {
				override fun log(message: String) {
					if (message.length <= 4068) {
						Log.d("ktor", message)
					} else {
						var remainingMessage = message
						while (remainingMessage.isNotEmpty()) {
							val chunk = remainingMessage.substring(0, minOf(4068, remainingMessage.length))
							Log.d("ktor", chunk)
							remainingMessage = remainingMessage.substring(chunk.length)
						}
					}
				}
			}
			level = LogLevel.ALL
		}
		install(ContentNegotiation) {
			json(Json{
				this.ignoreUnknownKeys = true
			})
		}
		install(DefaultRequest) {
			url {
				protocol = URLProtocol.HTTPS
				host = "api.themoviedb.org"
				encodedPath = "/3/movie/"
				parameters.append("language", "en-US")
			}
			headers {
				append("accept", "application/json")
				append("Authorization", "Bearer $ACCESS_TOKEN")
			}
		}
	}
}