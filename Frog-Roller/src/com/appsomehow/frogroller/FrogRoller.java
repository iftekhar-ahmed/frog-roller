package com.appsomehow.frogroller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FrogRoller implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private TextureRegion tr;
	private Sprite sprite;
	private float w, h, ppuX, ppuY, frogPosX, frogPosY, jumpDuration = 0f;
	private boolean jumpState;
	
	public static final float VIEWPORT_WIDTH = 10f;
	public static final float VIEWPORT_HEIGHT = 7f;
	private	static final float FROG_JUMP_X = 3f;
	private static final float FROG_JUMP_Y = 3f;
	
	@Override
	public void create() {		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		ppuX = w / VIEWPORT_WIDTH;
		ppuY = h / VIEWPORT_HEIGHT;
		frogPosX = frogPosY = 0f;
		camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		camera.setToOrtho(false);
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("data/frog_1.png"));
		tr = new TextureRegion(texture);
		tr.flip(true, false);
		//texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, w, h);
		
		sprite = new Sprite(region);
		sprite.setSize(w, h);
		//sprite.setOrigin(0f, 0f);
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isTouched()) {
			frogPosX += FROG_JUMP_X;
			frogPosY += FROG_JUMP_Y;
			jumpDuration += Gdx.graphics.getDeltaTime();
		}
		else {
			if(frogPosY >= 0f) {
				frogPosX += FROG_JUMP_X;
				frogPosY -= FROG_JUMP_Y;
				jumpState = true;
				jumpDuration += Gdx.graphics.getDeltaTime();
			}
			if(frogPosY <= 0f && jumpState) {
				camera.translate(FROG_JUMP_X * jumpDuration, 0f);
				camera.update();
				jumpState = false;
			}
		}
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//sprite.draw(batch);
		batch.draw(tr, frogPosX, frogPosY, 1f * ppuX, 1f * ppuY);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
