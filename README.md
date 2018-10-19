# LQLibrary
自用快速开发框架


网络请求实例:
        ApiProxy.getInstance().call(ApiProxy.getService(ApiService.class).login(4))
                .subscribeWith(new BaseHttpObserver<BaseHttpResult<Object>>() {
                    @Override
                    public void onHttpSuccess(BaseHttpResult result) {

                    }
                });
