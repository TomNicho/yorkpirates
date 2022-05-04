package yorkpirates;

import static org.mockito.Mockito.mock;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;


public class GdxTestRunner extends BlockJUnit4ClassRunner implements ApplicationListener {
    
    public GdxTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
		HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();

		new HeadlessApplication(this, conf);
		Gdx.gl = mock(GL20.class);
	}

    @Override
    public void create() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }
}
