package sk.tsystems.gamestudio.services.jdbc;

import sk.tsystems.gamestudio.entity.GameEntity;
import sk.tsystems.gamestudio.entity.RatingEntity;
import sk.tsystems.gamestudio.entity.UserEntity;
import sk.tsystems.gamestudio.services.RatingService;

public class RatingSvc extends jdbcConnector implements RatingService {

	public RatingSvc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addRating(RatingEntity rating) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RatingEntity myRating(GameEntity game, UserEntity user) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public double gameRating(GameEntity game) {
		// TODO Auto-generated method stub
		return 0;
	}
}
