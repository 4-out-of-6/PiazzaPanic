package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Ingredient;
import com.mygdx.game._convenience.IngredientStack;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The Player class stores all information regarding a chef, and also handles player movement.
 *
 */
public class Player {
	
	// player speed is a hard-coded value, so that it is the same for multiple players
	private final float speed = 3.5f;
	private final int id;
	private float posX;
	private float posY;
	private float previousPosX;
	private float previousPosY;
	private Rectangle collisionRect;
	private Sprite sprite;
	// The LinkedList is used as an implementation of a stack
	public Ingredient carrying;
	// Determines if the player can move
	private boolean movementEnabled;


	//==========================================================\\
	//                      CONSTRUCTOR                         \\
	//==========================================================\\
	
	public Player(int id, int startX, int startY, String texture)
	{
		this.id = id;
		this.posX = startX;
		this.posY = startY;
		this.sprite = new Sprite(new Texture(texture));
		this.carrying = null;
		this.movementEnabled = true;

		previousPosX = startX;
		previousPosY = startY;
		collisionRect = new Rectangle(posX, posY, 0.8f, 0.8f);
	}
	
	
	//==========================================================\\
	//                    PLAYER MOVEMENT                       \\
	//==========================================================\\
	
	public void handleMovement(Rectangle[] colliders) {
		sprite.setCenter(getXPos() + sprite.getTexture().getWidth() / 2f, getYPos() + sprite.getTexture().getHeight() / 2f);

		if(movementEnabled) {
			// Check for user movement input
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {moveY(1f);}
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {moveY(-1f);}
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {moveX(-1f);}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {moveX(1f);}

			collisionRect.setPosition(posX, posY);

			for(Rectangle c : colliders)
			{
				if(c.overlaps(collisionRect))
				{
					posX = previousPosX;
					posY = previousPosY;
					collisionRect.setPosition(posX, posY);
				}
			}
		}
	}
	
	public void moveX(float multiplier) {
		previousPosX = posX;
		posX += Gdx.graphics.getDeltaTime() * multiplier * speed;
	}
	
	public void moveY(float multiplier) {
		previousPosY = posY;
		posY += Gdx.graphics.getDeltaTime() * multiplier * speed;
	}
	
	
	//==========================================================\\
	//                    GETTERS & SETTERS                     \\
	//==========================================================\\
	
	public int getID() { return id; }
	
	public float getXPos() { return posX; }
	
	public float getYPos() { return posY; }
	
	public Sprite getSprite() { return sprite; }

	public Ingredient getCurrentIngredient() { return carrying; }

	public void setMovementEnabled(boolean movementEnabled) {
		this.movementEnabled = movementEnabled;
		System.out.println("CHEF " + (getID() + 1) + " MOVEMENT SET TO " + movementEnabled);
	}
	
}
