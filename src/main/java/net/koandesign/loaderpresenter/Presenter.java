package net.koandesign.loaderpresenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class Presenter<V> {

	@Nullable private V view;

	public void attachView(@NonNull V view) {
		this.view = view;
		onAttachView(view);
	}

	public void detachView() {
		onDetachView();
		view = null;
	}

	@NonNull
	protected V getViewOrThrow() {
		if (view == null) {
			throw new IllegalStateException("View is null but should not be");
		} else {
			return view;
		}
	}

	protected boolean hasView() {
		return view != null;
	}

	protected abstract void onCreatePresenter();

	protected abstract void onDestroyPresenter();

	protected abstract void onAttachView(@NonNull V view);

	protected abstract void onDetachView();
}