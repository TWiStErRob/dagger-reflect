package com.example.issue176;

import com.example.issue176.logic.ProfileDataInteractor;
import javax.inject.Inject;

public class DebugConfigPresenter implements DebugConfigContract.Presenter {
  @Inject DebugConfigPresenter(ProfileDataInteractor interactor) {}
}
