package net.koandesign.loaderpresenter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public class FragmentPresenterModule<P extends Presenter<V>, V extends Fragment> implements LoaderManager.LoaderCallbacks<P> {

	private static final int PRESENTER_ID = 101;
	private final PresenterFactory<P> presenterFactory;
	private       P                   presenter;
	private       Context             applicationContext; // Do not store activity context here!

	/**
	 * Instance of {@link PresenterFactory} use to create a Presenter when needed. This instance should
	 * not contain {@link android.app.Activity} applicationContext reference since it will be kept on rotations.
	 */
	public FragmentPresenterModule(PresenterFactory<P> presenterFactory) {
		this.presenterFactory = presenterFactory;
	}

	@CallSuper
	public void onCreate(@NonNull V fragment) {
		final Context context = fragment.getContext();
		if (context == null) {
			throw new IllegalStateException("Fragment has to be associated with a context at this point");
		}
		applicationContext = context.getApplicationContext();
		fragment.getLoaderManager().initLoader(PRESENTER_ID, null, this);
	}

	@CallSuper
	public void onResume(@NonNull V view) {
		presenter.attachView(view);
	}

	@CallSuper
	public void onPause() {
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

	public P getPresenter() {
		return presenter;
	}
}