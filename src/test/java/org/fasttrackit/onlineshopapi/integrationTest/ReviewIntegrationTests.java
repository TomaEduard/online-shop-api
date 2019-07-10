package org.fasttrackit.onlineshopapi.integrationTest;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.domain.Review;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ReviewService;
import org.fasttrackit.onlineshopapi.steps.ProductSteps;
import org.fasttrackit.onlineshopapi.transfer.review.CreateReviewRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewIntegrationTests {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ProductSteps productSteps;

	@Test
	public void testCreateReview_whenValidRequest_thenReturnReview() throws ResourceNotFoundException {
		Product product = productSteps.createProduct();

		CreateReviewRequest reviewRequest = new CreateReviewRequest();
		reviewRequest.setProductId(product.getId());
		reviewRequest.setContent("Super awesome");

		Review review = reviewService.createReview(reviewRequest);

		assertThat(review, notNullValue());
		assertThat(review.getId(), greaterThan(0L));

		assertThat(review.getProduct(), notNullValue());
		assertThat(review.getProduct().getId(), is(product.getId()));
		assertThat(review.getContent(), is(reviewRequest.getContent()));
	}

}



















