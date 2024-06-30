package com.example.backend

import com.example.backend.dataClass.User
import com.example.backend.dataClass.UserRequest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/insert_test_data.sql")
@ContextConfiguration(initializers = [ConfigDataApplicationContextInitializer::class])
class BackendApplicationTests(
	@Autowired val restTemplate: TestRestTemplate,
	@LocalServerPort val port:Int,
	@Autowired val env: ConfigurableEnvironment
) {

	@Test
	fun contextLoads() {
	}

	@Test
	fun `最初のテスト` () {
		assertThat(1+2, equalTo(3))
	}

	@Test
	fun `GETリクエストはOKステータスを返す` () {
//		val response = restTemplate.getForEntity("${env.getProperty("spring.host.url")}:$port/api/users", String::class.java)
		val response = restTemplate.getForEntity("http://localhost:$port/api/users", String::class.java)
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `GETリクエストはuserオブジェクトのリストを返す` () {
		val response = restTemplate.getForEntity("http://localhost:$port/api/users", Array<User>::class.java)
		assertThat(response.headers.contentType, equalTo(MediaType.APPLICATION_JSON))
		val users = response.body!!
		assertThat(users.size, equalTo(2))
		assertThat(users[0].id, equalTo(1))
		assertThat(users[0].username, equalTo("masahiro"))
		assertThat(users[1].id, equalTo(2))
		assertThat(users[1].username, equalTo("mac"))
	}

	@Test
	fun `POSTリクエストはOKステータスを返す` () {
		val request = UserRequest("bob")
		val response = restTemplate.postForEntity("http://localhost:$port/api/users", request, String::class.java)
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `POSTリクエストはUserオブジェクトを格納する` () {
		val request = UserRequest("bob")
		val beforePost = restTemplate.getForEntity("http://localhost:$port/api/users", Array<User>::class.java)
		val response = restTemplate.postForEntity("http://localhost:$port/api/users", request, String::class.java)
		val afterPost = restTemplate.getForEntity("http://localhost:$port/api/users", Array<User>::class.java)

		assertThat(afterPost.body!!.size - beforePost.body!!.size, equalTo(1))
		assertThat(afterPost.body!!.map {user: User -> user.username}, hasItem("bob"))
	}
}
