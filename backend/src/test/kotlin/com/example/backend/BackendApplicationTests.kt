package com.example.backend

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/insert_test_data.sql")
class BackendApplicationTests(
	@Autowired val restTemplate: TestRestTemplate,
	@LocalServerPort val port:Int
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
		val response = restTemplate.getForEntity("http://localhost:$port/users", String::class.java)
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `GETリクエストはuserオブジェクトのリストを返す` () {
		val response = restTemplate.getForEntity("http://localhost:$port/users", Array<User>::class.java)
		assertThat(response.headers.contentType, equalTo(MediaType.APPLICATION_JSON))
		val users = response.body!!
		assertThat(users.size, equalTo(2))
		assertThat(users[0].id, equalTo(1))
		assertThat(users[0].name, equalTo("massa"))
		assertThat(users[1].id, equalTo(2))
		assertThat(users[1].name, equalTo("mac"))
	}

	@Test
	fun `POSTリクエストはOKステータスを返す` () {
		val request = UserRequest("bob")
		val response = restTemplate.postForEntity("http://localhost:$port/users", request, String::class.java)
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `POSTリクエストはUserオブジェクトを格納する` () {
		val request = UserRequest("bob")
		val beforePost = restTemplate.getForEntity("http://localhost:$port/users", Array<User>::class.java)
		val response = restTemplate.postForEntity("http://localhost:$port/users", request, String::class.java)
		val afterPost = restTemplate.getForEntity("http://localhost:$port/users", Array<User>::class.java)

		assertThat(afterPost.body!!.size - beforePost.body!!.size, equalTo(1))
		assertThat(afterPost.body!!.map {user: User -> user.name}, hasItem("bob"))
	}
}
