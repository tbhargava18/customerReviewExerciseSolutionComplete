package de.hybris.platform.customerreview;
import java.util.Arrays;
import java.util.List;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.impl.UserModel;

public class ManageCustomerReviews {
	
	
	//Reading the forbidden words list from the properties file
	@Value("${customer.reviews.forbiddenWords}")
	private String forbiddenWords;
	
	//injecting the CustomerReviewService
	private CustomerReviewService customerReviewService;
	

	/**
	 * Method to get a product’s total number of customer reviews whose ratings
	 * are within a given range (inclusive)
	 * @param model
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public Integer getNumberOfReviewsInRange(ProductModel model, int minRange,int maxRange) {
		return customerReviewService.getNumberOfReviewsInRange(model, minRange, maxRange);
	}
	
	
	/**
	 * Method to create a customer review only if the user comment doesn't have a curse word
	 * @param rating
	 * @param headline
	 * @param comment
	 * @param user
	 * @param product
	 * @return
	 */
	public CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, UserModel user, ProductModel product) {
		
		CustomerReviewModel reviewModel = null;
		
		//Check if Customer’s comment contains any of these curse words
		if(!hasForbiddenWords(comment)){
			
			//chek if the rating is less than zero
			if(rating > 0) {
				reviewModel = customerReviewService.createCustomerReview(rating, headline, comment, user, product);
			}else {
				throw new RuntimeException("Unable to create review with rating less than zero");
			}
			
		}else {
			throw new RuntimeException("The comment provided contains forbidden words");
		}
		
		return reviewModel;
	}
	
	/**
	 * Method to check if the comment has a forbidden word
	 * @param comment
	 * @return
	 */
	public boolean hasForbiddenWords(String comment) {
		//Read the  list of curse words
		List<String> curseList = Arrays.asList(forbiddenWords.split(","));
		
		//Check if Customer’s comment contains any of these curse words
		boolean flag = false;
		for(String curseWord : curseList) {
			if(comment.contains(curseWord)) {
				flag=true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getForbiddenWords() {
		return forbiddenWords;
	}

	/**
	 * 
	 * @param forbiddenWords
	 */
	public void setForbiddenWords(String forbiddenWords) {
		this.forbiddenWords = forbiddenWords;
	}
	
	/**
	 * 
	 * @return
	 */
	public CustomerReviewService getCustomerReviewService() {
		return customerReviewService;
	}
	
	/**
	 * 
	 * @param customerReviewService
	 */
	public void setCustomerReviewService(CustomerReviewService customerReviewService) {
		this.customerReviewService = customerReviewService;
	}

}
