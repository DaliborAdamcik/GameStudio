package sk.tsystems.gamestudio.services.jdbc;

import java.util.List;

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
	public void myRating(GameEntity game, UserEntity user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double gameRating(GameEntity game) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RatingEntity> ratings() {
		// TODO Auto-generated method stub
		return null;
	}

}
