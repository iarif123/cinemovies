package com.aweshams.cinematch.di;

import com.aweshams.cinematch.CinematchApplication;
import com.aweshams.cinematch.ui.BaseActivity;
import com.aweshams.cinematch.ui.BaseFragment;
import com.aweshams.cinematch.ui.BaseHomeFragment;
import com.aweshams.cinematch.ui.MainActivity;
import com.aweshams.cinematch.ui.MoviesHomeFragment;
import com.aweshams.cinematch.ui.MoviesNowPlayingFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by irteza on 2018-05-20.
 */

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApplicationComponent {
    /** General */
    void inject(CinematchApplication application);

    void inject(MainActivity mainActivity);

    // BaseActivity / Fragment
    void inject(BaseActivity activity);
    void inject(BaseFragment fragment);

    void inject(BaseHomeFragment homeFragment);
    void inject(MoviesHomeFragment moviesHomeFragment);

    void inject(MoviesNowPlayingFragment fragment);
}
