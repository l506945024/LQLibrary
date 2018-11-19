# LQLibrary 自用快速开发框架
                         <br>
                                 1.网络请求
                         <br>
                                 2.app下载更新
                         <br>
                                 3.吐司
                         <br>
                                 4.Activity/Fragment基类
                         
                         ### 网络请求实例
                                 ApiProxy.getInstance().call(ApiProxy.getService(ApiService.class).login(4))
                                         .subscribeWith(new BaseHttpObserver<BaseHttpResult<Object>>() {
                                             @Override
                                             public void onHttpSuccess(BaseHttpResult result) {
                         
                                             }
                                         });
                         ### apk更新实例
                                   if (mUpdateManager == null) {
                                         mUpdateManager = new AppUpdateManager(this);
                                   }
                                   if (downId != 0) {
                                         mUpdateManager.clearCurrentTask(downId);
                                   }
                         ### 自定义dialog调用实例
                         
                                 if (mAkDialog == null) {
                                     mAkDialog = new AkDialog.Builder(this, R.layout.activity_test)
                                             .setStyle(R.style.AkDialog)
                                             .setCancelTouchOutSide(true)
                                             .setTitle()
                                             .setMessage()
                                             .setViewText()
                                             .addBtnOnClickListener()
                                             .build();
                                 }
                                 mAkDialog.show();
