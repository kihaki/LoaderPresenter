package net.koandesign.loaderpresenter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

public class ActivityPresenterModule<P extends Presenter<V>, V extends AppCompatActivity> implements LoaderManager.LoaderCallbacks<P> {

	private static final int PRESENTER_ID = 101;
	private final PresenterFactory<P> presenterFactory;
	private       P                   presenter;
	private       Context             applicationContext; // Do not store activity context here!

	/**
	 * Instance of {@link PresenterFactory} use to create a Presenter when needed. This instance should
	 * not contain {@link android.app.Activity} applicationContext reference since it will be kept on rotations.
	 */
	public ActivityPresenterModule(PresenterFactory<P> presenterFactory) {
		this.presenterFactory = presenterFactory;
	}

	@CallSuper
	public void onCreate(@NonNull V activity) {
		applicationContext = activity.getApplicationContext();
		activity.getSupportLoaderManager().initLoader(PRESENTER_ID, null, this);
	}

	@CallSuper
	public void onStart(@NonNull V view) {
		presenter.attachView(view);
	}

	@CallSuper
	public void onStop() {
		presenter.detachView();
	}

	@Override
	public final Loader<P> onCreateLoader(int id, Bundle args) {
		return new PresenterLoader<>(applicationContext, presenterFactory);
	}

	@Override
	public final void onLoadFinished(Loader<P> loader, P presenter) {
		this.presenter = presenter;
	}

	@Override
	public final void onLoaderReset(Loader<P> loader) {
		presenter = null;
	}
}