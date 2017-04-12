package net.koandesign.loaderpresenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;

public class PresenterLoader<T extends Presenter> extends Loader<T> {

	private final PresenterFactory<T> factory;
	private       T                   presenter;

	/**
	 * Stores away the application context associated with context.
	 * Since Loaders can be used across multiple activities it's dangerous to
	 * store the context directly; always use {@link #getContext()} to retrieve
	 * the Loader's Context, don't use the constructor argument directly.
	 * The Context returned by {@link #getContext} is safe to use across
	 * Activity instances.
	 *
	 * @param context used to retrieve the application context.
	 */
	public PresenterLoader(@NonNull Context context, PresenterFactory<T> factory) {
		super(context);
		this.factory = factory;
	}

	@Override
	protected void onStartLoading() {

		// If we already own an instance, simply deliver it.
		if (presenter != null) {
			deliverResult(presenter);
			return;
		}

		// Otherwise, force a load
		forceLoad();
	}

	@Override
	protected void onForceLoad() {
		// Create the Presenter using the Factory
		presenter = factory.create();

		// Notify the presenter that it has been created
		presenter.onCreatePresenter();

		// Deliver the result
		deliverResult(presenter);
	}

	@Override
	protected void onReset() {
		if (presenter != null) {
			presenter.onDestroyPresenter();
			presenter = null;
		}
	}
}