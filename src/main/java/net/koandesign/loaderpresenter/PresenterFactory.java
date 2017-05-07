package net.koandesign.loaderpresenter;

import android.support.annotation.NonNull;

public interface PresenterFactory<T extends Presenter> {
	@NonNull
	T create();
}