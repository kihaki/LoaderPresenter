package net.koandesign.loaderpresenter;

public interface PresenterFactory<T extends Presenter> {
	T create();
}