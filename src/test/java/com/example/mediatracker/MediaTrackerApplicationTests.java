package com.example.mediatracker;

import com.example.mediatracker.dto.MediaItemRecordDto;
import com.example.mediatracker.enums.MediaStatus;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MediaTrackerApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnOneMediaItem() {
		ResponseEntity<String> response = restTemplate
				.getForEntity("/media-item/25", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());

		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(25);

		String title = documentContext.read("$.title");
		assertThat(title).isEqualTo("Portal 2");

		Number rating = documentContext.read("$.rating");
		assertThat(rating).isEqualTo(10);

		String startDate = documentContext.read("$.startDate");
		assertThat(startDate).isEqualTo("2024-02-21");

		String finishDate = documentContext.read("$.finishDate");
		assertThat(finishDate).isEqualTo("2024-02-25");

		String status = documentContext.read("$.status");
		assertThat(status).isEqualTo("COMPLETED");

		Number mediaTypeId = documentContext.read("$.mediaType.id");
		assertThat(mediaTypeId).isEqualTo(99);

		String mediaTypeName = documentContext.read("$.mediaType.name");
		assertThat(mediaTypeName).isEqualTo("Game");

		String notes = documentContext.read("$.notes");
		assertThat(notes).isEqualTo("GREAT");
	}

	@Test
	void shouldReturnAllMediaItem() {
		ResponseEntity<String> response = restTemplate
				.getForEntity("/media-item", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int mediaItemCount = documentContext.read("$.length()");
		assertThat(mediaItemCount).isEqualTo(3);
	}

	@Test
	@DirtiesContext
	void shouldSaveANewMediaItem() {
		MediaItemRecordDto newMediaItemDto = new MediaItemRecordDto(
				"Deadpool",
				null,
				null,
				null,
				MediaStatus.WISHLIST,
				88L,
				null);
		ResponseEntity<Void> createResponse = restTemplate
				.postForEntity("/media-item", newMediaItemDto, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewMediaItem = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate
				.getForEntity(locationOfNewMediaItem, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());

		Number id = documentContext.read("$.id");
		assertThat(id).isNotNull();

		String title = documentContext.read("$.title");
		assertThat(title).isEqualTo("Deadpool");

		Number rating = documentContext.read("$.rating");
		assertThat(rating).isNull();

		String startDate = documentContext.read("$.startDate");
		assertThat(startDate).isNull();

		String finishDate = documentContext.read("$.finishDate");
		assertThat(finishDate).isNull();

		String status = documentContext.read("$.status");
		assertThat(status).isEqualTo("WISHLIST");

		Number mediaTypeId = documentContext.read("$.mediaType.id");
		assertThat(mediaTypeId).isEqualTo(88);

		String mediaTypeName = documentContext.read("$.mediaType.name");
		assertThat(mediaTypeName).isEqualTo("Movie");

		String notes = documentContext.read("$.notes");
		assertThat(notes).isNull();
	}

	@Test
	@DirtiesContext
	void shouldUpdateAnExistingMediaItem() {
		MediaItemRecordDto mediaItemRecordUpdate = new MediaItemRecordDto(
				"Baldurs Gate 3",
				10,
				LocalDate.of(2024, 4, 29),
				LocalDate.of(2024, 5, 1),
				MediaStatus.COMPLETED,
				99L,
				null);
		HttpEntity<MediaItemRecordDto> request = new HttpEntity<>(mediaItemRecordUpdate);
		ResponseEntity<Void> updateResponse = restTemplate
				.exchange("/media-item/11", HttpMethod.PUT, request, Void.class);
		assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> getResponse = restTemplate
				.getForEntity("/media-item/11", String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());

		Number id = documentContext.read("$.id");
		assertThat(id).isNotNull();

		String title = documentContext.read("$.title");
		assertThat(title).isEqualTo("Baldurs Gate 3");

		Number rating = documentContext.read("$.rating");
		assertThat(rating).isEqualTo(10);

		String startDate = documentContext.read("$.startDate");
		assertThat(startDate).isEqualTo("2024-04-29");

		String finishDate = documentContext.read("$.finishDate");
		assertThat(finishDate).isEqualTo("2024-05-01");

		String status = documentContext.read("$.status");
		assertThat(status).isEqualTo("COMPLETED");

		Number mediaTypeId = documentContext.read("$.mediaType.id");
		assertThat(mediaTypeId).isEqualTo(99);

		String mediaTypeName = documentContext.read("$.mediaType.name");
		assertThat(mediaTypeName).isEqualTo("Game");

		String notes = documentContext.read("$.notes");
		assertThat(notes).isNull();
	}

	@Test
	void shouldNotUpdateAMediaItemThatDoesNotExist() {
		MediaItemRecordDto unknownMediaItem = new MediaItemRecordDto(
				"Unknown Game",
				1,
				null,
				null,
				MediaStatus.COMPLETED,
				1L,
				null);
		HttpEntity<MediaItemRecordDto> request = new HttpEntity<>(unknownMediaItem);
		ResponseEntity<Void> updateResponse = restTemplate
				.exchange("/media-item/99999", HttpMethod.PUT, request, Void.class);
		assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DirtiesContext
	void shouldDeleteAnExistingMediaItem() {
		ResponseEntity<Void> deleteResponse = restTemplate
				.exchange("/media-item/11", HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> getResponse = restTemplate
				.getForEntity("/media-item/11", String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldNotDeleteAMediaItemThatDoesNotExist() {
		ResponseEntity<Void> deleteResponse = restTemplate
				.exchange("/media-item/99999", HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
