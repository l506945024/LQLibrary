# LQLibrary
自用快速开发框架


网络请求实例:
        ApiProxy.getInstance().call(ApiProxy.getService(ApiService.class).login(4))
                .subscribeWith(new BaseHttpObserver<BaseHttpResult<Object>>() {
                    @Override
                    public void onHttpSuccess(BaseHttpResult result) {

                    }
                });
apk更新实例:
  if (mUpdateManager == null) {
            mUpdateManager = new AppUpdateManager(this);
        }
        if (downId != 0) {
            mUpdateManager.clearCurrentTask(downId);
        }
        downId = mUpdateManager.updateApkWifi(downUrl, "靓才", "更新中...");